package com.zanonjonascodes.ssmts.core.rest.crud;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import com.zanonjonascodes.ssmts.core.data.BaseEntity;

public interface CrudMapper<E extends BaseEntity<I>, I extends Serializable, V extends RequestModel, R extends RepresentationModel<R>>  {

  E toEntity(V requestModel);
  
  R toResponse(E entity);

  V toRequestModel(E entity);

  Iterable<R> toResponse(Iterable<E> entity);

}
