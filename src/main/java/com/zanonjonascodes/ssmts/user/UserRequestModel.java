package com.zanonjonascodes.ssmts.user;

import com.zanonjonascodes.ssmts.core.rest.crud.RequestModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestModel implements RequestModel {

  protected String id;

}
