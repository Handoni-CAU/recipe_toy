package com.oviesAries.recipe.domain.recipe.api;

import com.oviesAries.recipe.domain.entity.Recipe;
import com.oviesAries.recipe.domain.recipe.dto.RecipeCreateRequest;
import com.oviesAries.recipe.domain.recipe.dto.RecipeResponse;
import com.oviesAries.recipe.domain.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
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
                .map(recipe -> new RecipeResponse(recipe.getId(), recipe.getDishName(), recipe.getTotalTime()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> retrieveRecipeById(@PathVariable Long id) {
        Recipe recipe = recipeService.getRecipeById(id);

        if(recipe == null) {
            return ResponseEntity.notFound().build();
        }

        RecipeResponse response = new RecipeResponse(recipe.getId(), recipe.getDishName(), recipe.getTotalTime());

        return ResponseEntity.ok(response);
    }


    @PostMapping("/create")
    public ResponseEntity<RecipeResponse> createRecipe(@RequestBody RecipeCreateRequest request) {
        Recipe createdRecipe = recipeService.createRecipe(request.getDishName()); // 필드명 변경

        RecipeResponse response = new RecipeResponse(createdRecipe.getId(), createdRecipe.getDishName(), createdRecipe.getTotalTime()); // 필드명 변경

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
