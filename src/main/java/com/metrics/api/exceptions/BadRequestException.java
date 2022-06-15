package com.metrics.api.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final HttpStatus status;

    public BadRequestException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }
}
