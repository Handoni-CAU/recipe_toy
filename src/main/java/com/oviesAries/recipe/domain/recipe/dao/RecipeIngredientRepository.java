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

    @Query("SELECT r.recipe FROM RecipeIngredient r " +
            "LEFT JOIN UserIngredient u ON r.ingredient.name = u.ingredient.name AND u.user.id = :userId " +
            "WHERE (u.ingredient.name IS NULL OR r.quantity <= u.quantity) " +
            "GROUP BY r.recipe " +
            "HAVING COUNT(DISTINCT r.ingredient.name) = COUNT(DISTINCT u.ingredient.name)")
    List<Recipe> findRecipesByUserId(@Param("userId") Long userId);

}
