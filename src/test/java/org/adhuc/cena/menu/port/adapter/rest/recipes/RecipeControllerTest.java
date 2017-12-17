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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.port.adapter.rest.recipes.RecipeJsonAssertion.assertJsonContainsRecipe;

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

import org.adhuc.cena.menu.application.RecipeAppService;
import org.adhuc.cena.menu.configuration.MenuGenerationProperties;
import org.adhuc.cena.menu.configuration.WebSecurityConfiguration;
import org.adhuc.cena.menu.port.adapter.rest.ControllerTestSupport;
import org.adhuc.cena.menu.port.adapter.rest.recipe.RecipeController;
import org.adhuc.cena.menu.port.adapter.rest.recipe.RecipeResourceAssembler;
import org.adhuc.cena.menu.port.adapter.web.WebIndexController;

/**
 * The {@link RecipeController} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("integration")
@Tag("restController")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = { RecipeController.class, WebIndexController.class },
        includeFilters = { @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = RecipeResourceAssembler.class) })
@EnableConfigurationProperties(MenuGenerationProperties.class)
@Import(WebSecurityConfiguration.class)
@DisplayName("Recipe controller")
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class RecipeControllerTest extends ControllerTestSupport {

    private static final String RECIPE_API_URL = "/api/recipes/{id}";

    @Autowired
    private MockMvc             mvc;

    @MockBean
    private RecipeAppService    recipeAppServiceMock;

    @BeforeEach
    public void setUp() {
        reset(recipeAppServiceMock);
    }

    @Test
    @DisplayName("getting recipe detail from invalid id returns not found status")
    public void getRecipeWithInvalidIdStatusNotFound() throws Exception {
        mvc.perform(get(RECIPE_API_URL, "invalid")).andExpect(status().isNotFound());
    }

    @Nested
    @DisplayName("getting tomato, cucumber and mozzarella salad detail")
    class TomatoCucumberMozzaSaladDetail {

        @BeforeEach
        public void setUp() {
            when(recipeAppServiceMock.getRecipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID)).thenReturn(tomatoCucumberMozzaSalad());
        }

        @Test
        @DisplayName("returns OK status")
        public void getRecipeFoundStatusOK() throws Exception {
            mvc.perform(get(RECIPE_API_URL, TOMATO_CUCUMBER_MOZZA_SALAD_ID.toString())).andExpect(status().isOk());
        }

        @Test
        @DisplayName("contains tomato, cucumber and mozzarella salad data")
        public void getRecipeFoundContainsData() throws Exception {
            final ResultActions resultActions =
                    mvc.perform(get(RECIPE_API_URL, TOMATO_CUCUMBER_MOZZA_SALAD_ID.toString()));
            assertJsonContainsRecipe(resultActions, "$", tomatoCucumberMozzaSalad());
        }

        @Test
        @DisplayName("contains self link to detail")
        public void getRecipeHasSelfLink() throws Exception {
            assertSelfLinkEqualToRequestUrl(
                    mvc.perform(get(RECIPE_API_URL, TOMATO_CUCUMBER_MOZZA_SALAD_ID.toString())));
        }

        @Test
        @DisplayName("contains recipe ingredients list link")
        public void getRecipeHasIngredientsLink() throws Exception {
            ResultActions resultActions = mvc.perform(get(RECIPE_API_URL, TOMATO_CUCUMBER_MOZZA_SALAD_ID.toString()));
            resultActions.andExpect(jsonPath("$._links.ingredients.href").exists()).andExpect(
                    jsonPath("$._links.ingredients.href", equalTo(getRequestUrl(resultActions) + "/ingredients")));
        }

    }

}
