package com.oviesAries.recipe.domain.user.dto.request;

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
public class UserIngredientDTO {

    private Long id;
    private String ingredientName;
    private String icon;
    private Integer quantity;
    private Integer userIngredientId;

    public static UserIngredientDTO fromEntity(UserIngredient userIngredient) {
        UserIngredientDTO dto = new UserIngredientDTO();

        dto.setId(userIngredient.getId());
        dto.setIngredientName(userIngredient.getIngredient().getName());
        dto.setIcon(userIngredient.getIngredient().getIcon());
        dto.setQuantity(userIngredient.getQuantity());
        dto.setUserIngredientId(userIngredient.getUserIngredientId());

        return dto;
    }

}
