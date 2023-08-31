package com.zanonjonascodes.ssmts.auth;

import lombok.Data;

@Data
public class AuthReponseModel {
  protected String token;

  protected Long expiresIn;
}
