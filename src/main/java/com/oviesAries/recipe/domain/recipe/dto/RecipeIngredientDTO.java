package com.oviesAries.recipe.domain.recipe.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.oviesAries.recipe.domain.entity.RecipeIngredient;
import com.oviesAries.recipe.domain.utill.BlobConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.util.Base64;

@Data
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredientDTO {

    private String ingredientName;
    private String icon;
    private int quantity;

    public static RecipeIngredientDTO fromEntity(RecipeIngredient recipeIngredient) {
        RecipeIngredientDTO dto = new RecipeIngredientDTO();

        dto.setIngredientName(recipeIngredient.getIngredient().getName());
        dto.setIcon(BlobConverter.blobToString(recipeIngredient.getIngredient().getIcon()));
        dto.setQuantity(recipeIngredient.getQuantity());

        return dto;
    }


}
