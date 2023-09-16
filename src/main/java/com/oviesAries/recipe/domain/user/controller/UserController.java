package com.oviesAries.recipe.domain.user.controller;

import com.oviesAries.recipe.domain.entity.User;
import com.oviesAries.recipe.domain.user.application.AuthService;
import com.oviesAries.recipe.domain.user.dto.request.LoginRequest;
import com.oviesAries.recipe.domain.user.dto.request.SignUpRequest;
import com.oviesAries.recipe.domain.user.dto.request.UserResponseDTO;
import com.oviesAries.recipe.domain.user.dto.response.LoginResponse;
import com.oviesAries.recipe.domain.user.dto.response.SignupResponse;
import com.oviesAries.recipe.domain.user.dto.response.UserCreateRequest;
import com.oviesAries.recipe.domain.user.service.UserService;
import com.oviesAries.recipe.domain.entity.UserIngredient;
import com.oviesAries.recipe.domain.user.dto.request.UserIngredientDTO;
import com.oviesAries.recipe.domain.user.dto.response.UserIngredientResponse;
import com.oviesAries.recipe.domain.utill.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> createUser(@Valid @RequestBody SignUpRequest request) {
        final Long memberId = authService.signUp(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(SignupResponse.from(memberId));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody final LoginRequest request) {
        final LoginResponse loginResponse = authService.loginMember(request);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/{userId}/ingredients")
    public ResponseEntity<UserIngredientResponse> createRecipeIngredient(
            @PathVariable Long userId,
            @RequestBody UserIngredientDTO ingredientDTO) {

        UserIngredient savedStep = userService.addIngredientToUser(userId, UserMapper.toIngredientEntity(ingredientDTO));

        return new ResponseEntity<>(UserMapper.toIngredientResponse(savedStep), HttpStatus.CREATED);
    }

//    @GetMapping("username/{userName}")
//    public ResponseEntity<UserResponseDTO> getUserByUserName(@PathVariable String userName) {
//        User user = userService.getUserByUserName(userName);
//        if (user == null){
//            return ResponseEntity.notFound().build();
//        }
//        UserResponseDTO response = new UserResponseDTO(user.getId(), user.getUserName(), user.getPassword());
//        return ResponseEntity.ok(response);
//    }


    @GetMapping("/{userId}/ingredients")
    public ResponseEntity<List<UserIngredientResponse>> getAllIngredient(
            @PathVariable Long userId) {

        List<UserIngredient> recipeIngredients = userService.getAllUserIngredient(userId);

        List<UserIngredientResponse> response = recipeIngredients.stream()
                .map(UserMapper::toIngredientResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

//    @GetMapping("id/{id}")
//    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
//        User user = userService.getUserById(id);
//        if (user == null){
//            return ResponseEntity.notFound().build();
//        }
//        UserResponseDTO response = new UserResponseDTO(user.getId(), user.getUserName(), user.getPassword());
//        return ResponseEntity.ok(response);
//    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
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