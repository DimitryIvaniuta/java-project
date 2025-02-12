package com.javaproject.server.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Internal Server Error Status.
     */
    private static final int INTERNAL_SERVER_ERROR_STATUS = 500;

    /**
     * Handle ResponseStatusException.
     *
     * @param ex a ResponseStatusException
     * @return aValidationErrorResponse
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ValidationErrorResponse> handleResponseStatusException(final ResponseStatusException ex) {
        // Return a ValidationErrorResponse with the status code and message
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
                ex.getStatusCode().value(),
                ex.getReason()
        );
        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
    }

    /**
     * Handle Exception.
     * Return a generic error response for unexpected exceptions.
     *
     * @param ex an Exception object
     * @return a ValidationErrorResponse object
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(final Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "status", INTERNAL_SERVER_ERROR_STATUS,
                        "message", ex.getMessage()
                ));
    }

}
