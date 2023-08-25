package com.zanonjonascodes.ssmts.core.rest.crud;

public interface CrudMapper<E, V, R>  {

  E toEntity(V requestModel);
  
  R toResponse(E entity);

  Iterable<R> toResponse(Iterable<E> entity);

}
