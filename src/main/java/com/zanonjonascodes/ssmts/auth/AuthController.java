package com.zanonjonascodes.ssmts.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

  AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<AuthReponseModel> login(@RequestBody AuthRequestModel authRequestModel) {
    return authService.login(authRequestModel);
  }
}
