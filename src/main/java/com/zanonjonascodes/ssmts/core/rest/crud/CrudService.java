package com.zanonjonascodes.ssmts.core.rest.crud;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.zanonjonascodes.ssmts.core.data.BaseEntity;
import com.zanonjonascodes.ssmts.core.exception.EntityNotFoundException;
import com.zanonjonascodes.ssmts.tenant.TenantEntity;
import com.zanonjonascodes.ssmts.tenant.TenantResponseModel;

public interface CrudService<E extends BaseEntity, I extends UUID, V, R> {
  
  public default R create(V requestModel) {
    E entity = getMapper().toEntity(requestModel);
    E savedEntity = getRepository().save(entity);
    return getMapper().toResponse(savedEntity);
  }

  public default R patch(Map<String, Object> requestModel, I uuid)
      throws JsonPatchException, JsonProcessingException {
    E oldEntity = getRepository().findById(uuid).orElseThrow(() -> {
      return new EntityNotFoundException(getEntityClass(), "uuid", uuid.toString());
    });
    JsonMergePatch jsonMergePatch = objectMapper.readValue(objectMapper.writeValueAsString(requestModel),
        JsonMergePatch.class);
    JsonNode patched = jsonMergePatch.apply(objectMapper.convertValue(oldEntity, JsonNode.class));

    V patchedRequestModel = objectMapper.treeToValue(patched, getRequestModelClass());
    E patchedEntity = getMapper().toEntity(patchedRequestModel);
    patchedEntity.setId(uuid);

    E savedEntity = getRepository().save(patchedEntity);
    return getMapper().toResponse(savedEntity);
  }

  public default R findById(I uuid) {
    E entity = getRepository().findById(uuid).orElseThrow(() -> {
      return new EntityNotFoundException(getEntityClass(), "uuid", uuid.toString());
    });

    return getMapper().toResponse(entity);
  }

  public default PagedModel<TenantResponseModel> findAll(Pageable pageable) {

    Page<E> page = getRepository().findAll(pageable);
    return getPagedResourcesAssembler().toModel(page, getModelAssembler());
  }

  public default void delete(I uuid) {
    getRepository().deleteById(uuid);
  }

  JpaRepository<E, I> getRepository();

  CrudMapper<E, V, R> getMapper();

  Class<E> getEntityClass();

  Class<V> getRequestModelClass();

  ObjectMapper getObjectMapper();

  PagedResourcesAssembler<E> getPagedResourcesAssembler();

  // TODO parei aqui. Problema com generics no representationModel...
  RepresentationModelAssemblerSupport<E, R> getModelAssembler();
}