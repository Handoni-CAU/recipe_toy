package com.oviesAries.recipe.global.config;

import com.oviesAries.recipe.domain.entity.*;
import com.oviesAries.recipe.domain.recipe.dao.IngredientRepository;
import com.oviesAries.recipe.domain.recipe.dao.RecipeIngredientRepository;
import com.oviesAries.recipe.domain.recipe.dao.RecipeRepository;
import com.oviesAries.recipe.domain.recipe.dao.RecipeStepRepository;
import com.oviesAries.recipe.domain.user.dao.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner demoData(UserRepository userRepository,
                                      IngredientRepository ingredientRepository,
                                      RecipeRepository recipeRepository,
                                      RecipeStepRepository recipeStepRepository,
                                      RecipeIngredientRepository recipeIngredientRepository) {
        return args -> {
            // Users
            User user1 = User.builder().
                    userName("이상윤").
                    password("password1").
                    nickName("한도니").
                    email("email1@email.com").
                    build();
            User user2 = User.builder().
                    userName("최원준").
                    password("password2").
                    nickName("황새치").
                    email("email2@email.com").
                    build();

            userRepository.save(user1);
            userRepository.save(user2);

            // Ingredients
            Ingredient kimchi = new Ingredient(null, "김치", null);
            Ingredient pork = new Ingredient(null, "돼지고기", null);
            Ingredient onion = new Ingredient(null, "양파", null);
            Ingredient garlic = new Ingredient(null, "마늘", null);
            Ingredient tofu = new Ingredient(null, "두부", null);
            Ingredient chiliPowder = new Ingredient(null, "고춧가루", null);

            ingredientRepository.save(kimchi);
            ingredientRepository.save(pork);
            ingredientRepository.save(onion);
            ingredientRepository.save(garlic);
            ingredientRepository.save(tofu);
            ingredientRepository.save(chiliPowder);

            // Recipe
            Recipe kimchiStew = new Recipe(null, "김치찌개", null, 2, null, null);
            recipeRepository.save(kimchiStew);

            // Recipe Steps
            RecipeStep step1 = new RecipeStep(null, kimchiStew, 1, "돼지고기와 김치를 볶는다.", null);
            RecipeStep step2 = new RecipeStep(null, kimchiStew, 2, "양파와 마늘을 추가해 볶는다.", null);
            RecipeStep step3 = new RecipeStep(null, kimchiStew, 3, "물을 넣고 고춧가루를 추가하여 끓인다.", null);
            RecipeStep step4 = new RecipeStep(null, kimchiStew, 4, "두부를 추가하여 끓인다.", null);

            List<RecipeStep> steps = new ArrayList<>(Arrays.asList(step1, step2, step3, step4));
            kimchiStew.setRecipeSteps(steps);

            recipeStepRepository.save(step1);
            recipeStepRepository.save(step2);
            recipeStepRepository.save(step3);
            recipeStepRepository.save(step4);

            // Recipe Ingredients
            RecipeIngredient ri1 = new RecipeIngredient(null, kimchiStew, kimchi, 300);
            RecipeIngredient ri2 = new RecipeIngredient(null, kimchiStew, pork, 150);
            RecipeIngredient ri3 = new RecipeIngredient(null, kimchiStew, onion, 100);
            RecipeIngredient ri4 = new RecipeIngredient(null, kimchiStew, garlic, 5);
            RecipeIngredient ri5 = new RecipeIngredient(null, kimchiStew, tofu, 100);
            RecipeIngredient ri6 = new RecipeIngredient(null, kimchiStew, chiliPowder, 2);

            recipeIngredientRepository.save(ri1);
            recipeIngredientRepository.save(ri2);
            recipeIngredientRepository.save(ri3);
            recipeIngredientRepository.save(ri4);
            recipeIngredientRepository.save(ri5);
            recipeIngredientRepository.save(ri6);

            List<RecipeIngredient> recipeIngredients = new ArrayList<>(Arrays.asList(ri1, ri2, ri3, ri4, ri5, ri6));
            kimchiStew.setRecipeIngredients(recipeIngredients);


        };
    }
}
