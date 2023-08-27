package com.zanonjonascodes.ssmts.user;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class UserResponseModel extends RepresentationModel<UserResponseModel> {
  protected String id;

}
