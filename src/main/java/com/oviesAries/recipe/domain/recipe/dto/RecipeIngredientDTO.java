package com.oviesAries.recipe.domain.recipe.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.oviesAries.recipe.domain.entity.RecipeIngredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredientDTO {

    private Long id;
    private String ingredientName;
    private String icon;
    private Integer quantity;
    private Integer recipeIngredientId;

    public static RecipeIngredientDTO fromEntity(RecipeIngredient recipeIngredient) {
        RecipeIngredientDTO dto = new RecipeIngredientDTO();

        dto.setId(recipeIngredient.getId());
        dto.setIngredientName(recipeIngredient.getIngredient().getName());
        dto.setIcon(recipeIngredient.getIngredient().getIcon());
        dto.setQuantity(recipeIngredient.getQuantity());
        dto.setRecipeIngredientId(recipeIngredient.getRecipeIngredientId());

        return dto;
    }


}
