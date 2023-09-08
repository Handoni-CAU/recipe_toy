package com.oviesAries.recipe.domain.recipe.dao;


import com.oviesAries.recipe.domain.entity.Recipe;
import com.oviesAries.recipe.domain.entity.RecipeStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {
    Optional<RecipeStep> findByStepOrder(Integer stepOrder);

}
