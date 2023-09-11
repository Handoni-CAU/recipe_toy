package com.oviesAries.recipe.domain.recipe.dao;

import com.oviesAries.recipe.domain.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}
