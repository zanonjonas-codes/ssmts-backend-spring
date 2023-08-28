package com.zanonjonascodes.ssmts.role;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zanonjonascodes.ssmts.core.rest.crud.CrudControllerAbstract;
import com.zanonjonascodes.ssmts.core.rest.crud.CrudService;

import lombok.AllArgsConstructor;

@RestController()
@RequestMapping("/role")
@AllArgsConstructor
public class RoleController
    extends CrudControllerAbstract<RoleEntity, String, RoleRequestModel, RoleResponseModel> {

  private RoleService roleService;

  @Override
  public CrudService<RoleEntity, String, RoleRequestModel, RoleResponseModel> getCrudService() {
    return roleService;
  }

}
