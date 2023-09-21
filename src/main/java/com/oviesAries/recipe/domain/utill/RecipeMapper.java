package com.oviesAries.recipe.domain.utill;

import com.oviesAries.recipe.domain.entity.Ingredient;
import com.oviesAries.recipe.domain.entity.Recipe;
import com.oviesAries.recipe.domain.entity.RecipeIngredient;
import com.oviesAries.recipe.domain.entity.RecipeStep;
import com.oviesAries.recipe.domain.recipe.dto.request.RecipeIngredientDTO;
import com.oviesAries.recipe.domain.recipe.dto.request.RecipeStepDTO;
import com.oviesAries.recipe.domain.recipe.dto.response.RecipeIngredientResponse;
import com.oviesAries.recipe.domain.recipe.dto.response.RecipeResponse;
import com.oviesAries.recipe.domain.recipe.dto.response.RecipeStepResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeMapper {

    // 중복 검증으로 한 후 DTO 반환하겠금 해도됨

    public static RecipeStep toStepEntity(RecipeStepDTO stepDTO) {
        return RecipeStep.builder()
                .description(stepDTO.getDescription())
                .imagePath(stepDTO.getImagePath())
                .build();
    }

    public static RecipeIngredient toIngredientEntity(RecipeIngredientDTO ingredientDTO) {
        return RecipeIngredient.builder()
                .ingredient(Ingredient.builder()
                        .name(ingredientDTO.getIngredientName())
                        .build())
                .quantity(ingredientDTO.getQuantity())
                .recipeIngredientId(ingredientDTO.getRecipeIngredientId())
                .build();
    }

    public static RecipeResponse toNewResponse(Recipe recipe) {
        if(recipe == null
                || recipe.getRecipeSteps() == null
                || recipe.getRecipeIngredients() == null) {
            return null;
        }

        return RecipeResponse.builder()
                .id(recipe.getId())
                .dishName(recipe.getDishName())
                .subtitle(recipe.getSubtitle())
                .totalTime(recipe.getTotalTime())
                .recipeSteps(new ArrayList<>()) // 비어있는 steps로 초기화
                .recipeIngredients(new ArrayList<>())
                .build();
    }

    public static RecipeStepResponse toStepResponse(RecipeStep savedStep) {
        return RecipeStepResponse.builder()
                .id(savedStep.getId())
                .stepOrder(savedStep.getStepOrder())
                .description(savedStep.getDescription())
                .imagePath(savedStep.getImagePath())
                .build();
    }

    public static RecipeIngredientResponse toIngredientResponse(RecipeIngredient ingredient) {
        return RecipeIngredientResponse.builder()
                .id(ingredient.getId())
                .ingredientName(ingredient.getIngredient().getName())
                .icon(ingredient.getIngredient().getIcon())
                .quantity(ingredient.getQuantity())
                .recipeIngredientId(ingredient.getRecipeIngredientId())
                .build();
    }

    public static List<RecipeStepDTO> toRecipeStepDTOList(List<RecipeStep> steps) {
        if (steps == null) {
            return new ArrayList<>();
        }
        return steps.stream()
                .map(step -> RecipeStepDTO.builder()
                        .stepOrder(step.getStepOrder())
                        .description(step.getDescription())
                        .imagePath(step.getImagePath())
                        .build())
                .collect(Collectors.toList());
    }

    public static List<RecipeIngredientDTO> toIngredientDTOList(List<RecipeIngredient> ingredients) {
        if (ingredients == null) {
            return new ArrayList<>();
        }
        return ingredients.stream()
                .map(ingredient -> RecipeIngredientDTO.builder()
                        .id(ingredient.getId())
                        .quantity(ingredient.getQuantity())
                        .recipeIngredientId(ingredient.getRecipeIngredientId())
                        .ingredientName(ingredient.getIngredient().getName())
                        .icon(ingredient.getIngredient().getIcon())
                        .build())
                .collect(Collectors.toList());
    }

    public static RecipeResponse toRecipeResponse(Recipe recipe) {
        return RecipeResponse.builder()
                .id(recipe.getId())
                .dishName(recipe.getDishName())
                .subtitle(recipe.getSubtitle())
                .totalTime(recipe.getTotalTime())
                .recipeSteps(toRecipeStepDTOList(recipe.getRecipeSteps()))
                .recipeIngredients(toIngredientDTOList(recipe.getRecipeIngredients()))
                .build();
    }

}
