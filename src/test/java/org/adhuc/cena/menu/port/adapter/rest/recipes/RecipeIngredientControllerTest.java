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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.CUCUMBER_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.MUSTARD_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.cucumber;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CANTAL_PIE_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.addCucumberToTomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.addMustardToTomatoCantalPie;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.cucumberInTomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.port.adapter.rest.recipes.RecipeJsonAssertion.assertJsonContainsRecipeIngredient;

import org.junit.jupiter.api.DisplayName;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import org.adhuc.cena.menu.application.RecipeIngredientAppService;
import org.adhuc.cena.menu.configuration.MenuGenerationProperties;
import org.adhuc.cena.menu.configuration.WebSecurityConfiguration;
import org.adhuc.cena.menu.domain.model.recipe.RecipeId;
import org.adhuc.cena.menu.domain.model.recipe.ingredient.AddIngredientToRecipe;
import org.adhuc.cena.menu.domain.model.recipe.ingredient.RecipeIngredient;
import org.adhuc.cena.menu.port.adapter.rest.ControllerTestSupport;
import org.adhuc.cena.menu.port.adapter.rest.recipe.RecipeIngredientController;
import org.adhuc.cena.menu.port.adapter.rest.recipe.RecipeIngredientResourceAssembler;
import org.adhuc.cena.menu.port.adapter.rest.recipe.RecipeIngredientsController;
import org.adhuc.cena.menu.port.adapter.web.WebIndexController;
import org.adhuc.cena.menu.support.security.WithAuthenticatedUser;

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
@WebMvcTest(controllers = { RecipeIngredientController.class, WebIndexController.class }, includeFilters = {
        @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = RecipeIngredientResourceAssembler.class) })
@EnableConfigurationProperties(MenuGenerationProperties.class)
@Import(WebSecurityConfiguration.class)
@DisplayName("Recipe ingredient controller")
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class RecipeIngredientControllerTest extends ControllerTestSupport {

    private static final String        RECIPE_INGREDIENT_API_URL = "/api/recipes/{recipeId}/ingredients/{ingredientId}";

    @Autowired
    private MockMvc                    mvc;

    @MockBean
    private RecipeIngredientAppService recipeIngredientAppServiceMock;

    @Test
    @DisplayName("getting ingredient linked to recipe returns ingredient")
    public void getIngredientLinkedToRecipe() throws Exception {
        RecipeId recipeId = TOMATO_CUCUMBER_MOZZA_SALAD_ID;
        RecipeIngredient cucumber = new RecipeIngredient(recipeId, cucumberInTomatoCucumberMozzaSalad(), cucumber());
        when(recipeIngredientAppServiceMock.getRecipeIngredient(recipeId, CUCUMBER_ID)).thenReturn(cucumber);

        ResultActions resultActions = mvc.perform(get(RECIPE_INGREDIENT_API_URL, recipeId, CUCUMBER_ID));
        assertJsonContainsRecipeIngredient(resultActions, "$", cucumber);
    }

    @Test
    @DisplayName("adding ingredient to recipe returns no content status")
    @WithAuthenticatedUser
    public void addIngredientToRecipeReturnsNoContentStatus() throws Exception {
        mvc.perform(put(RECIPE_INGREDIENT_API_URL, TOMATO_CUCUMBER_MOZZA_SALAD_ID, CUCUMBER_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("adding ingredient to recipe as basic ingredient calls the application service with command")
    @WithAuthenticatedUser
    public void addBasicIngredientToRecipeCallsAppServiceWithCommand() throws Exception {
        final ArgumentCaptor<AddIngredientToRecipe> commandCaptor =
                ArgumentCaptor.forClass(AddIngredientToRecipe.class);

        mvc.perform(put(RECIPE_INGREDIENT_API_URL, TOMATO_CANTAL_PIE_ID, MUSTARD_ID)).andReturn();

        verify(recipeIngredientAppServiceMock).addIngredientToRecipe(commandCaptor.capture());
        assertThat(commandCaptor.getValue()).isEqualTo(addMustardToTomatoCantalPie());
    }

    @Test
    @DisplayName("adding ingredient to recipe as main ingredient calls the application service with command")
    @WithAuthenticatedUser
    public void addMainIngredientToRecipeCallsAppServiceWithCommand() throws Exception {
        final ArgumentCaptor<AddIngredientToRecipe> commandCaptor =
                ArgumentCaptor.forClass(AddIngredientToRecipe.class);

        mvc.perform(put(RECIPE_INGREDIENT_API_URL, TOMATO_CUCUMBER_MOZZA_SALAD_ID, CUCUMBER_ID).param("main",
                Boolean.TRUE.toString())).andReturn();

        verify(recipeIngredientAppServiceMock).addIngredientToRecipe(commandCaptor.capture());
        assertThat(commandCaptor.getValue()).isEqualTo(addCucumberToTomatoCucumberMozzaSalad());
    }

}
