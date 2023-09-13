package com.oviesAries.recipe.domain.recipe.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
public class RecipeStepResponse {

    private Long id;
    private Integer stepOrder;
    private String description;
    private String imagePath;

}
