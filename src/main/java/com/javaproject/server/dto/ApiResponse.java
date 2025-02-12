package com.javaproject.server.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {

    /**
     * Response data.
     */
    private T data;

    /**
     * Response message.
     */
    private String message;

}
