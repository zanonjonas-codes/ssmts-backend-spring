package com.zanonjonascodes.ssmts.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zanonjonascodes.ssmts.core.security.jwt.JwtAuthService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

  private AuthenticationManager authenticationManager;

  private JwtAuthService jwtAuthService;

  public ResponseEntity<AuthReponseModel> login(AuthRequestModel authRequestModel) {
    Authentication authentication;
    
    try {
      authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authRequestModel.getUsername(), authRequestModel.getPassword()));
    } catch ( BadCredentialsException | DisabledException | LockedException e ) {
      throw e;
    }

    if (authentication.isAuthenticated()) {
      AuthReponseModel authReponseModel = new AuthReponseModel();
      String token = jwtAuthService.generateToken(authRequestModel.getUsername());
      authReponseModel.setToken(token);
      authReponseModel.setExpiresIn(jwtAuthService.getExpiresIn(token));

      return new ResponseEntity<AuthReponseModel>(authReponseModel, HttpStatus.OK);

    } else {
      throw new UsernameNotFoundException("Invalid username.");
    }
  }
}
