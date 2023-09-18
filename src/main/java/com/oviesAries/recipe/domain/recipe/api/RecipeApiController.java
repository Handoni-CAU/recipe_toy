package com.oviesAries.recipe.domain.recipe.api;

import com.oviesAries.recipe.domain.entity.Recipe;
import com.oviesAries.recipe.domain.entity.RecipeIngredient;
import com.oviesAries.recipe.domain.entity.RecipeStep;
import com.oviesAries.recipe.domain.recipe.dto.request.RecipeCreateDTO;
import com.oviesAries.recipe.domain.recipe.dto.request.RecipeIngredientDTO;
import com.oviesAries.recipe.domain.recipe.dto.request.RecipeStepDTO;
import com.oviesAries.recipe.domain.recipe.dto.response.RecipeIngredientResponse;
import com.oviesAries.recipe.domain.recipe.dto.response.RecipeResponse;
import com.oviesAries.recipe.domain.recipe.dto.response.RecipeStepResponse;
import com.oviesAries.recipe.domain.recipe.service.RecipeService;
import com.oviesAries.recipe.domain.user.annotation.Authenticated;
import com.oviesAries.recipe.domain.user.annotation.Member;
import com.oviesAries.recipe.domain.user.domain.AuthPrincipal;
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

    @Member
    @GetMapping("/filter")
    public ResponseEntity<List<RecipeResponse>> getAllRecipesByUserIngredients(
            @Authenticated final AuthPrincipal authPrincipal
    ) {
        List<Recipe> recipes = recipeService.findRecipesByUserIngredients(authPrincipal.getId());

        List<RecipeResponse> response = recipes.stream()
                .map(RecipeMapper::toRecipeResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<RecipeResponse> retrieveRecipeByName(@PathVariable Long recipeId) {
        Recipe recipe = recipeService.getRecipeById(recipeId);

        if(recipe == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(RecipeMapper.toRecipeResponse(recipe));
    }

    @GetMapping("/{recipeId}/steps/{stepOrder}")
    public ResponseEntity<RecipeStepResponse> retrieveStepByStepOrder(
            @PathVariable Long recipeId,
            @PathVariable Integer stepOrder) {

        RecipeStep recipeStep = recipeService.getStepByRecipeIdAndStepOrder(recipeId, stepOrder);

        return ResponseEntity.ok(RecipeMapper.toStepResponse(recipeStep));
    }

    @GetMapping("/{recipeId}/ingredients")
    public ResponseEntity<List<RecipeIngredientResponse>> getAllIngredient(
            @PathVariable Long recipeId) {

        List<RecipeIngredient> recipeIngredients = recipeService.getAllRecipeIngredient(recipeId);

        List<RecipeIngredientResponse> response = recipeIngredients.stream()
                .map(RecipeMapper::toIngredientResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<RecipeResponse> createRecipe(@RequestBody RecipeCreateDTO request) {
        Recipe createdRecipe = recipeService.createRecipe(request);

        return new ResponseEntity<>(RecipeMapper.toNewResponse(createdRecipe), HttpStatus.CREATED);
    }

    @PostMapping("/{recipeId}/steps")
    public ResponseEntity<RecipeStepResponse> createRecipeStep(
            @PathVariable Long recipeId,
            @RequestBody RecipeStepDTO stepDTO) {

        RecipeStep savedStep = recipeService.addStepToRecipe(recipeId, RecipeMapper.toStepEntity(stepDTO));

        return new ResponseEntity<>(RecipeMapper.toStepResponse(savedStep), HttpStatus.CREATED);
    }

    @PostMapping("/{recipeId}/ingredients")
    public ResponseEntity<RecipeIngredientResponse> createRecipeIngredient(
            @PathVariable Long recipeId,
            @RequestBody RecipeIngredientDTO ingredientDTO) {

        RecipeIngredient savedStep = recipeService.addIngredientToRecipe(recipeId, RecipeMapper.toIngredientEntity(ingredientDTO));

        return new ResponseEntity<>(RecipeMapper.toIngredientResponse(savedStep), HttpStatus.CREATED);
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<RecipeResponse> deleteRecipe(@PathVariable Long recipeId) {
        recipeService.deleteRecipe(recipeId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{recipeId}/steps/{stepOrder}")
    public ResponseEntity<RecipeStepResponse> deleteRecipeStep(
            @PathVariable Long recipeId,
            @PathVariable Integer stepOrder) {
        recipeService.deleteRecipeStep(recipeId, stepOrder);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{recipeId}/ingredients/{recipeIngredientId}")
    public ResponseEntity<RecipeStepResponse> deleteIngredient(
            @PathVariable Long recipeId,
            @PathVariable Integer recipeIngredientId) {
        recipeService.deleteRecipeIngredient(recipeId, recipeIngredientId);
        return ResponseEntity.noContent().build();
    }

}
