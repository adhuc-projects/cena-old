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
package org.adhuc.cena.menu.port.adapter.rest.ingredient;

import static org.assertj.core.api.Assertions.assertThat;
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

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_NAME;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.createTomato;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.cucumber;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.tomato;

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

import org.adhuc.cena.menu.application.IngredientAppService;
import org.adhuc.cena.menu.configuration.MenuGenerationProperties;
import org.adhuc.cena.menu.configuration.WebSecurityConfiguration;
import org.adhuc.cena.menu.domain.model.ingredient.CreateIngredient;
import org.adhuc.cena.menu.domain.model.ingredient.Ingredient;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientId;

/**
 * The {@link IngredientsController} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = IngredientsController.class,
        includeFilters = { @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = IngredientResourceAssembler.class) })
@EnableConfigurationProperties(MenuGenerationProperties.class)
@Import(WebSecurityConfiguration.class)
public class IngredientsControllerTest extends IngredientControllerTestSupport {

    private static final String  INGREDIENTS_API_URL = "/api/ingredients";

    @Autowired
    private MockMvc              mvc;

    @MockBean
    private IngredientAppService ingredientAppServiceMock;

    @Before
    public void setUp() {
        reset(ingredientAppServiceMock);
    }

    @Test
    public void getIngredientsEmptyListStatusOK() throws Exception {
        when(ingredientAppServiceMock.getIngredients()).thenReturn(Collections.emptyList());

        mvc.perform(get(INGREDIENTS_API_URL)).andExpect(status().isOk());
    }

    @Test
    public void getIngredientsEmptyListNoData() throws Exception {
        when(ingredientAppServiceMock.getIngredients()).thenReturn(Collections.emptyList());

        mvc.perform(get(INGREDIENTS_API_URL)).andExpect(jsonPath("$._embedded.data").isArray())
                .andExpect(jsonPath("$._embedded.data").isEmpty());
    }

    @Test
    public void getIngredientsStatusOK() throws Exception {
        when(ingredientAppServiceMock.getIngredients()).thenReturn(Arrays.asList(tomato(), cucumber()));

        mvc.perform(get(INGREDIENTS_API_URL)).andExpect(status().isOk());
    }

    @Test
    public void getIngredientsContainsData() throws Exception {
        when(ingredientAppServiceMock.getIngredients()).thenReturn(Arrays.asList(tomato(), cucumber()));

        final ResultActions resultActions = mvc.perform(get(INGREDIENTS_API_URL))
                .andExpect(jsonPath("$._embedded.data").isArray()).andExpect(jsonPath("$._embedded.data").isNotEmpty())
                .andExpect(jsonPath("$._embedded.data", hasSize(2)));
        assertJsonContainsIngredient(resultActions, "$._embedded.data[0]", tomato());
        assertJsonContainsIngredient(resultActions, "$._embedded.data[1]", cucumber());
    }

    @Test
    public void getIngredientsHasSelfLink() throws Exception {
        when(ingredientAppServiceMock.getIngredients()).thenReturn(Arrays.asList(tomato(), cucumber()));

        assertSelfLinkEqualToRequestUrl(mvc.perform(get(INGREDIENTS_API_URL)));
    }

    @Test
    @WithMockUser(authorities = "INGREDIENT_MANAGER")
    public void createIngredientInvalidRequest() throws Exception {
        mvc.perform(post(INGREDIENTS_API_URL).contentType(APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "INGREDIENT_MANAGER")
    public void createIngredientCallsAppServiceWithCommand() throws Exception {
        final ArgumentCaptor<CreateIngredient> commandCaptor = ArgumentCaptor.forClass(CreateIngredient.class);

        mvc.perform(post(INGREDIENTS_API_URL).contentType(APPLICATION_JSON).content(createTomatoRequest())).andReturn();

        verify(ingredientAppServiceMock).createIngredient(commandCaptor.capture());
        assertThat(commandCaptor.getValue()).isEqualToIgnoringGivenFields(createTomato(), "ingredientId");
    }

    @Test
    @WithMockUser(authorities = "INGREDIENT_MANAGER")
    public void createIngredientReturnsCreatedStatus() throws Exception {
        mvc.perform(post(INGREDIENTS_API_URL).contentType(APPLICATION_JSON).content(createTomatoRequest()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "INGREDIENT_MANAGER")
    public void createIngredientReturnsLocationHeader() throws Exception {
        final ArgumentCaptor<CreateIngredient> commandCaptor = ArgumentCaptor.forClass(CreateIngredient.class);
        doNothing().when(ingredientAppServiceMock).createIngredient(commandCaptor.capture());

        mvc.perform(post(INGREDIENTS_API_URL).contentType(APPLICATION_JSON).content(createTomatoRequest()))
                .andExpect(header().string(HttpHeaders.LOCATION,
                        endsWith(buildIngredientSelfLink(commandCaptor.getValue().ingredientId()))));
    }

    private String createTomatoRequest() {
        return asJson(CreateIngredientRequest.builder().name(TOMATO_NAME).build());
    }

    @Override
    protected void assertJsonContainsIngredient(final ResultActions resultActions, final String jsonPath,
            final Ingredient ingredient) throws Exception {
        super.assertJsonContainsIngredient(resultActions, jsonPath, ingredient);
        resultActions.andExpect(jsonPath(jsonPath + "._links.self.href").exists()).andExpect(
                jsonPath(jsonPath + "._links.self.href", endsWith(buildIngredientSelfLink(ingredient.id()))));
    }

    private String buildIngredientSelfLink(IngredientId ingredientId) {
        return INGREDIENTS_API_URL + "/" + ingredientId.id().toString();
    }

}
