package com.zanonjonascodes.ssmts.core.rest.crud;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface CrudController<E, I, V, R> {

  // @PostMapping()
  // default ResponseEntity<R> create(@RequestBody V requestModel) {
  //   R responseModel = this.getService().create(requestModel);
  //   return new ResponseEntity<R>(responseModel, HttpStatus.OK);
  // }

  // public CrudService<E, I, V, R> getService();
}
