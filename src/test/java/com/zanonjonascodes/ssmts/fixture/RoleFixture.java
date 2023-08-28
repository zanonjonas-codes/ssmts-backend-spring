package com.zanonjonascodes.ssmts.fixture;

import java.util.List;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import com.zanonjonascodes.ssmts.core.rest.crud.CrudMapper;
import com.zanonjonascodes.ssmts.role.RoleEntity;
import com.zanonjonascodes.ssmts.role.RoleMapperImpl;
import com.zanonjonascodes.ssmts.role.RoleModelAssembler;
import com.zanonjonascodes.ssmts.role.RoleRequestModel;
import com.zanonjonascodes.ssmts.role.RoleResponseModel;

public class RoleFixture extends FixtureAbstract<RoleEntity, String, RoleRequestModel, RoleResponseModel>{

  public List<RoleEntity> getEntityList() {
    return List.of(
        RoleEntity.builder()
            .id("1")
            .role("USER")
            .build(),
        RoleEntity.builder()
            .id("2")
            .role("ADMIN")
            .build());
  }

  @Override
  protected CrudMapper<RoleEntity, String, RoleRequestModel, RoleResponseModel> getMapper() {
    return new RoleMapperImpl();
  }

  @Override
  protected RepresentationModelAssemblerSupport<RoleEntity, RoleResponseModel> getModelAssembler() {
    return new RoleModelAssembler(new RoleMapperImpl());
  }

  
}
