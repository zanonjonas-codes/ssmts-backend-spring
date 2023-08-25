package com.zanonjonascodes.ssmts.tenant;

import org.mapstruct.Mapper;

import com.zanonjonascodes.ssmts.core.rest.crud.CrudMapper;

@Mapper(componentModel = "spring")
public interface TenantMapper extends CrudMapper<TenantEntity, String, TenantRequestModel, TenantResponseModel> {

}
