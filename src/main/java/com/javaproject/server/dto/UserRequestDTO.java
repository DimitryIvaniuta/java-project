package com.javaproject.server.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

    /**
     * Email field.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    /**
     * Login field.
     */
    @NotBlank(message = "Login is required")
    private String login;

    /**
     * Name field.
     */
    @NotBlank(message = "Name is required")
    private String name;

    /**
     * Password field.
     */
    @NotBlank(message = "Password is required")
    private String password;
}
