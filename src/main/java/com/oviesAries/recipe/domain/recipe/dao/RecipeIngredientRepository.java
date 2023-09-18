package com.oviesAries.recipe.domain.recipe.dao;

import com.oviesAries.recipe.domain.entity.Recipe;
import com.oviesAries.recipe.domain.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {

    Optional<RecipeIngredient> findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    List<RecipeIngredient> findByRecipeId(Long id);

}
