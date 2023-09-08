package com.oviesAries.recipe.domain.recipe.api;

import com.oviesAries.recipe.domain.entity.Recipe;
import com.oviesAries.recipe.domain.entity.RecipeIngredient;
import com.oviesAries.recipe.domain.entity.RecipeStep;
import com.oviesAries.recipe.domain.recipe.dto.*;
import com.oviesAries.recipe.domain.recipe.service.RecipeService;
import com.oviesAries.recipe.domain.utill.RecipeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                .map(RecipeMapper::toRecipeResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{dishName}")
    public ResponseEntity<RecipeResponse> retrieveRecipeByName(@PathVariable String dishName) {
        Recipe recipe = recipeService.getRecipeByDishName(dishName);

        if(recipe == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(RecipeMapper.toRecipeResponse(recipe));
    }

    @GetMapping("/{dishName}/steps/{stepOrder}")
    public ResponseEntity<RecipeStepResponse> retrieveStepByStepOrder(
            @PathVariable Integer stepOrder) {

        RecipeStep recipeStep = recipeService.getStepByStepOrder(stepOrder);

        return ResponseEntity.ok(RecipeMapper.toStepResponse(recipeStep));
    }


    @PostMapping("/{dishName}/steps")
    public ResponseEntity<RecipeStepResponse> createRecipeStep(
            @PathVariable String dishName,
            @RequestBody RecipeStepDTO stepDTO) {

        RecipeStep savedStep = recipeService.addStepToRecipe(dishName, RecipeMapper.toStepEntity(stepDTO));

        return new ResponseEntity<>(RecipeMapper.toStepResponse(savedStep), HttpStatus.CREATED);
    }

    @PostMapping("/{dishName}/ingredients")
    public ResponseEntity<RecipeIngredientResponse> createRecipeIngredient(
            @PathVariable String dishName,
            @RequestBody RecipeIngredientDTO ingredientDTO) {

        RecipeIngredient savedStep = recipeService.addIngredientToRecipe(dishName, RecipeMapper.toIngredientEntity(ingredientDTO));

        return new ResponseEntity<>(RecipeMapper.toIngredientResponse(savedStep), HttpStatus.CREATED);
    }

    @PostMapping("/create")
    public ResponseEntity<RecipeResponse> createRecipe(@RequestBody RecipeCreateDTO request) {
        Recipe createdRecipe = recipeService.createRecipe(request.getDishName());

        return new ResponseEntity<>(RecipeMapper.toNewResponse(createdRecipe), HttpStatus.CREATED);
    }

    @DeleteMapping("/{dishName}")
    public ResponseEntity<RecipeResponse> deleteRecipe(@PathVariable String dishName) {
        recipeService.deleteRecipe(dishName);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{dishName}/steps/{stepOrder}")
    public ResponseEntity<RecipeStepResponse> deleteRecipeStep(@PathVariable Integer stepOrder) {
        recipeService.deleteRecipeStep(stepOrder);
        return ResponseEntity.noContent().build();
    }






}
