package com.zanonjonascodes.ssmts.core.rest.crud;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.RepresentationModel;

import com.zanonjonascodes.ssmts.core.data.BaseEntity;

public interface CrudHooks<E extends BaseEntity<I>, I extends Serializable, V extends RequestModel, R extends RepresentationModel<R>> {
  E beforeCreate(E entity);

  E afterCreate(E entity);

  E beforePatch(E entity);

  E afterPatch(E entity);

  E beforeDelete(E entity);

  E afterDelete(E entity);

  Page<E> afterFindAll(Page<E> entity);

  E afterFindById(E entity);
}
