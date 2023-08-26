package com.zanonjonascodes.ssmts.core.rest.crud;

import java.io.Serializable;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;
import com.zanonjonascodes.ssmts.core.data.BaseEntity;

public interface CrudController<E extends BaseEntity<I>, I extends Serializable, V extends RequestModel, R extends RepresentationModel<R>> {

  ResponseEntity<R> create(V requestModel);
  
  ResponseEntity<R> patch(
      Map<String, Object> requestModel, I uuid)
      throws JsonProcessingException, JsonPatchException;

  ResponseEntity<R> findById(I uuid);

  ResponseEntity<PagedModel<R>> findAll(Pageable pageable);

  ResponseEntity<R> delete(I uuid);

  CrudService<E, I , V, R> getCrudService();

}