package com.oviesAries.recipe.domain.user.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    @Test
    void getUserNameTest() {
        UserDto userDto = new UserDto("username", "password");
        assertEquals("username", userDto.getUsername());
    }

}