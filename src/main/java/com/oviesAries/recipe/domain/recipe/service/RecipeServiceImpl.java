package com.oviesAries.recipe.domain.recipe.service;

import com.oviesAries.recipe.domain.entity.Ingredient;
import com.oviesAries.recipe.domain.entity.Recipe;
import com.oviesAries.recipe.domain.entity.RecipeIngredient;
import com.oviesAries.recipe.domain.entity.RecipeStep;
import com.oviesAries.recipe.domain.recipe.dao.IngredientRepository;
import com.oviesAries.recipe.domain.recipe.dao.RecipeIngredientRepository;
import com.oviesAries.recipe.domain.recipe.dao.RecipeRepository;
import com.oviesAries.recipe.domain.recipe.dao.RecipeStepRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;
    private final RecipeStepRepository recipeStepRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    private final IngredientRepository ingredientRepository;

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    @Transactional
    public Recipe createRecipe(String recipeName) {
        Recipe recipe = Recipe.builder()
                .dishName(recipeName)
                .recipeSteps(new ArrayList<>())
                .build();

        recipeRepository.save(recipe);

        return recipe;
    }

    @Override
    public RecipeStep addStepToRecipe(String dishName, RecipeStep step) {
        Recipe recipe = recipeRepository.findByDishName(dishName)
                .orElseThrow(() -> new NoSuchElementException("Recipe with dish name: " + dishName + " does not exist."));

        int currentStepCount = recipe.getRecipeSteps().size();
        step.setStepOrder(currentStepCount + 1);

        step.setRecipe(recipe);

        if (recipe.getRecipeSteps() == null) {
            recipe.setRecipeSteps(new ArrayList<>());
        }

        recipe.getRecipeSteps().add(step);
        return recipeStepRepository.save(step);
    }

    @Override
    public RecipeIngredient addIngredientToRecipe(String dishName, RecipeIngredient recipeIngredient) {
        Recipe recipe = recipeRepository.findByDishName(dishName)
                .orElseThrow(() -> new NoSuchElementException("Recipe with dish name: " + dishName + " does not exist."));

        Ingredient ingredient = Ingredient.builder()
                .name(recipeIngredient.getIngredient().getName())
                .icon(recipeIngredient.getIngredient().getIcon())
                .build();

        Ingredient savedIngredient = ingredientRepository.save(ingredient); // 저장된 Ingredient 인스턴스를 반환받습니다.

        recipeIngredient.setIngredient(savedIngredient); // 영속 상태의 Ingredient 인스턴스를 설정해줍니다.
        recipeIngredient.setRecipe(recipe);

        if (recipe.getRecipeIngredients() == null) {
            recipe.setRecipeIngredients(new ArrayList<>());
        }

        recipe.getRecipeIngredients().add(recipeIngredient);
        return recipeIngredientRepository.save(recipeIngredient);
    }


    @Override
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    @Override
    public Recipe getRecipeByDishName(String dishName) {
        return recipeRepository.findByDishName(dishName).orElse(null);
    }

    @Override
    public RecipeStep getStepByStepOrder(Integer stepOrder) {
        return recipeStepRepository.findByStepOrder(stepOrder).orElse(null);
    }

    @Override
    public void deleteRecipe(String dishName) {
        Optional<Recipe> stepOptional = recipeRepository.findByDishName(dishName);
        if (!stepOptional.isPresent()) {
            throw new EntityNotFoundException("Recipe with ID " + stepOptional + " not found.");
        }
        recipeRepository.delete(stepOptional.get());
    }

    @Override
    public void deleteRecipeStep(Integer stepOrder) {
        Optional<RecipeStep> stepOptional = recipeStepRepository.findByStepOrder(stepOrder);
        if (!stepOptional.isPresent()) {
            throw new EntityNotFoundException("RecipeStep with ID " + stepOptional + " not found.");
        }
        recipeStepRepository.delete(stepOptional.get());
    }
}
