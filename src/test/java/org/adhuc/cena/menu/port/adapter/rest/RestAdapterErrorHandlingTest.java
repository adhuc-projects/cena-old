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
package org.adhuc.cena.menu.port.adapter.rest;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.RequestDispatcher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.adhuc.cena.menu.application.IngredientAppService;
import org.adhuc.cena.menu.application.RecipeAppService;
import org.adhuc.cena.menu.configuration.MenuGenerationProperties;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientNameAlreadyUsedException;
import org.adhuc.cena.menu.exception.ExceptionCode;

/**
 * A test class that validates REST adapter error handling.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("integration")
@Tag("restController")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableConfigurationProperties(MenuGenerationProperties.class)
@AutoConfigureMockMvc
@DisplayName("REST adapter error handling")
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class RestAdapterErrorHandlingTest {

    @Autowired
    private MockMvc              mvc;

    @MockBean
    private IngredientAppService ingredientAppServiceMock;
    @MockBean
    private RecipeAppService     recipeAppServiceMock;

    @Test
    @DisplayName("returns error information without code with no exception information")
    public void testErrorHandlingWithoutExceptionInfo() throws Exception {
        mvc.perform(get("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400)
                .requestAttr(RequestDispatcher.ERROR_REQUEST_URI, "/api/ingredients")
                .requestAttr(RequestDispatcher.ERROR_MESSAGE, "Ingredient name 'Tomato' is already used"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("error", is("Bad Request")))
                .andExpect(jsonPath("timestamp", is(notNullValue()))).andExpect(jsonPath("status", is(400)))
                .andExpect(jsonPath("path", is(notNullValue()))).andExpect(jsonPath("code").doesNotExist());
    }

    @Test
    @DisplayName("returns error information with default code with runtime exception")
    public void testErrorHandlingWithRuntimeExceptionInfo() throws Exception {
        mvc.perform(get("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400)
                .requestAttr(RequestDispatcher.ERROR_REQUEST_URI, "/api/ingredients")
                .requestAttr(RequestDispatcher.ERROR_MESSAGE, "Ingredient name 'Tomato' is already used")
                .requestAttr(DefaultErrorAttributes.class.getName() + ".ERROR", new RuntimeException("test")))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("error", is("Bad Request")))
                .andExpect(jsonPath("timestamp", is(notNullValue()))).andExpect(jsonPath("status", is(400)))
                .andExpect(jsonPath("path", is(notNullValue())))
                .andExpect(jsonPath("code", is(ExceptionCode.INTERNAL_ERROR.code())));
    }

    @Test
    @DisplayName("returns error information with default code with illegal argument exception")
    public void testErrorHandlingWithIllegalArgumentExceptionInfo() throws Exception {
        mvc.perform(get("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400)
                .requestAttr(RequestDispatcher.ERROR_REQUEST_URI, "/api/ingredients")
                .requestAttr(RequestDispatcher.ERROR_MESSAGE, "Ingredient name 'Tomato' is already used")
                .requestAttr(DefaultErrorAttributes.class.getName() + ".ERROR", new IllegalArgumentException("test")))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("error", is("Bad Request")))
                .andExpect(jsonPath("timestamp", is(notNullValue()))).andExpect(jsonPath("status", is(400)))
                .andExpect(jsonPath("path", is(notNullValue())))
                .andExpect(jsonPath("code", is(ExceptionCode.INTERNAL_ERROR.code())));
    }

    @Test
    @DisplayName("returns error information with custom code with custom exception")
    public void testErrorHandlingWithCenaExceptionInfo() throws Exception {
        mvc.perform(get("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400)
                .requestAttr(RequestDispatcher.ERROR_REQUEST_URI, "/api/ingredients")
                .requestAttr(RequestDispatcher.ERROR_MESSAGE, "Ingredient name 'Tomato' is already used")
                .requestAttr(DefaultErrorAttributes.class.getName() + ".ERROR",
                        new IngredientNameAlreadyUsedException("Tomato")))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("error", is("Bad Request")))
                .andExpect(jsonPath("timestamp", is(notNullValue()))).andExpect(jsonPath("status", is(400)))
                .andExpect(jsonPath("path", is(notNullValue())))
                .andExpect(jsonPath("code", is(ExceptionCode.INGREDIENT_NAME_ALREADY_USED.code())));
    }

}
