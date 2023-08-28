package com.zanonjonascodes.ssmts.role;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RoleModelAssembler extends RepresentationModelAssemblerSupport<RoleEntity, RoleResponseModel> {

  RoleMapper mapper;

  public RoleModelAssembler(RoleMapper mapper) {
    super(RoleController.class, RoleResponseModel.class);
    this.mapper = mapper;
  }

  @Override
  public RoleResponseModel toModel(RoleEntity entity) {
    RoleResponseModel responseModel = mapper.toResponse(entity);

    responseModel.add(linkTo(
        methodOn(RoleController.class)
            .findById(entity.getId()))
        .withSelfRel());

    return responseModel;
  }

  @Override
  public CollectionModel<RoleResponseModel> toCollectionModel(Iterable<? extends RoleEntity> entities) {
    CollectionModel<RoleResponseModel> responseModels = super.toCollectionModel(entities);

    responseModels.add(linkTo(methodOn(RoleController.class).findAll(null)).withSelfRel());

    return responseModels;
  }
}
