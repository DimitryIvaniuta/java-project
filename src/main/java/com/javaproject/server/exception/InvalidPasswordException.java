package com.javaproject.server.exception;

public class InvalidPasswordException extends RuntimeException {

    /**
     * InvalidPasswordException constructor.
     *
     * @param message value
     */
    public InvalidPasswordException(final String message) {
        super(message);
    }

    /**
     * InvalidPasswordException throwable constructor.
     *
     * @param message value
     * @param cause   Throwable cause
     */
    public InvalidPasswordException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
