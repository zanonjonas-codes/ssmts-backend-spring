package com.zanonjonascodes.ssmts.tenant;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.zanonjonascodes.ssmts.core.exception.EntityNotFoundException;
import com.zanonjonascodes.ssmts.core.rest.crud.CrudMapper;
import com.zanonjonascodes.ssmts.core.rest.crud.CrudService;

@Service
public class TenantService implements CrudService<TenantEntity, UUID, TenantRequestModel, TenantResponseModel> {

  @Autowired
  private TenantRepository repository;

  @Autowired
  private TenantMapper mapper;

  @Autowired
  private TenantModelAssembler modelAssembler;

  @Autowired
  private PagedResourcesAssembler<TenantEntity> pagedResourcesAssembler;

  @Override
  public CrudMapper<TenantEntity, TenantRequestModel, TenantResponseModel> getMapper() {
    return mapper;
  }

  @Override
  public JpaRepository<TenantEntity, UUID> getRepository() {
    return repository;
  }

  @Override
  public Class<TenantEntity> getEntityClass() {
    return TenantEntity.class;
  }

  @Override
  public Class<TenantRequestModel> getRequestModelClass() {
    return TenantRequestModel.class;
  }

  public TenantResponseModel patch(Map<String, Object> requestModel, UUID uuid)
      throws JsonPatchException, JsonProcessingException {
    TenantEntity oldEntity = repository.findById(uuid).orElseThrow(() -> {
      return new EntityNotFoundException(TenantEntity.class, "uuid", uuid.toString());
    });
    JsonMergePatch jsonMergePatch = objectMapper.readValue(objectMapper.writeValueAsString(requestModel),
        JsonMergePatch.class);
    JsonNode patched = jsonMergePatch.apply(objectMapper.convertValue(oldEntity, JsonNode.class));

    TenantRequestModel patchedRequestModel = objectMapper.treeToValue(patched, TenantRequestModel.class);
    TenantEntity patchedEntity = mapper.toEntity(patchedRequestModel);
    patchedEntity.setId(uuid);

    TenantEntity savedEntity = repository.save(patchedEntity);
    return mapper.toResponse(savedEntity);
  }

  public TenantResponseModel findById(UUID uuid) {
    TenantEntity entity = repository.findById(uuid).orElseThrow(() -> {
      return new EntityNotFoundException(TenantEntity.class, "uuid", uuid.toString());
    });

    return mapper.toResponse(entity);
  }

  public PagedModel<TenantResponseModel> findAll(Pageable pageable) {

    Page<TenantEntity> page = repository.findAll(pageable);
    return pagedResourcesAssembler.toModel(page, modelAssembler);
  }

  public void delete(UUID uuid) {
    repository.deleteById(uuid);
  }

}
