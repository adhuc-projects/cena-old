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
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import javax.servlet.RequestDispatcher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.adhuc.cena.menu.configuration.MenuGenerationProperties;
import org.adhuc.cena.menu.configuration.WebSecurityConfiguration;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientNameAlreadyUsedException;
import org.adhuc.cena.menu.exception.ExceptionCode;
import org.adhuc.cena.menu.port.adapter.rest.documentation.support.ErrorsSnippet;
import org.adhuc.cena.menu.port.adapter.web.WebIndexController;
import org.adhuc.cena.menu.support.security.WithAuthenticatedUser;

/**
 * The general API documentation.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("integration")
@Tag("documentation")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = { ApiIndexController.class, WebIndexController.class })
@ContextConfiguration(classes = ResultHandlerConfiguration.class)
@EnableConfigurationProperties(MenuGenerationProperties.class)
@Import(WebSecurityConfiguration.class)
@AutoConfigureRestDocs("target/generated-snippets")
@DisplayName("API documentation")
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class ApiDocumentation extends ControllerTestSupport {

    private static final String            API_URL = "/api";

    @Autowired
    private MockMvc                        mvc;
    @Autowired
    private RestDocumentationResultHandler documentationHandler;

    @Test
    @DisplayName("generates headers example")
    public void headersExample() throws Exception {
        mvc.perform(get(API_URL)).andExpect(status().isOk())
                .andDo(documentationHandler.document(responseHeaders(headerWithName("Content-Type")
                        .description("The Content-Type of the payload, e.g. `application/hal+json`"))));
    }

    @Test
    @WithAuthenticatedUser
    @DisplayName("generates index example")
    public void indexExample() throws Exception {
        mvc.perform(get(API_URL)).andExpect(status().isOk()).andDo(documentationHandler.document(links(
                linkWithRel("documentation").description("This documentation"),
                linkWithRel("management").description(
                        "The https://docs.spring.io/spring-boot/docs/1.5.7.RELEASE/reference/htmlsingle/#production-ready-endpoints[Spring Boot Actuator] endpoints"),
                linkWithRel("ingredients").description("The <<resources-ingredients,Ingredients resource>>"),
                linkWithRel("recipes").description("The <<resources-recipes,Recipes resource>>"),
                linkWithRel("menus").description("The <<resources-menus,Menus resource>>")),
                responseFields(subsectionWithPath("_links")
                        .description("<<resources-index-links,Links>> to other resources"))));
    }

    @Test
    public void errorExample() throws Exception {
        mvc.perform(get("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400)
                .requestAttr(RequestDispatcher.ERROR_REQUEST_URI, "/api/ingredients")
                .requestAttr(RequestDispatcher.ERROR_MESSAGE, "Ingredient name 'Tomato' is already used")
                .requestAttr(DefaultErrorAttributes.class.getName() + ".ERROR",
                        new IngredientNameAlreadyUsedException("Tomato")))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("error", is("Bad Request")))
                .andExpect(jsonPath("timestamp", is(notNullValue()))).andExpect(jsonPath("status", is(400)))
                .andExpect(jsonPath("path", is(notNullValue())))
                .andExpect(jsonPath("code", is(ExceptionCode.INGREDIENT_NAME_ALREADY_USED.code())))
                .andDo(documentationHandler.document(responseFields(
                        fieldWithPath("error").description("The HTTP error that occurred, e.g. `Bad Request`"),
                        fieldWithPath("message").description("A description of the cause of the error"),
                        fieldWithPath("path").description("The path to which the request was made"),
                        fieldWithPath("status").description("The HTTP status code, e.g. `400`"),
                        fieldWithPath("timestamp")
                                .description("The time, in milliseconds, at which the error occurred"),
                        fieldWithPath("code").description("The more specific error code"))));
    }

    @Test
    public void errorsList() throws Exception {
        mvc.perform(get("/api")).andExpect(status().isOk())
                .andDo(documentationHandler.document(new ErrorsSnippet(Arrays.asList(ExceptionCode.values()))));
    }

}
