package com.oviesAries.recipe.domain.user.service;

import com.oviesAries.recipe.domain.entity.User;
import com.oviesAries.recipe.domain.entity.UserIngredient;

import java.util.List;

public interface UserService {
    User createUser(String userName, String password);

    User getUserByUserName(String userName);

    void deleteUser(Long id);

    User getUserById(Long id);

    List<UserIngredient> getAllUserIngredient(Long id);

    UserIngredient getUserIngredientById(Long id);

    UserIngredient addIngredientToUser(Long userId, UserIngredient ingredient);

    void deleteUserIngredient(Long userId, Integer ingredientOrderToRemove);


}
