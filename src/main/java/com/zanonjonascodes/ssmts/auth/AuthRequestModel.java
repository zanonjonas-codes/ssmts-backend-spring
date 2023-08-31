package com.zanonjonascodes.ssmts.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestModel {

    private String username;

    private String password;

}
