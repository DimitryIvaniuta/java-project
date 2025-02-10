package com.javaproject.server.validation;

public class ValidationErrorResponse extends ErrorResponse {

    /**
     * ValidationErrorResponse constructor.
     *
     * @param status  ValidationErrorResponse status
     * @param message ValidationErrorResponse message
     */
    public ValidationErrorResponse(final int status, final String message) {
        super(status, message);
    }
}
