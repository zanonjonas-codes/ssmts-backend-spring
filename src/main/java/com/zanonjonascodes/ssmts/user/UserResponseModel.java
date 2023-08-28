package com.zanonjonascodes.ssmts.user;

import com.zanonjonascodes.ssmts.core.rest.crud.ResponseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserResponseModel extends ResponseModel<UserResponseModel, String> {

  protected String email;

  protected String password;

  protected String firstName;

  protected String lastName;

  protected String isEnabled;

}
