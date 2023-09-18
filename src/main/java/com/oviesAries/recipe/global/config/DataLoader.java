package com.oviesAries.recipe.global.config;

import com.oviesAries.recipe.domain.entity.*;
import com.oviesAries.recipe.domain.dao.IngredientRepository;
import com.oviesAries.recipe.domain.recipe.dao.RecipeIngredientRepository;
import com.oviesAries.recipe.domain.recipe.dao.RecipeRepository;
import com.oviesAries.recipe.domain.recipe.dao.RecipeStepRepository;
import com.oviesAries.recipe.domain.user.dao.UserRepository;
import com.oviesAries.recipe.domain.user.domain.Email;
import com.oviesAries.recipe.domain.user.domain.EncodedPassword;
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
                    encodedPassword(EncodedPassword.from("1234")).
                    nickName("한도니").
                    email(Email.from("email1@email.com")).
                    build();
            User user2 = User.builder().
                    userName("최원준").
                    encodedPassword(EncodedPassword.from("1234")).
                    nickName("황새치").
                    email(Email.from("email2@email.com")).
                    build();

            userRepository.save(user1);
            userRepository.save(user2);

            // Ingredients
            Ingredient kimchi = new Ingredient(1L, "김치", null);
            Ingredient pork = new Ingredient(2L, "돼지고기", null);
            Ingredient onion = new Ingredient(3L, "양파", null);
            Ingredient garlic = new Ingredient(4L, "마늘", null);
            Ingredient tofu = new Ingredient(5L, "두부", null);
            Ingredient chiliPowder = new Ingredient(6L, "고춧가루", null);

            ingredientRepository.save(kimchi);
            ingredientRepository.save(pork);
            ingredientRepository.save(onion);
            ingredientRepository.save(garlic);
            ingredientRepository.save(tofu);
            ingredientRepository.save(chiliPowder);

            // Recipe
            Recipe kimchiStew = new Recipe(1L, "김치찌개", "맛있는 김치찌개s", 2, null, null);
            recipeRepository.save(kimchiStew);

            // Recipe Steps
            RecipeStep step1 = new RecipeStep(1L, kimchiStew, 1, "돼지고기와 김치를 볶는다.", null, 0L);
            RecipeStep step2 = new RecipeStep(2L, kimchiStew, 2, "양파와 마늘을 추가해 볶는다.", null, 0L);
            RecipeStep step3 = new RecipeStep(3L, kimchiStew, 3, "물을 넣고 고춧가루를 추가하여 끓인다.", null, 0L);
            RecipeStep step4 = new RecipeStep(4L, kimchiStew, 4, "두부를 추가하여 끓인다.", null, 0L);

            List<RecipeStep> steps = new ArrayList<>(Arrays.asList(step1, step2, step3, step4));
            kimchiStew.setRecipeSteps(steps);

            recipeStepRepository.save(step1);
            recipeStepRepository.save(step2);
            recipeStepRepository.save(step3);
            recipeStepRepository.save(step4);

            // Recipe Ingredients
            RecipeIngredient ri1 = new RecipeIngredient(1L, kimchiStew, kimchi, 300, 1);
            RecipeIngredient ri2 = new RecipeIngredient(2L, kimchiStew, pork, 150, 2);
            RecipeIngredient ri3 = new RecipeIngredient(3L, kimchiStew, onion, 100, 3);
            RecipeIngredient ri4 = new RecipeIngredient(4L, kimchiStew, garlic, 5, 4 );
            RecipeIngredient ri5 = new RecipeIngredient(5L, kimchiStew, tofu, 100, 5);
            RecipeIngredient ri6 = new RecipeIngredient(6L, kimchiStew, chiliPowder, 2, 5);

            recipeIngredientRepository.save(ri1);
            recipeIngredientRepository.save(ri2);
            recipeIngredientRepository.save(ri3);
            recipeIngredientRepository.save(ri4);
            recipeIngredientRepository.save(ri5);
            recipeIngredientRepository.save(ri6);

            List<RecipeIngredient> recipeIngredients = new ArrayList<>(Arrays.asList(ri1, ri2, ri3, ri4, ri5, ri6));
            kimchiStew.setRecipeIngredients(recipeIngredients);


// 추가적인 Ingredients
            Ingredient beef = new Ingredient(null, "소고기", null);
            Ingredient rice = new Ingredient(null, "밥", null);
            Ingredient egg = new Ingredient(null, "계란", null);
            Ingredient soySauce = new Ingredient(null, "간장", null);
            Ingredient sesameOil = new Ingredient(null, "참기름", null);

            ingredientRepository.saveAll(Arrays.asList(beef, rice, egg, soySauce, sesameOil));

// 추가적인 Recipe: 불고기
            Recipe bulgogi = new Recipe(null, "불고기", "맛있는 소고기 불고기", 2, null, null);
            recipeRepository.save(bulgogi);

// 불고기 Recipe Steps
            RecipeStep bulgogiStep1 = new RecipeStep(null, bulgogi, 1, "소고기에 간장, 마늘, 참기름을 넣고 밑간한다.", null, 0L);
            RecipeStep bulgogiStep2 = new RecipeStep(null, bulgogi, 2, "팬에 소고기를 넣고 볶는다.", null, 0L);
            recipeStepRepository.saveAll(Arrays.asList(bulgogiStep1, bulgogiStep2));

// 불고기 Recipe Ingredients
            RecipeIngredient bulgogiRi1 = new RecipeIngredient(null, bulgogi, beef, 300, 1);
            RecipeIngredient bulgogiRi2 = new RecipeIngredient(null, bulgogi, soySauce, 50, 2);
            RecipeIngredient bulgogiRi3 = new RecipeIngredient(null, bulgogi, garlic, 10, 3);
            RecipeIngredient bulgogiRi4 = new RecipeIngredient(null, bulgogi, sesameOil, 10, 4 );
            recipeIngredientRepository.saveAll(Arrays.asList(bulgogiRi1, bulgogiRi2, bulgogiRi3, bulgogiRi4));

// 추가적인 Recipe: 계란밥
            Recipe eggRice = new Recipe(null, "계란밥", "간단한 계란밥", 1, null, null);
            recipeRepository.save(eggRice);

// 계란밥 Recipe Steps
            RecipeStep eggRiceStep1 = new RecipeStep(null, eggRice, 1, "밥을 볶는다.", null, 0L);
            RecipeStep eggRiceStep2 = new RecipeStep(null, eggRice, 2, "계란을 넣고 섞는다.", null, 0L);
            recipeStepRepository.saveAll(Arrays.asList(eggRiceStep1, eggRiceStep2));

// 계란밥 Recipe Ingredients
            RecipeIngredient eggRiceRi1 = new RecipeIngredient(null, eggRice, rice, 200, 1);
            RecipeIngredient eggRiceRi2 = new RecipeIngredient(null, eggRice, egg, 2, 2);
            recipeIngredientRepository.saveAll(Arrays.asList(eggRiceRi1, eggRiceRi2));




        };
    }
}
