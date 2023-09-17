package com.oviesAries.recipe.domain.user.domain;


import com.oviesAries.recipe.domain.dao.IngredientRepository;
import com.oviesAries.recipe.domain.entity.Ingredient;
import com.oviesAries.recipe.domain.entity.UserIngredient;
import com.oviesAries.recipe.domain.user.dto.request.UserIngredientDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class IngredientMapper {

    private final IngredientRepository ingredientRepository;


    public UserIngredient mapFrom(final Long userId, final List<UserIngredientDTO> request) {
        return null;
    }


}
