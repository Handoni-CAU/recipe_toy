package com.oviesAries.recipe.domain.user.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

public class PasswordInvalidException extends RuntimeException {

    private final HttpStatus httpStatus;

    public PasswordInvalidException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

