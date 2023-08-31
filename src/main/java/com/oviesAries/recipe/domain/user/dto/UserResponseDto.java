package com.oviesAries.recipe.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String username;

    public UserResponseDto(UserDto userDto) {
        this.username = userDto.getUsername();
    }
}