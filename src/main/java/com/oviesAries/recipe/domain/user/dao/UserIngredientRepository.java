package com.oviesAries.recipe.domain.user.dao;

import com.oviesAries.recipe.domain.entity.UserIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserIngredientRepository extends JpaRepository<UserIngredient, Long> {

    List<UserIngredient> findByUserId(Long id);

}
