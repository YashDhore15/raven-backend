package com.raven.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.raven.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        
    	return new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage()
        );
    }
    
    @ExceptionHandler(TenantNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTenantNotFound(TenantNotFoundException ex) {

        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationErrors(MethodArgumentNotValidException ex) {

        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed"
        );
    }
    
    
}






