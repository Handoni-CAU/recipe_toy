package com.oviesAries.recipe.domain.recipe.service;

import com.oviesAries.recipe.domain.entity.Recipe;
import com.oviesAries.recipe.domain.entity.RecipeIngredient;
import com.oviesAries.recipe.domain.entity.RecipeStep;
import com.oviesAries.recipe.domain.recipe.dto.request.RecipeCreateDTO;


import java.util.List;

public interface RecipeService {

    List<Recipe> getAllRecipes();

    Recipe createRecipe(RecipeCreateDTO createDT);

    Recipe getRecipeById(Long id);

    Recipe getRecipeByDishName(String dishName);

    void deleteRecipe(Long id);

    RecipeStep getStepByRecipeIdAndStepOrder(Long recipeId, Integer stepOrder);

    RecipeStep addStepToRecipe(Long id, RecipeStep step);

    void deleteRecipeStep(Long recipeId, Integer stepOrder);

    List<RecipeIngredient> getAllRecipeIngredient(Long id);

    RecipeIngredient getRecipeIngredientById(Long id);

    RecipeIngredient addIngredientToRecipe(Long id, RecipeIngredient ingredient);

    void deleteRecipeIngredient(Long recipeId, Integer ingredientOrderToRemove);


}
