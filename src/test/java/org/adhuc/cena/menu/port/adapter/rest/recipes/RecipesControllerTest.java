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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
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

/**
 * The {@link RecipesController} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RecipesController.class,
        includeFilters = { @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = RecipeResourceAssembler.class) })
@EnableConfigurationProperties(MenuGenerationProperties.class)
@Import(WebSecurityConfiguration.class)
public class RecipesControllerTest extends ControllerTestSupport {

    private static final String RECIPES_API_URL = "/api/recipes";

    @Autowired
    private MockMvc             mvc;

    @MockBean
    private RecipeAppService    recipeAppServiceMock;

    @Before
    public void setUp() {
        reset(recipeAppServiceMock);
    }

    @Test
    public void getRecipesEmptyListStatusOK() throws Exception {
        when(recipeAppServiceMock.getRecipes()).thenReturn(Collections.emptyList());

        mvc.perform(get(RECIPES_API_URL)).andExpect(status().isOk());
    }

    @Test
    public void getRecipesEmptyListNoData() throws Exception {
        when(recipeAppServiceMock.getRecipes()).thenReturn(Collections.emptyList());

        mvc.perform(get(RECIPES_API_URL)).andExpect(jsonPath("$._embedded.data").isArray())
                .andExpect(jsonPath("$._embedded.data").isEmpty());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void getRecipesStatusOK() throws Exception {
        when(recipeAppServiceMock.getRecipes())
                .thenReturn(Arrays.asList(tomatoCucumberMozzaSalad(), tomatoCucumberOliveFetaSalad()));

        mvc.perform(get(RECIPES_API_URL)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void getRecipesContainsData() throws Exception {
        when(recipeAppServiceMock.getRecipes())
                .thenReturn(Arrays.asList(tomatoCucumberMozzaSalad(), tomatoCucumberOliveFetaSalad()));

        final ResultActions resultActions = mvc.perform(get(RECIPES_API_URL))
                .andExpect(jsonPath("$._embedded.data").isArray()).andExpect(jsonPath("$._embedded.data").isNotEmpty())
                .andExpect(jsonPath("$._embedded.data", hasSize(2)));
        assertJsonContainsRecipe(resultActions, "$._embedded.data[0]", tomatoCucumberMozzaSalad());
        assertJsonContainsRecipe(resultActions, "$._embedded.data[1]", tomatoCucumberOliveFetaSalad());
    }

    @Test
    public void getRecipesHasSelfLink() throws Exception {
        when(recipeAppServiceMock.getRecipes()).thenReturn(Arrays.asList(tomatoCucumberMozzaSalad()));

        assertSelfLinkEqualToRequestUrl(mvc.perform(get(RECIPES_API_URL)));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void createRecipeInvalidRequestNoName() throws Exception {
        mvc.perform(post(RECIPES_API_URL).contentType(APPLICATION_JSON)
                .content(asJson(CreateRecipeRequest.builder().content(TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT).build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void createRecipeInvalidRequestBlankName() throws Exception {
        mvc.perform(post(RECIPES_API_URL).contentType(APPLICATION_JSON).content(
                asJson(CreateRecipeRequest.builder().name("").content(TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT).build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void createRecipeInvalidRequestNoContent() throws Exception {
        mvc.perform(post(RECIPES_API_URL).contentType(APPLICATION_JSON)
                .content(asJson(CreateRecipeRequest.builder().name(TOMATO_CUCUMBER_MOZZA_SALAD_NAME).build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void createRecipeInvalidRequestBlankContent() throws Exception {
        mvc.perform(post(RECIPES_API_URL).contentType(APPLICATION_JSON).content(
                asJson(CreateRecipeRequest.builder().name(TOMATO_CUCUMBER_MOZZA_SALAD_NAME).content("").build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void createRecipeCallsAppServiceWithCommand() throws Exception {
        final ArgumentCaptor<CreateRecipe> commandCaptor = ArgumentCaptor.forClass(CreateRecipe.class);

        mvc.perform(
                post(RECIPES_API_URL).contentType(APPLICATION_JSON).content(createTomatoCucumberMozzaSaladRequest()))
                .andReturn();

        verify(recipeAppServiceMock).createRecipe(commandCaptor.capture());
        assertThat(commandCaptor.getValue()).isEqualToIgnoringGivenFields(createTomatoCucumberMozzaSalad(), "recipeId");
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void createRecipeReturnsCreatedStatus() throws Exception {
        mvc.perform(
                post(RECIPES_API_URL).contentType(APPLICATION_JSON).content(createTomatoCucumberMozzaSaladRequest()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void createIngredientReturnsLocationHeader() throws Exception {
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
