package com.oviesAries.recipe.domain.user.domain;

import com.oviesAries.recipe.domain.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class  AuthPrincipal {

    private Long id;
    private String email;

    public static AuthPrincipal from(final User user) {
        return new AuthPrincipal(user.getId(), user.getEmail().getValue());
    }
    

}
