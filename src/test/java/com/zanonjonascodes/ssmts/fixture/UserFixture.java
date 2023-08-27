package com.zanonjonascodes.ssmts.fixture;

import java.util.List;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import com.zanonjonascodes.ssmts.core.rest.crud.CrudMapper;
import com.zanonjonascodes.ssmts.user.UserEntity;
import com.zanonjonascodes.ssmts.user.UserMapperImpl;
import com.zanonjonascodes.ssmts.user.UserModelAssembler;
import com.zanonjonascodes.ssmts.user.UserRequestModel;
import com.zanonjonascodes.ssmts.user.UserResponseModel;

public class UserFixture extends FixtureAbstract<UserEntity, String, UserRequestModel, UserResponseModel>{

  public List<UserEntity> getEntityList() {
    return List.of(
        UserEntity.builder()
            .id("1")
            .build(),
        UserEntity.builder()
            .id("2")
            .build());
  }

  @Override
  protected CrudMapper<UserEntity, String, UserRequestModel, UserResponseModel> getMapper() {
    return new UserMapperImpl();
  }

  @Override
  protected RepresentationModelAssemblerSupport<UserEntity, UserResponseModel> getModelAssembler() {
    return new UserModelAssembler(new UserMapperImpl());
  }

  
}
