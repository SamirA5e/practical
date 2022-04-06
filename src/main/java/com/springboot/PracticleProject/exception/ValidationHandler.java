package com.springboot.PracticleProject.exception;

import org.springframework.http.HttpStatus;

public class ValidationHandler extends RuntimeException{
    private String message;
    private HttpStatus status;

    public ValidationHandler(String message,HttpStatus status) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
