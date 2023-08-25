package com.zanonjonascodes.ssmts.core.rest.crud;

import java.io.Serializable;
import java.util.Map;

import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.zanonjonascodes.ssmts.core.data.BaseEntity;
import com.zanonjonascodes.ssmts.core.exception.EntityNotFoundException;

public abstract class CrudServiceAbstract<E extends BaseEntity<I>, I extends Serializable, V extends RequestModel, R extends RepresentationModel<R>>
    implements CrudService<E, I, V, R> {

  public R create(V requestModel) {
    E entity = getMapper().toEntity(requestModel);
    E savedEntity = getRepository().save(entity);
    return getMapper().toResponse(savedEntity);
  }

  public R patch(Map<String, Object> requestModel, I uuid)
      throws JsonPatchException, JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
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

  public R findById(I uuid) {
    E entity = getRepository().findById(uuid).orElseThrow(() -> {
      return new EntityNotFoundException(getEntityClass(), "uuid", uuid.toString());
    });

    return getMapper().toResponse(entity);
  }

  public PagedModel<R> findAll(Pageable pageable) {

    Page<E> page = getRepository().findAll(pageable);
    return getPagedResourcesAssembler().toModel(page, getModelAssembler());
  }

  public void delete(I uuid) {
    getRepository().deleteById(uuid);
  }

  @Override
  public Class<E> getEntityClass() {
    return (Class<E>) GenericTypeResolver.resolveTypeArguments(getClass(), CrudServiceAbstract.class)[0];
  }

  @Override
  public Class<V> getRequestModelClass() {
    return (Class<V>) GenericTypeResolver.resolveTypeArguments(getClass(), CrudServiceAbstract.class)[2];
  }

}
