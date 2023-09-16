package com.oviesAries.recipe.domain.user.security;

public interface PasswordEncoder {

    boolean matches(final String planePassword, final String encodedPassword);

    String encode(final String password);
}
