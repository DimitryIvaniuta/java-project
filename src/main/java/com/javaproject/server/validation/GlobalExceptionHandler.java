package com.javaproject.server.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Hanfle ResponseStatusException.
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
     *
     * @param ex an Exception object
     * @return a ValidationErrorResponse object
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ValidationErrorResponse> handleGenericException(final Exception ex) {
        // Return a generic error response for unexpected exceptions
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred"
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
