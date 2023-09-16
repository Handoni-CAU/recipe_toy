package com.oviesAries.recipe.domain.utill;

import com.oviesAries.recipe.domain.entity.Ingredient;
import com.oviesAries.recipe.domain.entity.UserIngredient;
import com.oviesAries.recipe.domain.user.dto.request.UserIngredientDTO;
import com.oviesAries.recipe.domain.user.dto.response.UserIngredientResponse;

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
