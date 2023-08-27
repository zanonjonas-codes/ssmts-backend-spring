package com.zanonjonascodes.ssmts.user;

import org.mapstruct.Mapper;

import com.zanonjonascodes.ssmts.core.rest.crud.CrudMapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends CrudMapper<UserEntity, String, UserRequestModel, UserResponseModel> {

}
