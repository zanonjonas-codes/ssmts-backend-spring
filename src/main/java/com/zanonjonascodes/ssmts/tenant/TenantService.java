package com.zanonjonascodes.ssmts.tenant;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.zanonjonascodes.ssmts.core.exception.EntityNotFoundException;


@Service
public class TenantService {

  @Autowired
  private TenantRepository tenantRepository;

  @Autowired
  private TenantMapper mapper;

  @Autowired
  private ObjectMapper objectMapper;

  public TenantEntity create(TenantRequestModel requestModel) {
    TenantEntity entity = mapper.toEntity(requestModel);
    return tenantRepository.save(entity);
  }

  public TenantEntity patch(Map<String, Object> requestModel, UUID uuid)
      throws JsonPatchException, JsonProcessingException {
    TenantEntity oldEntity = tenantRepository.findById(uuid).orElseThrow(() -> {
        return new EntityNotFoundException(TenantEntity.class, "uuid", uuid.toString());
      }
    );
    JsonMergePatch jsonMergePatch = objectMapper.readValue(objectMapper.writeValueAsString(requestModel),
        JsonMergePatch.class);
    JsonNode patched = jsonMergePatch.apply(objectMapper.convertValue(oldEntity, JsonNode.class));

    TenantRequestModel patchedRequestModel = objectMapper.treeToValue(patched, TenantRequestModel.class);
    TenantEntity patchedEntity = mapper.toEntity(patchedRequestModel);
    patchedEntity.setId(uuid);

    return tenantRepository.save(patchedEntity);
  }

  public TenantEntity findById(UUID uuid) {
    return tenantRepository.findById(uuid).orElseThrow(() -> {
        return new EntityNotFoundException(TenantEntity.class, "uuid", uuid.toString());
      }
    );
  }

  public Page<TenantEntity> findAll(Pageable pageable) {
    return tenantRepository.findAll(pageable);
  }

  public void delete(UUID uuid) {
    tenantRepository.deleteById(uuid);
  }

}
