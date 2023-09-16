package com.oviesAries.recipe.domain.user.application;

import com.oviesAries.recipe.domain.user.exception.PasswordInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {

    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.{7,18}$).*";

    public void validatePassword(final String password) {
        if (!password.matches(PASSWORD_REGEX)) {
            throw new PasswordInvalidException("비밀번호는 하나 이상의 소문자를 포함한 7글자 이상 18글자 이하여야 합니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
