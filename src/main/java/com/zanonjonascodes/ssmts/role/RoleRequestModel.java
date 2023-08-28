package com.zanonjonascodes.ssmts.role;

import com.zanonjonascodes.ssmts.core.rest.crud.RequestModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequestModel implements RequestModel {

  protected String role;
  
}
