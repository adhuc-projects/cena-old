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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.tomato;
import static org.adhuc.cena.menu.port.adapter.rest.ingredient.IngredientJsonAssertion.assertJsonContainsIngredient;

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

import org.adhuc.cena.menu.application.IngredientAppService;
import org.adhuc.cena.menu.configuration.MenuGenerationProperties;
import org.adhuc.cena.menu.configuration.WebSecurityConfiguration;
import org.adhuc.cena.menu.port.adapter.rest.ControllerTestSupport;
import org.adhuc.cena.menu.port.adapter.web.WebIndexController;

/**
 * The {@link IngredientController} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("integration")
@Tag("restController")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = { IngredientController.class, WebIndexController.class },
        includeFilters = { @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = IngredientResourceAssembler.class) })
@EnableConfigurationProperties(MenuGenerationProperties.class)
@Import(WebSecurityConfiguration.class)
@DisplayName("Ingredient controller")
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class IngredientControllerTest extends ControllerTestSupport {

    private static final String  INGREDIENT_API_URL = "/api/ingredients/{id}";

    @Autowired
    private MockMvc              mvc;

    @MockBean
    private IngredientAppService ingredientAppServiceMock;

    @Test
    @DisplayName("getting ingredient detail from invalid id returns not found status")
    public void getIngredientWithInvalidIdStatusNotFound() throws Exception {
        mvc.perform(get(INGREDIENT_API_URL, "invalid")).andExpect(status().isNotFound());
    }

    @Nested
    @DisplayName("getting tomato detail")
    class TomatoDetail {

        @BeforeEach
        public void setUp() {
            when(ingredientAppServiceMock.getIngredient(TOMATO_ID)).thenReturn(tomato());
        }

        @Test
        @DisplayName("returns OK status")
        public void getIngredientFoundStatusOK() throws Exception {
            mvc.perform(get(INGREDIENT_API_URL, TOMATO_ID.toString())).andExpect(status().isOk());
        }

        @Test
        @DisplayName("contains tomato data")
        public void getIngredientFoundContainsData() throws Exception {
            final ResultActions resultActions = mvc.perform(get(INGREDIENT_API_URL, TOMATO_ID.toString()));
            assertJsonContainsIngredient(resultActions, "$", tomato());
        }

        @Test
        @DisplayName("contains self link to detail")
        public void getIngredientsHasSelfLink() throws Exception {
            assertSelfLinkEqualToRequestUrl(mvc.perform(get(INGREDIENT_API_URL, TOMATO_ID.toString())));
        }

    }

}
