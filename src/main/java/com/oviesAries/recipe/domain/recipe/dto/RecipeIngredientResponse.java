package com.oviesAries.recipe.domain.recipe.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Blob;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@Builder
public class RecipeIngredientResponse {

    private Long id;
    private String ingredientName;
    private String icon;
    private Integer quantity;
    private Integer recipeIngredientId;

}
