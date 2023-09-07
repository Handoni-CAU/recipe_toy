package com.oviesAries.recipe.domain.recipe.api;

import com.oviesAries.recipe.domain.entity.Recipe;
import com.oviesAries.recipe.domain.entity.RecipeStep;
import com.oviesAries.recipe.domain.recipe.dto.RecipeCreateDTO;
import com.oviesAries.recipe.domain.recipe.dto.RecipeResponse;
import com.oviesAries.recipe.domain.recipe.dto.RecipeStepDTO;
import com.oviesAries.recipe.domain.recipe.dto.RecipeStepResponse;
import com.oviesAries.recipe.domain.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeApiController {

    private final RecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<RecipeResponse>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();

        List<RecipeResponse> response = recipes.stream()
                .map(recipe -> {
                    List<RecipeStepDTO> stepResponses = recipe.getSteps().stream()
                            .map(step -> RecipeStepDTO.builder()
                                    .stepOrder(step.getStepOrder())
                                    .description(step.getDescription())
                                    .imagePath(step.getImagePath())
                                    .build())
                            .collect(Collectors.toList());

                    return RecipeResponse.builder()
                            .id(recipe.getId())
                            .dishName(recipe.getDishName())
                            .totalTime(recipe.getTotalTime())
                            .recipeSteps(stepResponses)
                            .build();
                })
                .collect(Collectors.toList());


        return ResponseEntity.ok(response);
    }

    @GetMapping("/{dishName}")
    public ResponseEntity<RecipeResponse> retrieveRecipeByName(@PathVariable String dishName) {
        Recipe recipe = recipeService.getRecipeByDishName(dishName);

        if(recipe == null) {
            return ResponseEntity.notFound().build();
        }

        List<RecipeStepDTO> stepResponses = recipe.getSteps().stream()
                .map(step -> RecipeStepDTO.builder()
                        .stepOrder(step.getStepOrder())
                        .description(step.getDescription())
                        .imagePath(step.getImagePath())
                        .build())
                .collect(Collectors.toList());

        RecipeResponse response = RecipeResponse.builder()
                .id(recipe.getId())
                .dishName(recipe.getDishName())
                .totalTime(recipe.getTotalTime())
                .recipeSteps(stepResponses)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{dishName}/steps")
    public ResponseEntity<RecipeStepResponse> createRecipeStep(
            @PathVariable String dishName,
            @RequestBody RecipeStepDTO stepDTO) {

        RecipeStep newStep = RecipeStep.builder()
                .description(stepDTO.getDescription())
                .imagePath(stepDTO.getImagePath())
                .build();

        RecipeStep savedStep = recipeService.addStepToRecipe(dishName, newStep);

        RecipeStepResponse response = RecipeStepResponse.builder()
                .id(savedStep.getId())
                .stepOrder(savedStep.getStepOrder())
                .description(savedStep.getDescription())
                .imagePath(savedStep.getImagePath())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping("/create")
    public ResponseEntity<RecipeResponse> createRecipe(@RequestBody RecipeCreateDTO request) {
        Recipe createdRecipe = recipeService.createRecipe(request.getDishName());

        RecipeResponse response = RecipeResponse.builder()
                .id(createdRecipe.getId())
                .dishName(createdRecipe.getDishName())
                .totalTime(createdRecipe.getTotalTime())
                .recipeSteps(new ArrayList<>())
                .build();


        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
