package com.zanonjonascodes.ssmts.tenant;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class TenantModelAssembler extends RepresentationModelAssemblerSupport<TenantEntity, TenantResponseModel> {

  @Autowired
  TenantMapper mapper;

  public TenantModelAssembler() {
    super(TenantController.class, TenantResponseModel.class);
  }

  @Override
  public TenantResponseModel toModel(TenantEntity entity) {
    TenantResponseModel responseModel = mapper.toResponse(entity);

    responseModel.add(linkTo(
        methodOn(TenantController.class)
            .findById(entity.getId()))
        .withSelfRel());

    return responseModel;
  }

  @Override
  public CollectionModel<TenantResponseModel> toCollectionModel(Iterable<? extends TenantEntity> entities) {
    CollectionModel<TenantResponseModel> responseModels = super.toCollectionModel(entities);

    responseModels.add(linkTo(methodOn(TenantController.class).findAll(null)).withSelfRel());

    return responseModels;
  }
}
