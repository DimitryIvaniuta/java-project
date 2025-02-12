package com.javaproject.server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    /**
     * Login email.
     */
    private String email;

    /**
     * Login password.
     */
    private String password;

}
