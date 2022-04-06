package com.springboot.PracticleProject.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(value = ValidationHandler.class)
    public ErrorResponse validationHandler(ValidationHandler exception){
        ErrorResponse response = new ErrorResponse();
        response.setStatus(exception.getStatus());
        response.setMessage(exception.getMessage());
        return response;
    }
}
