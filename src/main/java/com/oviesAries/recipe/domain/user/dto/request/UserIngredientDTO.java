package com.oviesAries.recipe.domain.user.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.oviesAries.recipe.domain.entity.UserIngredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
public class UserIngredientDTO {

    @NotNull(message = "재료 이름은 필수")
    private String productId;

    @NotNull(message = "재료 아이콘은 필수")
    private String icon;

    @NotNull(message = "재료 수량은 필수값")
    private Long quantity;

    public static UserIngredientDTO of(
            final String productId,
            final String icon,
            final Long quantity
            ) {
        return new UserIngredientDTO(productId, icon, quantity);
    }


}
