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
import static org.adhuc.cena.menu.port.adapter.rest.ingredient.IngredientJsonAssertion.assertJsonContainsIngredient;
import static org.adhuc.cena.menu.port.adapter.rest.ingredient.IngredientJsonAssertion.buildIngredientSelfLink;

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

import org.adhuc.cena.menu.application.IngredientAppService;
import org.adhuc.cena.menu.configuration.MenuGenerationProperties;
import org.adhuc.cena.menu.configuration.WebSecurityConfiguration;
import org.adhuc.cena.menu.domain.model.ingredient.CreateIngredient;
import org.adhuc.cena.menu.port.adapter.rest.ControllerTestSupport;
import org.adhuc.cena.menu.port.adapter.web.WebIndexController;
import org.adhuc.cena.menu.support.security.WithCommunityUser;
import org.adhuc.cena.menu.support.security.WithIngredientManager;

/**
 * The {@link IngredientsController} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("integration")
@Tag("restController")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = { IngredientsController.class, WebIndexController.class },
        includeFilters = { @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = IngredientResourceAssembler.class) })
@EnableConfigurationProperties(MenuGenerationProperties.class)
@Import(WebSecurityConfiguration.class)
@DisplayName("Ingredients controller")
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class IngredientsControllerTest extends ControllerTestSupport {

    private static final String  INGREDIENTS_API_URL = "/api/ingredients";

    @Autowired
    private MockMvc              mvc;

    @MockBean
    private IngredientAppService ingredientAppServiceMock;

    @Nested
    @DisplayName("with empty list")
    class WithEmptyList {

        @BeforeEach
        public void setUp() {
            when(ingredientAppServiceMock.getIngredients()).thenReturn(Collections.emptyList());
        }

        @Test
        @DisplayName("getting list returns OK status")
        public void getIngredientsEmptyListStatusOK() throws Exception {
            mvc.perform(get(INGREDIENTS_API_URL)).andExpect(status().isOk());
        }

        @Test
        @DisplayName("getting list returns empty list")
        public void getIngredientsEmptyListNoData() throws Exception {
            mvc.perform(get(INGREDIENTS_API_URL)).andExpect(jsonPath("$._embedded.data").isArray())
                    .andExpect(jsonPath("$._embedded.data").isEmpty());
        }
    }

    @Nested
    @DisplayName("with 2 ingredients")
    class With2Recipes {

        @BeforeEach
        public void setUp() {
            when(ingredientAppServiceMock.getIngredients()).thenReturn(Arrays.asList(tomato(), cucumber()));
        }

        @Test
        @DisplayName("getting list returns OK status")
        public void getIngredientsStatusOK() throws Exception {
            mvc.perform(get(INGREDIENTS_API_URL)).andExpect(status().isOk());
        }

        @Test
        @DisplayName("getting list returns all recipes")
        public void getIngredientsContainsData() throws Exception {
            final ResultActions resultActions =
                    mvc.perform(get(INGREDIENTS_API_URL)).andExpect(jsonPath("$._embedded.data").isArray())
                            .andExpect(jsonPath("$._embedded.data").isNotEmpty())
                            .andExpect(jsonPath("$._embedded.data", hasSize(2)));
            assertJsonContainsIngredient(resultActions, "$._embedded.data[0]", tomato());
            assertJsonContainsIngredient(resultActions, "$._embedded.data[1]", cucumber());
        }

        @Test
        @DisplayName("getting list contains self link to resource")
        public void getIngredientsHasSelfLink() throws Exception {
            assertSelfLinkEqualToRequestUrl(mvc.perform(get(INGREDIENTS_API_URL)));
        }

    }

    @Test
    @DisplayName("creating ingredient as community user returns unauthorized status")
    @WithCommunityUser
    public void createIngredientAsCommunityUser() throws Exception {
        mvc.perform(post(INGREDIENTS_API_URL).contentType(APPLICATION_JSON).content(createTomatoRequest()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("creating ingredient with no name returns bad request status")
    @WithIngredientManager
    public void createIngredientNoNameRequest() throws Exception {
        mvc.perform(post(INGREDIENTS_API_URL).contentType(APPLICATION_JSON)
                .content(asJson(CreateIngredientRequest.builder().build()))).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("creating ingredient with invalid name returns bad request status")
    @WithIngredientManager
    public void createIngredientInvalidNameRequest() throws Exception {
        mvc.perform(post(INGREDIENTS_API_URL).contentType(APPLICATION_JSON)
                .content(asJson(CreateIngredientRequest.builder().name("").build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("creating valid ingredient returns created status")
    @WithIngredientManager
    public void createIngredientReturnsCreatedStatus() throws Exception {
        mvc.perform(post(INGREDIENTS_API_URL).contentType(APPLICATION_JSON).content(createTomatoRequest()))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("creating valid ingredient calls the application service with command")
    @WithIngredientManager
    public void createIngredientCallsAppServiceWithCommand() throws Exception {
        final ArgumentCaptor<CreateIngredient> commandCaptor = ArgumentCaptor.forClass(CreateIngredient.class);
        mvc.perform(post(INGREDIENTS_API_URL).contentType(APPLICATION_JSON).content(createTomatoRequest())).andReturn();

        verify(ingredientAppServiceMock).createIngredient(commandCaptor.capture());
        assertThat(commandCaptor.getValue()).isEqualToIgnoringGivenFields(createTomato(), "ingredientId");
    }

    @Test
    @DisplayName("creating valid ingredient returns location header with link to ingredient details")
    @WithIngredientManager
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

}
