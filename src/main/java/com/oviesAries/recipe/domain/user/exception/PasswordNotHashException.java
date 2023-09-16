package com.oviesAries.recipe.domain.user.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class PasswordNotHashException extends RuntimeException{

    private final HttpStatus httpStatus;

    public PasswordNotHashException(Throwable cause) {
        super("비밀번호에 문제가 발생했습니다.");
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        log.error("비밀번호가 해시되지 않았습니다.", cause);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


}
