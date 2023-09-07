package com.oviesAries.recipe.domain.recipe.service;

import com.oviesAries.recipe.domain.entity.Recipe;
import com.oviesAries.recipe.domain.entity.RecipeStep;
import com.oviesAries.recipe.domain.recipe.dao.RecipeRepository;
import com.oviesAries.recipe.domain.recipe.dao.RecipeStepRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;

    private final RecipeStepRepository recipeStepRepository;

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    @Transactional
    public Recipe createRecipe(String recipeName) {
        Recipe recipe = Recipe.builder()
                .dishName(recipeName)
                .steps(new ArrayList<>())
                .build();

        recipeRepository.save(recipe);

        return recipe;
    }

    @Override
    public Recipe createRecipeStep(String stepName) {
        return null;
    }

    @Override
    public RecipeStep addStepToRecipe(String dishName, RecipeStep step) {
        Recipe recipe = recipeRepository.findByDishName(dishName)
                .orElseThrow(() -> new NoSuchElementException("Recipe with dish name: " + dishName + " does not exist."));

        int currentStepCount = recipe.getSteps().size();
        step.setStepOrder(currentStepCount + 1);

        step.setRecipe(recipe);

        if (recipe.getSteps() == null) {
            recipe.setSteps(new ArrayList<>());
        }

        recipe.getSteps().add(step);
        return recipeStepRepository.save(step);
    }

    @Override
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    @Override
    public Recipe getRecipeByDishName(String dishName) {
        return recipeRepository.findByDishName(dishName).orElse(null);
    }

}
