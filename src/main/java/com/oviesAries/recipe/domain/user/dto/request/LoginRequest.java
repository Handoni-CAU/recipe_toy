package com.oviesAries.recipe.domain.user.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoginRequest {


    @Email
    @NotNull
    private String email;

    @NotBlank
    private String password;

    public static LoginRequest of(final String email, final String password) {
        return new LoginRequest(email, password);
    }

}
