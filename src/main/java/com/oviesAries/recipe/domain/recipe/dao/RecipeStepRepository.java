package com.oviesAries.recipe.domain.recipe.dao;


import com.oviesAries.recipe.domain.entity.RecipeStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {
}
