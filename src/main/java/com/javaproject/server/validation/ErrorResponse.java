package com.javaproject.server.validation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ErrorResponse {

    /**
     * Error status.
     */
    private final int status;

    /**
     * Error message.
     */
    private final String message;

    protected ErrorResponse(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

}
