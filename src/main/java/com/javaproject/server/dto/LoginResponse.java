package com.javaproject.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse extends ApiResponse<String> {

    /**
     * Response message.
     */
    private String message;

    /**
     * Response Token.
     */
    private String token;

    /**
     * Response user name.
     */
    private String username;

}
