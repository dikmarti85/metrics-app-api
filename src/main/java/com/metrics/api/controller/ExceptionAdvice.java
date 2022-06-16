package com.metrics.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionAdvice {

    public static final String ERRORS = "errors";
    private static final String MESSAGE = "message";
    private static final String CAUSE = "cause";
    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERROR = "error";

    public ExceptionAdvice() {

    }

    @ExceptionHandler(value = {SQLIntegrityConstraintViolationException.class})
    ResponseEntity<Object> handleException(SQLIntegrityConstraintViolationException ve) {
        return buildExceptionResponse(ve, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildExceptionResponse(Exception ex, HttpStatus status) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put(STATUS, status.value());
        responseBody.put(ERROR, status.getReasonPhrase());
        responseBody.put(MESSAGE, ex.getMessage());
        responseBody.put(CAUSE, ex.toString());

        return new ResponseEntity<>(responseBody, status);
    }

}
