package com.raven.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ApiKeyResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ApiKeyResourceNotFoundException(String message) {
        super(message);
    }
}