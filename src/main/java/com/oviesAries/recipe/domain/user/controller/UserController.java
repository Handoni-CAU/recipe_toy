package com.oviesAries.recipe.domain.user.controller;

import com.oviesAries.recipe.domain.entity.RecipeIngredient;
import com.oviesAries.recipe.domain.entity.User;
import com.oviesAries.recipe.domain.recipe.dto.RecipeIngredientDTO;
import com.oviesAries.recipe.domain.recipe.dto.RecipeIngredientResponse;
import com.oviesAries.recipe.domain.recipe.dto.RecipeStepResponse;
import com.oviesAries.recipe.domain.user.dto.UserCreateRequest;
import com.oviesAries.recipe.domain.user.dto.UserResponseDto;
import com.oviesAries.recipe.domain.user.service.UserService;
import com.oviesAries.recipe.domain.utill.RecipeMapper;
import com.oviesAries.recipe.domain.entity.User;
import com.oviesAries.recipe.domain.entity.UserIngredient;
import com.oviesAries.recipe.domain.recipe.dto.RecipeIngredientDTO;
import com.oviesAries.recipe.domain.user.dto.UserCreateRequest;
import com.oviesAries.recipe.domain.user.dto.UserIngredientDTO;
import com.oviesAries.recipe.domain.user.dto.UserIngredientResponse;
import com.oviesAries.recipe.domain.user.dto.UserResponseDto;
import com.oviesAries.recipe.domain.user.service.UserService;
import com.oviesAries.recipe.domain.utill.RecipeMapper;
import com.oviesAries.recipe.domain.utill.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateRequest request) {
        User newUser = userService.createUser(request.getUserName(), request.getPassword());
        UserResponseDto response = new UserResponseDto(newUser.getId(), newUser.getUserName(), newUser.getPassword());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping("/{userId}/ingredients")
    public ResponseEntity<UserIngredientResponse> createRecipeIngredient(
            @PathVariable Long userId,
            @RequestBody UserIngredientDTO ingredientDTO) {

        UserIngredient savedStep = userService.addIngredientToUser(userId, UserMapper.toIngredientEntity(ingredientDTO));

        return new ResponseEntity<>(UserMapper.toIngredientResponse(savedStep), HttpStatus.CREATED);
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


    @GetMapping("/{userId}/ingredients")
    public ResponseEntity<List<UserIngredientResponse>> getAllIngredient(
            @PathVariable Long userId) {

        List<UserIngredient> recipeIngredients = userService.getAllUserIngredient(userId);

        List<UserIngredientResponse> response = recipeIngredients.stream()
                .map(UserMapper::toIngredientResponse)
                .collect(Collectors.toList());

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
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping("/{userId}/ingredients/{recipeIngredientId}")
    public ResponseEntity<UserIngredientResponse> deleteIngredient(
            @PathVariable Long userId,
            @PathVariable Integer recipeIngredientId) {
        userService.deleteUserIngredient(userId, recipeIngredientId);
        return ResponseEntity.noContent().build();
    }


}