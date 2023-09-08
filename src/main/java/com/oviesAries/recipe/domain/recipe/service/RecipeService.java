package com.oviesAries.recipe.domain.recipe.service;

import com.oviesAries.recipe.domain.entity.Recipe;
import com.oviesAries.recipe.domain.entity.RecipeIngredient;
import com.oviesAries.recipe.domain.entity.RecipeStep;


import java.util.List;

public interface RecipeService {

    List<Recipe> getAllRecipes();

    Recipe createRecipe(String recipeName);

    Recipe getRecipeById(Long id);

    Recipe getRecipeByDishName(String dishName);

    void deleteRecipe(String dishName);

    RecipeStep getStepByStepOrder(Integer stepOrder);

    RecipeStep addStepToRecipe(String dishName, RecipeStep step);
    RecipeIngredient addIngredientToRecipe(String dishName, RecipeIngredient ingredient);

    void deleteRecipeStep(Integer stepOrder);


}
