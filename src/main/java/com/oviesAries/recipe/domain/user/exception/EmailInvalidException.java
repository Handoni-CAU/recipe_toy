package com.oviesAries.recipe.domain.user.exception;

import org.springframework.http.HttpStatus;

public class EmailInvalidException extends RuntimeException{

    private final HttpStatus httpStatus;

    public EmailInvalidException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
