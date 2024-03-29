/*
 * Copyright (C) 2017 Alexandre Carbenay
 *
 * This file is part of Cena Project.
 *
 * Cena Project is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * Cena Project is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Cena Project. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package org.adhuc.cena.menu.port.adapter.rest.recipes;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.cucumber;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.mozzarella;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.cucumberInTomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.mozzaInTomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.port.adapter.rest.recipes.RecipeJsonAssertion.assertJsonContainsRecipeIngredient;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import org.adhuc.cena.menu.application.RecipeIngredientAppService;
import org.adhuc.cena.menu.configuration.MenuGenerationProperties;
import org.adhuc.cena.menu.configuration.WebSecurityConfiguration;
import org.adhuc.cena.menu.domain.model.recipe.RecipeId;
import org.adhuc.cena.menu.domain.model.recipe.ingredient.RecipeIngredient;
import org.adhuc.cena.menu.port.adapter.rest.ControllerTestSupport;
import org.adhuc.cena.menu.port.adapter.rest.recipe.RecipeIngredientResourceAssembler;
import org.adhuc.cena.menu.port.adapter.rest.recipe.RecipeIngredientsController;
import org.adhuc.cena.menu.port.adapter.web.WebIndexController;

/**
 * The {@link RecipeIngredientsController} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("integration")
@Tag("restController")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = { RecipeIngredientsController.class, WebIndexController.class }, includeFilters = {
        @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = RecipeIngredientResourceAssembler.class) })
@EnableConfigurationProperties(MenuGenerationProperties.class)
@Import(WebSecurityConfiguration.class)
@DisplayName("Recipe ingredients controller")
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class RecipeIngredientsControllerTest extends ControllerTestSupport {

    private static final String        RECIPE_INGREDIENTS_API_URL = "/api/recipes/{recipeId}/ingredients";

    @Autowired
    private MockMvc                    mvc;

    @MockBean
    private RecipeIngredientAppService recipeIngredientAppServiceMock;

    @Nested
    @DisplayName("with empty ingredients list")
    class WithEmptyIngredientsList {

        @BeforeEach
        public void setUp() {
            when(recipeIngredientAppServiceMock.getRecipeIngredients(TOMATO_CUCUMBER_MOZZA_SALAD_ID))
                    .thenReturn(Collections.emptyList());
        }

        @Test
        @DisplayName("getting list returns OK status")
        public void getRecipesEmptyListStatusOK() throws Exception {
            mvc.perform(get(RECIPE_INGREDIENTS_API_URL, TOMATO_CUCUMBER_MOZZA_SALAD_ID)).andExpect(status().isOk());
        }

        @Test
        @DisplayName("getting list returns empty list")
        public void getRecipesEmptyListNoData() throws Exception {
            mvc.perform(get(RECIPE_INGREDIENTS_API_URL, TOMATO_CUCUMBER_MOZZA_SALAD_ID))
                    .andExpect(jsonPath("$._embedded.data").isArray())
                    .andExpect(jsonPath("$._embedded.data").isEmpty());
        }

    }

    @Nested
    @DisplayName("with 1 main ingredient and 1 basic ingredient")
    class With2Ingredients {

        private RecipeIngredient mozza;
        private RecipeIngredient cucumber;

        @BeforeEach
        public void setUp() {
            RecipeId recipeId = TOMATO_CUCUMBER_MOZZA_SALAD_ID;
            mozza = new RecipeIngredient(recipeId, mozzaInTomatoCucumberMozzaSalad(), mozzarella());
            cucumber = new RecipeIngredient(recipeId, cucumberInTomatoCucumberMozzaSalad(), cucumber());

            when(recipeIngredientAppServiceMock.getRecipeIngredients(recipeId))
                    .thenReturn(Arrays.asList(mozza, cucumber));
        }

        @Test
        @DisplayName("getting list returns OK status")
        public void getRecipesStatusOK() throws Exception {
            mvc.perform(get(RECIPE_INGREDIENTS_API_URL, TOMATO_CUCUMBER_MOZZA_SALAD_ID)).andExpect(status().isOk());
        }

        @Test
        @DisplayName("getting list returns all ingredients")
        public void getRecipesContainsData() throws Exception {
            final ResultActions resultActions =
                    mvc.perform(get(RECIPE_INGREDIENTS_API_URL, TOMATO_CUCUMBER_MOZZA_SALAD_ID))
                            .andExpect(jsonPath("$._embedded.data").isArray())
                            .andExpect(jsonPath("$._embedded.data").isNotEmpty())
                            .andExpect(jsonPath("$._embedded.data", hasSize(2)));
            assertJsonContainsRecipeIngredient(resultActions, "$._embedded.data[0]", mozza);
            assertJsonContainsRecipeIngredient(resultActions, "$._embedded.data[1]", cucumber);
        }

        @Test
        @DisplayName("getting list contains self link to resource")
        public void getRecipesHasSelfLink() throws Exception {
            assertSelfLinkEqualToRequestUrl(
                    mvc.perform(get(RECIPE_INGREDIENTS_API_URL, TOMATO_CUCUMBER_MOZZA_SALAD_ID)));
        }

        @Test
        @DisplayName("contains link to recipe detail")
        public void getRecipeIngredientsHasRecipeLink() throws Exception {
            mvc.perform(get(RECIPE_INGREDIENTS_API_URL, TOMATO_CUCUMBER_MOZZA_SALAD_ID.toString())).andExpect(jsonPath(
                    "$._links.recipe.href", endsWith("/api/recipes/" + TOMATO_CUCUMBER_MOZZA_SALAD_ID.toString())));
        }

    }

}
