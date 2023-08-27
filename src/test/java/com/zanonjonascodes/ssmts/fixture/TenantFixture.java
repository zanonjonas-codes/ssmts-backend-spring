package com.zanonjonascodes.ssmts.fixture;

import java.util.List;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import com.zanonjonascodes.ssmts.core.rest.crud.CrudMapper;
import com.zanonjonascodes.ssmts.tenant.TenantEntity;
import com.zanonjonascodes.ssmts.tenant.TenantMapperImpl;
import com.zanonjonascodes.ssmts.tenant.TenantModelAssembler;
import com.zanonjonascodes.ssmts.tenant.TenantRequestModel;
import com.zanonjonascodes.ssmts.tenant.TenantResponseModel;

public class TenantFixture extends FixtureAbstract<TenantEntity, String, TenantRequestModel, TenantResponseModel>{

  public List<TenantEntity> getEntityList() {
    return List.of(
        TenantEntity.builder()
            .id("1")
            .companyName("The company 1")
            .logoImgUrl("the logo image url 2")
            .build(),
        TenantEntity.builder()
            .id("2")
            .companyName("The company 2")
            .logoImgUrl("the logo image url 2")
            .build());
  }

  @Override
  protected CrudMapper<TenantEntity, String, TenantRequestModel, TenantResponseModel> getMapper() {
    return new TenantMapperImpl();
  }

  @Override
  protected RepresentationModelAssemblerSupport<TenantEntity, TenantResponseModel> getModelAssembler() {
    return new TenantModelAssembler(new TenantMapperImpl());
  }

  
}
