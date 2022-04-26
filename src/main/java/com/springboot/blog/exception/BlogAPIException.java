package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;

    public BlogAPIException(String message, HttpStatus httpStatus, String message1) {
        this.httpStatus = httpStatus;
        this.message = message1;
    }

    public BlogAPIException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


