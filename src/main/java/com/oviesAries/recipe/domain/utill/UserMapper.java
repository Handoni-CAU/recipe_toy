package com.oviesAries.recipe.domain.utill;

import com.oviesAries.recipe.domain.entity.Ingredient;
import com.oviesAries.recipe.domain.entity.RecipeIngredient;
import com.oviesAries.recipe.domain.entity.UserIngredient;
import com.oviesAries.recipe.domain.recipe.dto.RecipeIngredientDTO;
import com.oviesAries.recipe.domain.recipe.dto.RecipeIngredientResponse;
import com.oviesAries.recipe.domain.user.dto.UserIngredientDTO;
import com.oviesAries.recipe.domain.user.dto.UserIngredientResponse;

public class UserMapper {

    public static UserIngredient toIngredientEntity(UserIngredientDTO ingredientDTO) {
        return UserIngredient.builder()
                .ingredient(Ingredient.builder()
                        .name(ingredientDTO.getIngredientName())
                        .build())
                .quantity(ingredientDTO.getQuantity())
                .userIngredientId(ingredientDTO.getUserIngredientId())
                .build();
    }

    public static UserIngredientResponse toIngredientResponse(UserIngredient ingredient) {
        return UserIngredientResponse.builder()
                .id(ingredient.getId())
                .ingredientName(ingredient.getIngredient().getName())
                .icon(ingredient.getIngredient().getIcon())
                .quantity(ingredient.getQuantity())
                .userIngredientId(ingredient.getUserIngredientId())
                .build();
    }

}
