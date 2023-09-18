package com.oviesAries.recipe.domain.user.service;

import com.oviesAries.recipe.domain.entity.User;
import com.oviesAries.recipe.domain.entity.UserIngredient;
import com.oviesAries.recipe.domain.user.domain.EncodedPassword;
import com.oviesAries.recipe.domain.user.dto.request.UserIngredientDTO;

import java.util.List;

public interface UserService {
    User createUser(String username, EncodedPassword password);

    User getUserByUserName(String userName);

    void deleteUserById(Long id);

    User getUserById(Long id);

    List<UserIngredient> getAllUserIngredient(Long id);

    UserIngredient getUserIngredientById(Long id);


    void deleteUserIngredient(Long userId, Integer ingredientOrderToRemove);

    UserIngredient addIngredient(UserIngredientDTO request, Long id);
}
