package com.oviesAries.recipe.domain.recipe.service;

import com.oviesAries.recipe.domain.entity.Recipe;
import com.oviesAries.recipe.domain.recipe.dao.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe createRecipe(String recipeName) {
        Recipe recipe = Recipe.builder()
                .dishName(recipeName)
                .steps(new ArrayList<>())
                .build();

        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(String.valueOf(id)).orElse(null);
    }

}
