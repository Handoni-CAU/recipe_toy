package com.oviesAries.recipe.domain.user.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.oviesAries.recipe.domain.entity.User;
import com.oviesAries.recipe.domain.entity.UserIngredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
public class UserIngredientResponse {

    private Long id;
    private List<IngredientResponse> ingredients;

    public static UserIngredientResponse of(
            final Long id,
            final List<IngredientResponse> ingredients
    ) {
        return new UserIngredientResponse(id, ingredients);
    }

    public static UserIngredientResponse from(final User user) {
        List<IngredientResponse> ingredientResponses = user.getUserIngredients().stream()
                .map(IngredientResponse::from)
                .collect(Collectors.toUnmodifiableList());

        return UserIngredientResponse.of(
                user.getId(),
                ingredientResponses
        );

    }


}
