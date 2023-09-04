package com.oviesAries.recipe.domain.recipe.service;

import com.oviesAries.recipe.domain.entity.Recipe;
import com.oviesAries.recipe.domain.recipe.dao.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceImplTest {

    @Autowired
    private RecipeService recipeService;

    @MockBean
    private RecipeRepository recipeRepository;

    @Test
    public void testGetAllRecipes() {
        Recipe recipe1 = Recipe.builder().dishName("Pizza").build();
        Recipe recipe2 = Recipe.builder().dishName("Pasta").build();

        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe1, recipe2));

        List<Recipe> result = recipeService.getAllRecipes();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testCreateRecipe() {
        Recipe recipe = Recipe.builder().dishName("Burger").build();

        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        Recipe result = recipeService.createRecipe("Burger");

        assertNotNull(result);
        assertEquals("Burger", result.getDishName());
    }

    @Test
    public void testGetRecipeById() {
        Recipe recipe = Recipe.builder().dishName("Tacos").id(1L).build();

        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        Recipe result = recipeService.getRecipeById(1L);

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getId());
    }
}
