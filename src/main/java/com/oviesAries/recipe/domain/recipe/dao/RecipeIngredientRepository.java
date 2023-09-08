package com.oviesAries.recipe.domain.recipe.dao;

import com.oviesAries.recipe.domain.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {


}
