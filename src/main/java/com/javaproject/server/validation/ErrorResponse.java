package com.javaproject.server.validation;

import com.javaproject.server.dto.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse extends ApiResponse<Integer> {

    /**
     * Error status.
     */
    private final Integer status;

    /**
     * Error message.
     */
    private final String message;

}
