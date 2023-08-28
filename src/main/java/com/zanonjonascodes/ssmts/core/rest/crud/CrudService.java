package com.zanonjonascodes.ssmts.core.rest.crud;

import java.io.Serializable;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;
import com.zanonjonascodes.ssmts.core.data.BaseEntity;

public interface CrudService<E extends BaseEntity<I>, I extends Serializable, V extends RequestModel, R extends RepresentationModel<R>>
    extends CrudHooks<E, I, V, R> {

  R create(V requestModel);

  R patch(Map<String, Object> requestModel, I uuid) throws JsonPatchException, JsonProcessingException;

  R findById(I uuid);

  PagedModel<R> findAll(Pageable pageable);

  void delete(I uuid);

  JpaRepository<E, I> getRepository();

  CrudMapper<E, I, V, R> getMapper();

  Class<E> getEntityClass();

  Class<V> getRequestModelClass();

  PagedResourcesAssembler<E> getPagedResourcesAssembler();

  RepresentationModelAssemblerSupport<E, R> getModelAssembler();

  E applyDefaultValues(E entity);

  void validateEntity(E entity);

}