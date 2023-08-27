package com.zanonjonascodes.ssmts.fixture;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.zanonjonascodes.ssmts.core.config.parser.JacksonConfig;
import com.zanonjonascodes.ssmts.core.data.BaseEntity;
import com.zanonjonascodes.ssmts.core.rest.crud.CrudMapper;
import com.zanonjonascodes.ssmts.core.rest.crud.RequestModel;

public abstract class FixtureAbstract<E extends BaseEntity<I>, I extends Serializable, V extends RequestModel, R extends RepresentationModel<R>> {

  public abstract List<E> getEntityList();

  protected abstract CrudMapper<E, I, V, R> getMapper();
  
  protected abstract RepresentationModelAssemblerSupport<E, R> getModelAssembler();

  public E getEntity() {
    return getEntityList().get(0);
  }

  public List<R> getTenantResponseModelList() {
    return Lists.newArrayList(getMapper().toResponse(getEntityList()));
  }

  public R getResponseModel() {
    return getMapper().toResponse(getEntity());
  }

  public V getRequestModel() {
    return getMapper().toRequestModel(getEntity());
  }

  public String getRequestModelAsJson() throws JsonProcessingException {
    JacksonConfig jacksonConfig = new JacksonConfig();
    return jacksonConfig.getObjectMapper().writeValueAsString(getRequestModel());
  }

  public Page<E> getEntityPage() {
    return new PageImpl<E>(getEntityList());
  }

  public PagedModel<R> getResponsePagedModel() {
    PagedResourcesAssembler<E> pagedResourcesAssembler = new PagedResourcesAssembler<>(null, null);
    return pagedResourcesAssembler.toModel(getEntityPage(), getModelAssembler());
  }

}
