package com.zanonjonascodes.ssmts.role;

import com.zanonjonascodes.ssmts.core.rest.crud.ResponseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class RoleResponseModel extends ResponseModel<RoleResponseModel, String> {

  protected String role;

}
