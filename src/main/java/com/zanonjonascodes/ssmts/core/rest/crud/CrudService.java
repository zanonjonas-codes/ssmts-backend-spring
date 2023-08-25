package com.zanonjonascodes.ssmts.core.rest.crud;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import com.zanonjonascodes.ssmts.core.data.BaseEntity;

public interface CrudService<E extends BaseEntity<I>, I extends Serializable, V extends RequestModel, R extends RepresentationModel<R>> {

  JpaRepository<E, I> getRepository();

  CrudMapper<E, I, V, R> getMapper();

  Class<E> getEntityClass();

  Class<V> getRequestModelClass();

  PagedResourcesAssembler<E> getPagedResourcesAssembler();

  RepresentationModelAssemblerSupport<E, R> getModelAssembler();

}