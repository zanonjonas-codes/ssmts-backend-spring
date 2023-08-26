package com.zanonjonascodes.ssmts.tenant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zanonjonascodes.ssmts.core.rest.crud.CrudMapper;
import com.zanonjonascodes.ssmts.core.rest.crud.CrudServiceAbstract;

@Service
public class TenantService extends CrudServiceAbstract<TenantEntity, String, TenantRequestModel, TenantResponseModel> {

  private TenantRepository repository;

  private TenantMapper mapper;
  
  private TenantModelAssembler modelAssembler;

  private PagedResourcesAssembler<TenantEntity> pagedResourcesAssembler;

  public TenantService(ObjectMapper objectMapper, TenantRepository repository, TenantMapper mapper,
      TenantModelAssembler modelAssembler, PagedResourcesAssembler<TenantEntity> pagedResourcesAssembler) {
    super(objectMapper);
    this.repository = repository;
    this.mapper = mapper;
    this.modelAssembler = modelAssembler;
    this.pagedResourcesAssembler = pagedResourcesAssembler;
  }

  @Override
  public CrudMapper<TenantEntity, String, TenantRequestModel, TenantResponseModel> getMapper() {
    return mapper;
  }

  @Override
  public JpaRepository<TenantEntity, String> getRepository() {
    return repository;
  }

  @Override
  public RepresentationModelAssemblerSupport<TenantEntity, TenantResponseModel> getModelAssembler() {
    return modelAssembler;
  }

  @Override
  public PagedResourcesAssembler<TenantEntity> getPagedResourcesAssembler() {
    return pagedResourcesAssembler;
  }

}
