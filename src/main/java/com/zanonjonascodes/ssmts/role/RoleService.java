package com.zanonjonascodes.ssmts.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zanonjonascodes.ssmts.core.rest.crud.CrudMapper;
import com.zanonjonascodes.ssmts.core.rest.crud.CrudServiceAbstract;

@Service
public class RoleService extends CrudServiceAbstract<RoleEntity, String, RoleRequestModel, RoleResponseModel> {

  private RoleRepository repository;

  private RoleMapper mapper;
  
  private RoleModelAssembler modelAssembler;

  private PagedResourcesAssembler<RoleEntity> pagedResourcesAssembler;

  public RoleService(ObjectMapper objectMapper, RoleRepository repository, RoleMapper mapper,
      RoleModelAssembler modelAssembler, PagedResourcesAssembler<RoleEntity> pagedResourcesAssembler) {
    super(objectMapper);
    this.repository = repository;
    this.mapper = mapper;
    this.modelAssembler = modelAssembler;
    this.pagedResourcesAssembler = pagedResourcesAssembler;
  }

  @Override
  public CrudMapper<RoleEntity, String, RoleRequestModel, RoleResponseModel> getMapper() {
    return mapper;
  }

  @Override
  public JpaRepository<RoleEntity, String> getRepository() {
    return repository;
  }

  @Override
  public RepresentationModelAssemblerSupport<RoleEntity, RoleResponseModel> getModelAssembler() {
    return modelAssembler;
  }

  @Override
  public PagedResourcesAssembler<RoleEntity> getPagedResourcesAssembler() {
    return pagedResourcesAssembler;
  }

}
