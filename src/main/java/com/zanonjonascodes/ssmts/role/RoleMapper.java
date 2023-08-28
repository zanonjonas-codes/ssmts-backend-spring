package com.zanonjonascodes.ssmts.role;

import org.mapstruct.Mapper;

import com.zanonjonascodes.ssmts.core.rest.crud.CrudMapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends CrudMapper<RoleEntity, String, RoleRequestModel, RoleResponseModel> {

}
