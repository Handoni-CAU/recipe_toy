package com.oviesAries.recipe.domain.user.dto.response;

import com.oviesAries.recipe.domain.user.domain.Email;
import lombok.*;


@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoginResponse {

    private String accessToken;
    private String name;
    private String email;


    public static LoginResponse of(
            final String accessToken,
            final String name,
            final Email email
    ) {
        return new LoginResponse(accessToken, name, email.getValue());
    }

}
