package com.zanonjonascodes.ssmts.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zanonjonascodes.ssmts.core.rest.crud.CrudMapper;
import com.zanonjonascodes.ssmts.core.rest.crud.CrudServiceAbstract;

@Service
public class UserService extends CrudServiceAbstract<UserEntity, String, UserRequestModel, UserResponseModel> {

  private UserRepository repository;

  private UserMapper mapper;
  
  private UserModelAssembler modelAssembler;

  private PagedResourcesAssembler<UserEntity> pagedResourcesAssembler;

  public UserService(ObjectMapper objectMapper, UserRepository repository, UserMapper mapper,
      UserModelAssembler modelAssembler, PagedResourcesAssembler<UserEntity> pagedResourcesAssembler) {
    super(objectMapper);
    this.repository = repository;
    this.mapper = mapper;
    this.modelAssembler = modelAssembler;
    this.pagedResourcesAssembler = pagedResourcesAssembler;
  }

  @Override
  public CrudMapper<UserEntity, String, UserRequestModel, UserResponseModel> getMapper() {
    return mapper;
  }

  @Override
  public JpaRepository<UserEntity, String> getRepository() {
    return repository;
  }

  @Override
  public RepresentationModelAssemblerSupport<UserEntity, UserResponseModel> getModelAssembler() {
    return modelAssembler;
  }

  @Override
  public PagedResourcesAssembler<UserEntity> getPagedResourcesAssembler() {
    return pagedResourcesAssembler;
  }

  @Override
  public UserEntity applyDefaultValues(UserEntity entity) {
    if (entity.getIsEnabled() == null) entity.setIsEnabled(true);
    return entity;
  }
}
