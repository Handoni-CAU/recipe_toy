package com.oviesAries.recipe.domain.utill;

import com.oviesAries.recipe.domain.entity.Ingredient;
import com.oviesAries.recipe.domain.entity.UserIngredient;
import com.oviesAries.recipe.domain.user.dto.request.UserIngredientDTO;
import com.oviesAries.recipe.domain.user.dto.response.UserIngredientResponse;

public class UserMapper {

    public static UserIngredient toIngredientEntity(UserIngredientDTO ingredientDTO) {
        return UserIngredient.builder()
                .ingredient(Ingredient.builder()
                        .name(ingredientDTO.getProductId())
                        .build())
                .quantity(ingredientDTO.getQuantity())
                .build();
    }

    public static UserIngredientResponse toIngredientResponse(UserIngredient ingredient) {

        UserIngredientResponse response = UserIngredientResponse.from(ingredient.getUser());

        return UserIngredientResponse.builder()
                .id(response.getId())
                .ingredients(response.getIngredients())
                .build();
    }

}
