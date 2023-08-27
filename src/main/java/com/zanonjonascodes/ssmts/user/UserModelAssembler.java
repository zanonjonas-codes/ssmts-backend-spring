package com.zanonjonascodes.ssmts.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<UserEntity, UserResponseModel> {

  UserMapper mapper;

  public UserModelAssembler(UserMapper mapper) {
    super(UserController.class, UserResponseModel.class);
    this.mapper = mapper;
  }

  @Override
  public UserResponseModel toModel(UserEntity entity) {
    UserResponseModel responseModel = mapper.toResponse(entity);

    responseModel.add(linkTo(
        methodOn(UserController.class)
            .findById(entity.getId()))
        .withSelfRel());

    return responseModel;
  }

  @Override
  public CollectionModel<UserResponseModel> toCollectionModel(Iterable<? extends UserEntity> entities) {
    CollectionModel<UserResponseModel> responseModels = super.toCollectionModel(entities);

    responseModels.add(linkTo(methodOn(UserController.class).findAll(null)).withSelfRel());

    return responseModels;
  }
}
