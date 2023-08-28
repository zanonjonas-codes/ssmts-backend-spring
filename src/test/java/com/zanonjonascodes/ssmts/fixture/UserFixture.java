package com.zanonjonascodes.ssmts.fixture;

import java.util.List;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.zanonjonascodes.ssmts.core.rest.crud.CrudMapper;
import com.zanonjonascodes.ssmts.user.UserEntity;
import com.zanonjonascodes.ssmts.user.UserMapperImpl;
import com.zanonjonascodes.ssmts.user.UserModelAssembler;
import com.zanonjonascodes.ssmts.user.UserRequestModel;
import com.zanonjonascodes.ssmts.user.UserResponseModel;

public class UserFixture extends FixtureAbstract<UserEntity, String, UserRequestModel, UserResponseModel>{

  private String password = "password";

  public List<UserEntity> getEntityList() {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    return List.of(
        UserEntity.builder()
            .id("1")
            .firstName("name1")
            .lastName("lastname1")
            .password(passwordEncoder.encode(password))
            .email("user1@gmail.com")
            .build(),
        UserEntity.builder()
            .id("2")
            .firstName("name2")
            .lastName("lastname2")
            .password(passwordEncoder.encode(password))
            .email("user2@gmail.com")
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

  @Override
  public UserRequestModel getRequestModel() {
    UserEntity entity = getEntity();
    entity.setPassword(password);
    return getMapper().toRequestModel(entity);
  }
   
}
