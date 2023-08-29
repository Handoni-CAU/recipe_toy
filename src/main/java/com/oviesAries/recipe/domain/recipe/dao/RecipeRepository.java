package com.oviesAries.recipe.domain.recipe.dao;

import com.oviesAries.recipe.domain.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, String> {
}
