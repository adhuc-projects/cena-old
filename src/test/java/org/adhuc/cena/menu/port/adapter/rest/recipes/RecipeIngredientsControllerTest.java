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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.CUCUMBER_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.cucumber;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.tomato;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.addCucumberToTomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.port.adapter.rest.ingredient.IngredientJsonAssertion.assertJsonContainsIngredient;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import org.adhuc.cena.menu.application.RecipeIngredientAppService;
import org.adhuc.cena.menu.configuration.MenuGenerationProperties;
import org.adhuc.cena.menu.configuration.WebSecurityConfiguration;
import org.adhuc.cena.menu.domain.model.recipe.AddIngredientToRecipe;
import org.adhuc.cena.menu.port.adapter.rest.ControllerTestSupport;
import org.adhuc.cena.menu.port.adapter.rest.ingredient.IngredientResourceAssembler;
import org.adhuc.cena.menu.port.adapter.rest.recipe.RecipeIngredientsController;

/**
 *
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("integration")
@Tag("restController")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RecipeIngredientsController.class,
        includeFilters = { @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = IngredientResourceAssembler.class) })
@EnableConfigurationProperties(MenuGenerationProperties.class)
@Import(WebSecurityConfiguration.class)
@DisplayName("Recipe ingredients controller")
public class RecipeIngredientsControllerTest extends ControllerTestSupport {

    private static final String        RECIPE_INGREDIENTS_API_URL          = "/api/recipes/{recipeId}/ingredients";
    private static final String        RECIPE_INGREDIENTS_ADDITION_API_URL =
            RECIPE_INGREDIENTS_API_URL + "/{ingredientId}";

    @Autowired
    private MockMvc                    mvc;

    @MockBean
    private RecipeIngredientAppService recipeIngredientAppServiceMock;

    @BeforeEach
    public void setUp() {
        reset(recipeIngredientAppServiceMock);
    }

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
    @DisplayName("with 2 ingredients")
    class With2Ingredients {

        @BeforeEach
        public void setUp() {
            when(recipeIngredientAppServiceMock.getRecipeIngredients(TOMATO_CUCUMBER_MOZZA_SALAD_ID))
                    .thenReturn(Arrays.asList(tomato(), cucumber()));
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
            assertJsonContainsIngredient(resultActions, "$._embedded.data[0]", tomato());
            assertJsonContainsIngredient(resultActions, "$._embedded.data[1]", cucumber());
        }

        @Test
        @DisplayName("getting list contains self link to resource")
        public void getRecipesHasSelfLink() throws Exception {
            assertSelfLinkEqualToRequestUrl(
                    mvc.perform(get(RECIPE_INGREDIENTS_API_URL, TOMATO_CUCUMBER_MOZZA_SALAD_ID)));
        }

    }

    @Test
    @DisplayName("adding ingredient to recipe returns no content status")
    @WithMockUser(authorities = "USER")
    public void addIngredientToRecipeReturnsNoContentStatus() throws Exception {
        mvc.perform(put(RECIPE_INGREDIENTS_ADDITION_API_URL, TOMATO_CUCUMBER_MOZZA_SALAD_ID, CUCUMBER_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("adding ingredient to recipe calls the application service with command")
    @WithMockUser(authorities = "USER")
    public void addIngredientToRecipeCallsAppServiceWithCommand() throws Exception {
        final ArgumentCaptor<AddIngredientToRecipe> commandCaptor =
                ArgumentCaptor.forClass(AddIngredientToRecipe.class);

        mvc.perform(put(RECIPE_INGREDIENTS_ADDITION_API_URL, TOMATO_CUCUMBER_MOZZA_SALAD_ID, CUCUMBER_ID)).andReturn();

        verify(recipeIngredientAppServiceMock).addIngredientToRecipe(commandCaptor.capture());
        assertThat(commandCaptor.getValue()).isEqualTo(addCucumberToTomatoCucumberMozzaSalad());
    }

}
