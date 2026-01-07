package com.assignment.backend.common.exception;

/**
 * Custom runtime exception used across the application
 * for predictable API errors.
 */
public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }
}
