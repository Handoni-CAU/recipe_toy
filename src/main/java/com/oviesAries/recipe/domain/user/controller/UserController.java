package com.oviesAries.recipe.domain.user.controller;

import com.oviesAries.recipe.domain.entity.User;
import com.oviesAries.recipe.domain.user.dto.UserCreateRequest;
import com.oviesAries.recipe.domain.user.dto.UserResponseDto;
import com.oviesAries.recipe.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateRequest request) {
        User newUser = userService.createUser(request.getUserName(), request.getPassword());
        UserResponseDto response = new UserResponseDto(newUser.getId(), newUser.getUserName(), newUser.getPassword());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("username/{userName}")
    public ResponseEntity<UserResponseDto> getUserByUserName(@PathVariable String userName) {
        User user = userService.getUserByUserName(userName);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        UserResponseDto response = new UserResponseDto(user.getId(), user.getUserName(), user.getPassword());
        return ResponseEntity.ok(response);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        UserResponseDto response = new UserResponseDto(user.getId(), user.getUserName(), user.getPassword());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}