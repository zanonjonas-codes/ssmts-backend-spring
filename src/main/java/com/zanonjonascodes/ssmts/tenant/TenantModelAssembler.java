package com.zanonjonascodes.ssmts.tenant;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class TenantModelAssembler extends RepresentationModelAssemblerSupport<TenantEntity, TenantResponseModel> {

  TenantMapper mapper;

  public TenantModelAssembler(TenantMapper mapper) {
    super(TenantController.class, TenantResponseModel.class);
    this.mapper = mapper;
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
