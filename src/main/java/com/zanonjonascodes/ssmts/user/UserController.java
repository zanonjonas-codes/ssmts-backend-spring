package com.zanonjonascodes.ssmts.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zanonjonascodes.ssmts.core.rest.crud.CrudControllerAbstract;
import com.zanonjonascodes.ssmts.core.rest.crud.CrudService;

import lombok.AllArgsConstructor;

@RestController()
@RequestMapping("/user")
@AllArgsConstructor
public class UserController
    extends CrudControllerAbstract<UserEntity, String, UserRequestModel, UserResponseModel> {

  private UserService userService;

  @Override
  public CrudService<UserEntity, String, UserRequestModel, UserResponseModel> getCrudService() {
    return userService;
  }

}
