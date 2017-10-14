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
import static org.mockito.Mockito.reset;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.RequestDispatcher;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.adhuc.cena.menu.application.IngredientAppService;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientNameAlreadyUsedException;
import org.adhuc.cena.menu.exception.CenaException;
import org.adhuc.cena.menu.exception.ExceptionCode;

/**
 * A test class that validates REST adapter error handling.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestAdapterErrorHandlingTest {

    @Autowired
    private MockMvc              mvc;

    @MockBean
    private IngredientAppService ingredientAppServiceMock;

    @Before
    public void setUp() {
        reset(ingredientAppServiceMock);
    }

    @Test
    public void testErrorHandlingWithoutExceptionInfo() throws Exception {
        mvc.perform(get("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400)
                .requestAttr(RequestDispatcher.ERROR_REQUEST_URI, "/api/ingredients")
                .requestAttr(RequestDispatcher.ERROR_MESSAGE, "Ingredient name 'Tomato' is already used"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("error", is("Bad Request")))
                .andExpect(jsonPath("timestamp", is(notNullValue()))).andExpect(jsonPath("status", is(400)))
                .andExpect(jsonPath("path", is(notNullValue()))).andExpect(jsonPath("code").doesNotExist());
    }

    @Test
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

    @SpringBootApplication(exclude = SecurityAutoConfiguration.class)
    public static class RestAdapterErrorHandlingConfiguration {

        @RestController
        public static class RestExceptionTestController {

            @RequestMapping("/test/exception/illegal-argument")
            public String illegalArgument() {
                throw new IllegalArgumentException("test rest-service exception handling");
            }

            @RequestMapping("/test/exception/custom")
            public String customCenaException() {
                throw new CustomCenaException("custom message", ExceptionCode.INTERNAL_ERROR);
            }

            @RequestMapping("/test/exception/custom-with-status")
            public String customStatusCenaException() {
                throw new CustomWithStatusCenaException("custom with status message", ExceptionCode.INTERNAL_ERROR);
            }

        }

        @SuppressWarnings("serial")
        private static class CustomCenaException extends CenaException {
            public CustomCenaException(final String message, final ExceptionCode code) {
                super(message, code);
            }
        }

        @SuppressWarnings("serial")
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        private static class CustomWithStatusCenaException extends CenaException {
            public CustomWithStatusCenaException(final String message, final ExceptionCode code) {
                super(message, code);
            }
        }

    }

}