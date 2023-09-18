package com.oviesAries.recipe.domain.recipe.service;

import com.oviesAries.recipe.domain.entity.*;
import com.oviesAries.recipe.domain.dao.IngredientRepository;
import com.oviesAries.recipe.domain.recipe.dao.RecipeIngredientRepository;
import com.oviesAries.recipe.domain.recipe.dao.RecipeRepository;
import com.oviesAries.recipe.domain.recipe.dao.RecipeStepRepository;
import com.oviesAries.recipe.domain.recipe.dto.request.RecipeCreateDTO;
import com.oviesAries.recipe.domain.user.dao.UserIngredientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;
    private final RecipeStepRepository recipeStepRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final UserIngredientRepository userIngredientRepository;

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    @Transactional
    public Recipe createRecipe(RecipeCreateDTO createDTO) {
        Recipe recipe = Recipe.builder()
                .dishName(createDTO.getDishName())
                .subtitle(createDTO.getSubtitle())
                .recipeSteps(new ArrayList<>())
                .build();

        recipeRepository.save(recipe);

        return recipe;
    }

    @Override
    public RecipeStep addStepToRecipe(Long id, RecipeStep step) {
        try {
            Recipe recipe = recipeRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Recipe with dish name: " + id + " does not exist."));

            int currentStepCount = recipe.getRecipeSteps().size();
            step.setStepOrder(currentStepCount + 1);

            step.setRecipe(recipe);

            if (recipe.getRecipeSteps() == null) {
                recipe.setRecipeSteps(new ArrayList<>());
            }

            recipe.getRecipeSteps().add(step);
            return recipeStepRepository.save(step);
        } catch (OptimisticLockingFailureException ex) {
            throw new ConcurrentModificationException("사용자가 변경 중", ex);
        }
    }


    @Override
    public RecipeIngredient addIngredientToRecipe(Long id, RecipeIngredient recipeIngredient) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Recipe with dish name: " + id + " does not exist."));

        Ingredient ingredient = Ingredient.builder()
                .name(recipeIngredient.getIngredient().getName())
                .icon(recipeIngredient.getIngredient().getIcon())
                .build();

        int currentSequenceCount = recipe.getRecipeIngredients().size();
        recipeIngredient.setRecipeIngredientId(currentSequenceCount + 1);

        Ingredient savedIngredient = ingredientRepository.save(ingredient);

        recipeIngredient.setIngredient(savedIngredient);
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
    public RecipeStep getStepByRecipeIdAndStepOrder(Long recipeId, Integer stepOrder) {
        return recipeStepRepository.findByRecipeIdAndStepOrder(recipeId, stepOrder)
                .orElseThrow(() -> new EntityNotFoundException("Recipe step not found"));
    }

    @Override
    public List<RecipeIngredient> getAllRecipeIngredient(Long id) {
        return recipeIngredientRepository.findByRecipeId(id);
    }


    @Override
    public RecipeIngredient getRecipeIngredientById(Long id) {
        return recipeIngredientRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteRecipe(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if (!recipeOptional.isPresent()) {
            throw new EntityNotFoundException("Recipe with ID " + recipeOptional + " not found.");
        }
        recipeRepository.delete(recipeOptional.get());
    }
    @Override
    public void deleteRecipeIngredient(Long recipeId, Integer ingredientOrderToRemove) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NoSuchElementException("Recipe with ID: " + recipeId + " does not exist."));

        List<RecipeIngredient> ingredients = recipe.getRecipeIngredients();

        RecipeIngredient ingredientToRemove = null;
        for (RecipeIngredient ingredient : ingredients) {
            if (ingredient.getRecipeIngredientId().equals(ingredientOrderToRemove)) {
                ingredientToRemove = ingredient;
                break;
            }
        }
        if (ingredientToRemove != null) {
            ingredients.remove(ingredientToRemove);
            recipeIngredientRepository.delete(ingredientToRemove);
        }

        for (RecipeIngredient ingredient : ingredients) {
            if (ingredient.getRecipeIngredientId() > ingredientOrderToRemove) {
                ingredient.setRecipeIngredientId(ingredient.getRecipeIngredientId() - 1);
                recipeIngredientRepository.save(ingredient);
            }
        }
    }


    @Override
    public void deleteRecipeStep(Long recipeId, Integer stepOrderToRemove) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NoSuchElementException("Recipe with ID: " + recipeId + " does not exist."));

        List<RecipeStep> steps = recipe.getRecipeSteps();

        RecipeStep stepToRemove = null;
        for (RecipeStep step : steps) {
            if (step.getStepOrder().equals(stepOrderToRemove)) {
                stepToRemove = step;
                break;
            }
        }
        if (stepToRemove != null) {
            steps.remove(stepToRemove);
            recipeStepRepository.delete(stepToRemove);
        }

        for (RecipeStep step : steps) {
            if (step.getStepOrder() > stepOrderToRemove) {
                step.setStepOrder(step.getStepOrder() - 1);
                recipeStepRepository.save(step);
            }
        }
    }

    public List<Recipe> findRecipesByUserIngredients(Long userId) {
        return recipeRepository.findRecipesByUserId(userId);
    }

}
