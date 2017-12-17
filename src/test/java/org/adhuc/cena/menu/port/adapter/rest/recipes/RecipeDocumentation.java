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

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSalad;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.adhuc.cena.menu.application.RecipeAppService;
import org.adhuc.cena.menu.configuration.MenuGenerationProperties;
import org.adhuc.cena.menu.configuration.WebSecurityConfiguration;
import org.adhuc.cena.menu.port.adapter.rest.ResultHandlerConfiguration;
import org.adhuc.cena.menu.port.adapter.rest.recipe.RecipeController;
import org.adhuc.cena.menu.port.adapter.rest.recipe.RecipeResourceAssembler;
import org.adhuc.cena.menu.port.adapter.web.WebIndexController;

/**
 * The recipe related rest-services documentation.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("integration")
@Tag("documentation")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = { RecipeController.class, WebIndexController.class },
        includeFilters = { @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = RecipeResourceAssembler.class) })
@ContextConfiguration(classes = ResultHandlerConfiguration.class)
@EnableConfigurationProperties(MenuGenerationProperties.class)
@Import(WebSecurityConfiguration.class)
@AutoConfigureRestDocs("target/generated-snippets")
@DisplayName("Recipe resource documentation")
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class RecipeDocumentation {

    private static final String            RECIPE_API_URL = "/api/recipes/{id}";

    @Autowired
    private MockMvc                        mvc;
    @Autowired
    private RestDocumentationResultHandler documentationHandler;

    @MockBean
    private RecipeAppService               recipeAppServiceMock;

    @BeforeEach
    public void setUp() {
        reset(recipeAppServiceMock);
    }

    @Test
    @DisplayName("generates recipe detail example")
    public void recipeDetailExample() throws Exception {
        when(recipeAppServiceMock.getRecipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID)).thenReturn(tomatoCucumberMozzaSalad());

        mvc.perform(get(RECIPE_API_URL, TOMATO_CUCUMBER_MOZZA_SALAD_ID.toString())).andExpect(status().isOk())
                .andDo(documentationHandler.document(
                        pathParameters(parameterWithName("id").description("The recipe identity")),
                        links(linkWithRel("self").description("This <<resources-recipe,recipe>>"),
                                linkWithRel("ingredients")
                                        .description("The recipe <<resources-ingredient,ingredients>>")),
                        responseFields(fieldWithPath("id").description("The recipe identity"),
                                fieldWithPath("name").description("The recipe name"),
                                fieldWithPath("content").description("The recipe content"),
                                fieldWithPath("author").description("The recipe author"), subsectionWithPath("_links")
                                        .description("<<resources-recipe-links,Links>> to other resources"))));
    }

}
