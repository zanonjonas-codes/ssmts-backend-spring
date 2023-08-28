package com.zanonjonascodes.ssmts.core.rest.crud;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

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

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class CrudServiceAbstract<E extends BaseEntity<I>, I extends Serializable, V extends RequestModel, R extends RepresentationModel<R>>
    implements CrudService<E, I, V, R> {

  private ObjectMapper objectMapper;

  public R create(V requestModel) {
    E entity = getMapper().toEntity(requestModel);
    entity = applyDefaultValues(entity);
    entity = beforeCreate(entity);
    validateEntity(entity);
    E savedEntity = getRepository().save(entity);
    entity = afterCreate(entity);
    return getMapper().toResponse(savedEntity);
  }

  public R patch(Map<String, Object> requestModel, I uuid)
      throws JsonPatchException, JsonProcessingException {
    E oldEntity = getRepository().findById(uuid).orElseThrow(() -> {
      return new EntityNotFoundException(getEntityClass(), "uuid", uuid.toString());
    });
    JsonMergePatch jsonMergePatch = objectMapper.readValue(objectMapper.writeValueAsString(requestModel),
        JsonMergePatch.class);
    JsonNode patched = jsonMergePatch.apply(objectMapper.convertValue(oldEntity, JsonNode.class));

    E patchedEntity = objectMapper.treeToValue(patched, getEntityClass());
    patchedEntity.setId(uuid);

    patchedEntity = beforePatch(patchedEntity);
    E savedEntity = getRepository().save(patchedEntity);
    patchedEntity = afterPatch(patchedEntity);

    return getMapper().toResponse(savedEntity);
  }

  public R findById(I uuid) {
    E entity = getRepository().findById(uuid).orElseThrow(() -> {
      return new EntityNotFoundException(getEntityClass(), "uuid", uuid.toString());
    });

    entity = afterFindById(entity);
    return getMapper().toResponse(entity);
  }

  public PagedModel<R> findAll(Pageable pageable) {
    Page<E> page = getRepository().findAll(pageable);
    page = afterFindAll(page);
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

  @Override
  public void validateEntity(E entity) {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<E>> violations = validator.validate(entity);
    if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
  }

  @Override
  public E applyDefaultValues(E entity) {
    return entity;
  }

  @Override
  public E afterCreate(E entity) {
    return entity;
  }

  @Override
  public E afterDelete(E entity) {
    return entity;
  }

  @Override
  public Page<E> afterFindAll(Page<E> page) {
    return page;
  }

  @Override
  public E afterFindById(E entity) {
    return entity;
  }

  @Override
  public E afterPatch(E entity) {
    return entity;
  }

  @Override
  public E beforeCreate(E entity) {
    return entity;
  }

  @Override
  public E beforeDelete(E entity) {
    return entity;
  }

  @Override
  public E beforePatch(E entity) {
    return entity;
  }

}
