package com.oviesAries.recipe.domain.recipe.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@Builder
public class RecipeResponse {

    private Long id;
    private String dishName;
    private String subtitle;
    private Integer totalTime;
    private List<RecipeStepDTO> recipeSteps;
    private List<RecipeIngredientDTO> recipeIngredients;

}
