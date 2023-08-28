package com.zanonjonascodes.ssmts.user;

import java.util.Set;

import com.zanonjonascodes.ssmts.core.rest.crud.ResponseModel;
import com.zanonjonascodes.ssmts.role.RoleEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserResponseModel extends ResponseModel<UserResponseModel, String> {

  protected String email;

  protected String firstName;

  protected String lastName;

  protected String isEnabled;

  protected Set<RoleEntity> grantedRoles;

}
