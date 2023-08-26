package com.zanonjonascodes.ssmts.core.rest.crud;

import java.io.Serializable;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;
import com.zanonjonascodes.ssmts.core.data.BaseEntity;

public abstract class CrudControllerAbstract<E extends BaseEntity<I>, I extends Serializable, V extends RequestModel, R extends RepresentationModel<R>>
    implements CrudController<E, I, V, R> {
 
  @Override
  @PostMapping
  public ResponseEntity<R> create(@RequestBody V requestModel) {
    R responseModel = getCrudService().create(requestModel);
    return new ResponseEntity<R>(responseModel, HttpStatus.CREATED);
  }

  @Override
  @PatchMapping("{uuid}")
  public ResponseEntity<R> patch(
      @RequestBody Map<String, Object> requestModel, @PathVariable I uuid)
      throws JsonProcessingException, JsonPatchException {

    R responseModel = getCrudService().patch(requestModel, uuid);
    return new ResponseEntity<R>(responseModel, HttpStatus.OK);
  }

  @Override
  @GetMapping("{uuid}")
  public ResponseEntity<R> findById(@PathVariable I uuid) {
    R responseModel = getCrudService().findById(uuid);
    return new ResponseEntity<R>(responseModel, HttpStatus.OK);
  }

  @Override
  @GetMapping()
  public ResponseEntity<PagedModel<R>> findAll(Pageable pageable) {

    PagedModel<R> responseModel = getCrudService().findAll(pageable);
    return new ResponseEntity<>(responseModel, HttpStatus.OK);

  }

  @Override
  @DeleteMapping("{uuid}")
  public ResponseEntity<R> delete(@PathVariable I uuid) {
    getCrudService().delete(uuid);
    return new ResponseEntity<R>(HttpStatus.OK);
  }

}
