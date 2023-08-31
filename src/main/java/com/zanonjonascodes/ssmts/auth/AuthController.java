package com.zanonjonascodes.ssmts.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zanonjonascodes.ssmts.core.security.jwt.JwtAuthService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

  private AuthenticationManager authenticationManager;

  private JwtAuthService jwtAuthService;

  // TODO: REFATORAR ISSO AQUI
  @PostMapping("/login")
  public ResponseEntity<AuthReponseModel> login(@RequestBody AuthRequestModel authRequestModel) {
    log.info("entrou aqui");
    System.out.println("alshdlfkjhasldjkhfjlkdas");
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestModel.getUsername(), authRequestModel.getPassword()));
    if (authentication.isAuthenticated()) {
      AuthReponseModel authReponseModel = new AuthReponseModel();
      String token = jwtAuthService.generateToken(authRequestModel.getUsername());
      authReponseModel.setToken(token);
      authReponseModel.setExpiresIn(jwtAuthService.getExpiresIn(token));

      return new ResponseEntity<AuthReponseModel>(authReponseModel, HttpStatus.OK);

    } else {
      throw new UsernameNotFoundException("invalid user request");
    }
  }
}
