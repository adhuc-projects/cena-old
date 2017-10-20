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

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_NAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.adhuc.cena.menu.configuration.MenuGenerationProperties;
import org.adhuc.cena.menu.configuration.WebSecurityConfiguration;
import org.adhuc.cena.menu.port.adapter.rest.ControllerTestSupport;
import org.adhuc.cena.menu.port.adapter.rest.ResultHandlerConfiguration;
import org.adhuc.cena.menu.port.adapter.rest.documentation.support.ConstrainedFields;
import org.adhuc.cena.menu.port.adapter.rest.recipe.CreateRecipeRequest;
import org.adhuc.cena.menu.port.adapter.rest.recipe.RecipeResourceAssembler;
import org.adhuc.cena.menu.port.adapter.rest.recipe.RecipesController;

/**
 * The recipes related rest-services documentation.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RecipesController.class,
        includeFilters = { @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = RecipeResourceAssembler.class) })
@ContextConfiguration(classes = ResultHandlerConfiguration.class)
@EnableConfigurationProperties(MenuGenerationProperties.class)
@Import(WebSecurityConfiguration.class)
@AutoConfigureRestDocs("target/generated-snippets")
public class RecipesDocumentation extends ControllerTestSupport {

    private static final String            RECIPES_API_URL = "/api/recipes";

    @Autowired
    private MockMvc                        mvc;
    @Autowired
    private RestDocumentationResultHandler documentationHandler;

    @Test
    @DirtiesContext
    @WithMockUser(authorities = "USER")
    public void recipesListExample() throws Exception {
        mvc.perform(
                post(RECIPES_API_URL).contentType(APPLICATION_JSON).content(createTomatoCucumberMozzaSaladRequest()))
                .andExpect(status().isCreated());

        mvc.perform(get(RECIPES_API_URL)).andExpect(status().isOk())
                .andDo(documentationHandler.document(
                        links(linkWithRel("self").description("This <<resources-recipes,recipes list>>")),
                        responseFields(
                                subsectionWithPath("_embedded.data")
                                        .description("An array of <<resources-recipe, Recipe resources>>"),
                                subsectionWithPath("_links")
                                        .description("<<resources-recipes-links,Links>> to other resources"))));
    }

    @Test
    @DirtiesContext
    @WithMockUser(authorities = "USER")
    public void recipesCreateExample() throws Exception {
        ConstrainedFields fields = new ConstrainedFields(CreateRecipeRequest.class);
        mvc.perform(
                post(RECIPES_API_URL).contentType(APPLICATION_JSON).content(createTomatoCucumberMozzaSaladRequest()))
                .andExpect(status().isCreated())
                .andDo(documentationHandler
                        .document(requestFields(fields.withPath("name").description("The name of the recipe"),
                                fields.withPath("content").description("The content of the recipe"))));
    }

    private String createTomatoCucumberMozzaSaladRequest() {
        return asJson(CreateRecipeRequest.builder().name(TOMATO_CUCUMBER_MOZZA_SALAD_NAME)
                .content(TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT).build());
    }

}
