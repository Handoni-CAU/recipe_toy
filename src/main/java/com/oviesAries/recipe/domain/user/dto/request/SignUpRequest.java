package com.oviesAries.recipe.domain.user.dto.request;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SignUpRequest {

    @NotBlank
    private String userName;

    @NotBlank
    private String nickName;

    @NotBlank
    private String password;

    @Email
    @NotNull
    private String email;

    // 정적 팩토리 메소드 추가
    public static SignUpRequest of(final String userName, final String nickName, final String password, final String email) {
        return new SignUpRequest(userName, nickName, password, email);
    }
}
