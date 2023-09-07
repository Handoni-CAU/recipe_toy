package com.oviesAries.recipe.domain.recipe.service;

import com.oviesAries.recipe.domain.entity.Recipe;
import com.oviesAries.recipe.domain.entity.RecipeStep;


import java.util.List;

public interface RecipeService {

    List<Recipe> getAllRecipes();

    Recipe createRecipe(String recipeName);

    RecipeStep addStepToRecipe(String dishName, RecipeStep step);

    Recipe createRecipeStep(String stepName);

    Recipe getRecipeById(Long id);

    Recipe getRecipeByDishName(String dishName);


}
