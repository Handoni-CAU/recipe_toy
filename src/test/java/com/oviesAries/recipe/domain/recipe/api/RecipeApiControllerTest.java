package com.oviesAries.recipe.domain.recipe.api;

import com.oviesAries.recipe.domain.entity.Recipe;
import com.oviesAries.recipe.domain.recipe.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@RunWith(SpringRunner.class)
@WebMvcTest(RecipeApiController.class)
@WithMockUser
class RecipeApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    @Test
    public void getAllRecipes() throws Exception {
        // given
        Recipe recipe1 = Recipe.builder().dishName("Pizza").id(1L).build();
        Recipe recipe2 = Recipe.builder().dishName("Pasta").id(2L).build();

        // when
        when(recipeService.getAllRecipes()).thenReturn(Arrays.asList(recipe1, recipe2));

        // then
        mockMvc.perform(get("/api/recipes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].dish_name", is("Pizza")))
                .andExpect(jsonPath("$[1].dish_name", is("Pasta")));
    }

    @Test
    public void retrieveRecipeById() throws Exception {
        // given
        Recipe recipe = Recipe.builder().dishName("Tacos").id(1L).build();

        // when
        when(recipeService.getRecipeById(1L)).thenReturn(recipe);

        // then
        mockMvc.perform(get("/api/recipes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dish_name", is("Tacos")))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void createRecipe() throws Exception {
        // given
        Recipe recipe = Recipe
                        .builder()
                        .dishName("Burger")
                        .build();

        // then
        mockMvc.perform(post("/api/recipes/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dish_name\":\"Burger\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dish_name", is("Burger")));
    }

}