package com.oviesAries.recipe.domain.recipe.service;

import com.oviesAries.recipe.domain.entity.Recipe;
import com.oviesAries.recipe.domain.recipe.dao.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RecipeService {

    List<Recipe> getAllRecipes();

    Recipe createRecipe(String recipeName);

    Recipe getRecipeById(Long id);

    Recipe getRecipeByDishName(String dishName);


}
