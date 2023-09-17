package com.oviesAries.recipe.domain.user.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.oviesAries.recipe.domain.entity.UserIngredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
public class IngredientResponse {

    private Long id;
    private String ingredientName;
    private String icon;
    private Integer quantity;
    private Integer userIngredientId;

    public static IngredientResponse of(
            final Long id,
            final String ingredientName,
            final String icon,
            final Integer quantity,
            final Integer userIngredientId
    ) {
        return new IngredientResponse(id, ingredientName, icon, quantity, userIngredientId);
    }

    public static IngredientResponse from(final UserIngredient ingredient) {
        return IngredientResponse.of(
                ingredient.getId(),
                ingredient.getIngredient().getName(),
                ingredient.getIngredient().getIcon(),
                ingredient.getQuantity(),
                ingredient.getUserIngredientId()
        );
    }
}
