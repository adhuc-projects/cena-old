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
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_NAME;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.createTomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberOliveFetaSalad;

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
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import org.adhuc.cena.menu.application.RecipeAppService;
import org.adhuc.cena.menu.configuration.MenuGenerationProperties;
import org.adhuc.cena.menu.configuration.WebSecurityConfiguration;
import org.adhuc.cena.menu.domain.model.recipe.CreateRecipe;
import org.adhuc.cena.menu.domain.model.recipe.Recipe;
import org.adhuc.cena.menu.domain.model.recipe.RecipeId;
import org.adhuc.cena.menu.port.adapter.rest.ControllerTestSupport;
import org.adhuc.cena.menu.port.adapter.rest.recipe.CreateRecipeRequest;
import org.adhuc.cena.menu.port.adapter.rest.recipe.RecipeResourceAssembler;
import org.adhuc.cena.menu.port.adapter.rest.recipe.RecipesController;
import org.adhuc.cena.menu.port.adapter.web.WebIndexController;
import org.adhuc.cena.menu.support.security.WithAuthenticatedUser;
import org.adhuc.cena.menu.support.security.WithCommunityUser;

/**
 * The {@link RecipesController} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("integration")
@Tag("restController")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = { RecipesController.class, WebIndexController.class },
        includeFilters = { @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = RecipeResourceAssembler.class) })
@EnableConfigurationProperties(MenuGenerationProperties.class)
@Import(WebSecurityConfiguration.class)
@DisplayName("Recipes controller")
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class RecipesControllerTest extends ControllerTestSupport {

    private static final String RECIPES_API_URL = "/api/recipes";

    @Autowired
    private MockMvc             mvc;

    @MockBean
    private RecipeAppService    recipeAppServiceMock;

    @BeforeEach
    public void setUp() {
        reset(recipeAppServiceMock);
    }

    @Nested
    @DisplayName("with empty list")
    class WithEmptyList {

        @BeforeEach
        public void setUp() {
            when(recipeAppServiceMock.getRecipes()).thenReturn(Collections.emptyList());
        }

        @Test
        @DisplayName("getting list returns OK status")
        public void getRecipesEmptyListStatusOK() throws Exception {
            mvc.perform(get(RECIPES_API_URL)).andExpect(status().isOk());
        }

        @Test
        @DisplayName("getting list returns empty list")
        public void getRecipesEmptyListNoData() throws Exception {
            mvc.perform(get(RECIPES_API_URL)).andExpect(jsonPath("$._embedded.data").isArray())
                    .andExpect(jsonPath("$._embedded.data").isEmpty());
        }

    }

    @Nested
    @DisplayName("with 2 recipes")
    class With2Recipes {

        @BeforeEach
        public void setUp() {
            when(recipeAppServiceMock.getRecipes())
                    .thenReturn(Arrays.asList(tomatoCucumberMozzaSalad(), tomatoCucumberOliveFetaSalad()));
        }

        @Test
        @DisplayName("getting list returns OK status")
        public void getRecipesStatusOK() throws Exception {
            mvc.perform(get(RECIPES_API_URL)).andExpect(status().isOk());
        }

        @Test
        @DisplayName("getting list returns all recipes")
        public void getRecipesContainsData() throws Exception {
            final ResultActions resultActions =
                    mvc.perform(get(RECIPES_API_URL)).andExpect(jsonPath("$._embedded.data").isArray())
                            .andExpect(jsonPath("$._embedded.data").isNotEmpty())
                            .andExpect(jsonPath("$._embedded.data", hasSize(2)));
            assertJsonContainsRecipe(resultActions, "$._embedded.data[0]", tomatoCucumberMozzaSalad());
            assertJsonContainsRecipe(resultActions, "$._embedded.data[1]", tomatoCucumberOliveFetaSalad());
        }

        @Test
        @DisplayName("getting list contains self link to resource")
        public void getRecipesHasSelfLink() throws Exception {
            assertSelfLinkEqualToRequestUrl(mvc.perform(get(RECIPES_API_URL)));
        }

    }

    @Test
    @DisplayName("creating recipe as community user returns unauthorized status")
    @WithCommunityUser
    public void createRecipeAsCommunityUser() throws Exception {
        mvc.perform(
                post(RECIPES_API_URL).contentType(APPLICATION_JSON).content(createTomatoCucumberMozzaSaladRequest()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("creating recipe with no name returns bad request status")
    @WithAuthenticatedUser
    public void createRecipeInvalidRequestNoName() throws Exception {
        mvc.perform(post(RECIPES_API_URL).contentType(APPLICATION_JSON)
                .content(asJson(CreateRecipeRequest.builder().content(TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT).build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("creating recipe with empty name returns bad request status")
    @WithAuthenticatedUser
    public void createRecipeInvalidRequestBlankName() throws Exception {
        mvc.perform(post(RECIPES_API_URL).contentType(APPLICATION_JSON).content(
                asJson(CreateRecipeRequest.builder().name("").content(TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT).build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("creating recipe with no content returns bad request status")
    @WithAuthenticatedUser
    public void createRecipeInvalidRequestNoContent() throws Exception {
        mvc.perform(post(RECIPES_API_URL).contentType(APPLICATION_JSON)
                .content(asJson(CreateRecipeRequest.builder().name(TOMATO_CUCUMBER_MOZZA_SALAD_NAME).build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("creating recipe with empty content returns bad request status")
    @WithAuthenticatedUser
    public void createRecipeInvalidRequestBlankContent() throws Exception {
        mvc.perform(post(RECIPES_API_URL).contentType(APPLICATION_JSON).content(
                asJson(CreateRecipeRequest.builder().name(TOMATO_CUCUMBER_MOZZA_SALAD_NAME).content("").build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("creating valid recipe returns created status")
    @WithAuthenticatedUser
    public void createRecipeReturnsCreatedStatus() throws Exception {
        mvc.perform(
                post(RECIPES_API_URL).contentType(APPLICATION_JSON).content(createTomatoCucumberMozzaSaladRequest()))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("creating valid recipe calls the application service with command")
    @WithAuthenticatedUser
    public void createRecipeCallsAppServiceWithCommand() throws Exception {
        final ArgumentCaptor<CreateRecipe> commandCaptor = ArgumentCaptor.forClass(CreateRecipe.class);

        mvc.perform(
                post(RECIPES_API_URL).contentType(APPLICATION_JSON).content(createTomatoCucumberMozzaSaladRequest()))
                .andReturn();

        verify(recipeAppServiceMock).createRecipe(commandCaptor.capture());
        assertThat(commandCaptor.getValue()).isEqualToIgnoringGivenFields(createTomatoCucumberMozzaSalad(), "recipeId");
    }

    @Test
    @DisplayName("creating valid recipe returns location header with link to recipe details")
    @WithAuthenticatedUser
    public void createRecipeReturnsLocationHeader() throws Exception {
        final ArgumentCaptor<CreateRecipe> commandCaptor = ArgumentCaptor.forClass(CreateRecipe.class);
        doNothing().when(recipeAppServiceMock).createRecipe(commandCaptor.capture());

        mvc.perform(
                post(RECIPES_API_URL).contentType(APPLICATION_JSON).content(createTomatoCucumberMozzaSaladRequest()))
                .andExpect(header().string(HttpHeaders.LOCATION,
                        endsWith(buildRecipeSelfLink(commandCaptor.getValue().recipeId()))));
    }

    private String createTomatoCucumberMozzaSaladRequest() {
        return asJson(CreateRecipeRequest.builder().name(TOMATO_CUCUMBER_MOZZA_SALAD_NAME)
                .content(TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT).build());
    }

    protected void assertJsonContainsRecipe(final ResultActions resultActions, final String jsonPath,
            final Recipe recipe) throws Exception {
        resultActions.andExpect(jsonPath(jsonPath + ".id").exists())
                .andExpect(jsonPath(jsonPath + ".id", equalTo(recipe.id().toString())))
                .andExpect(jsonPath(jsonPath + ".name").exists())
                .andExpect(jsonPath(jsonPath + ".name", equalTo(recipe.name())))
                .andExpect(jsonPath(jsonPath + ".content").exists())
                .andExpect(jsonPath(jsonPath + ".content", equalTo(recipe.content())));
    }

    private String buildRecipeSelfLink(RecipeId recipeId) {
        return RECIPES_API_URL + "/" + recipeId.id().toString();
    }

}
