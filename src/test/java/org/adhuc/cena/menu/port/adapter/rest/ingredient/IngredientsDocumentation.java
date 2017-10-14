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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_NAME;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.cucumber;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.tomato;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.adhuc.cena.menu.application.IngredientAppService;
import org.adhuc.cena.menu.configuration.MenuGenerationProperties;
import org.adhuc.cena.menu.configuration.WebSecurityConfiguration;
import org.adhuc.cena.menu.domain.model.ingredient.CreateIngredient;
import org.adhuc.cena.menu.port.adapter.rest.ControllerTestSupport;
import org.adhuc.cena.menu.port.adapter.rest.ResultHandlerConfiguration;
import org.adhuc.cena.menu.port.adapter.rest.documentation.support.ConstrainedFields;

/**
 * The ingredient related rest-services documentation.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = IngredientsController.class,
        includeFilters = { @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = IngredientResourceAssembler.class) })
@ContextConfiguration(classes = ResultHandlerConfiguration.class)
@EnableConfigurationProperties(MenuGenerationProperties.class)
@Import(WebSecurityConfiguration.class)
@AutoConfigureRestDocs("target/generated-snippets")
public class IngredientsDocumentation extends ControllerTestSupport {

    private static final String            INGREDIENTS_API_URL = "/api/ingredients";

    @Autowired
    private MockMvc                        mvc;
    @Autowired
    private RestDocumentationResultHandler documentationHandler;

    @MockBean
    private IngredientAppService           ingredientAppServiceMock;

    @Before
    public void setUp() {
        reset(ingredientAppServiceMock);
    }

    @Test
    public void ingredientsListExample() throws Exception {
        when(ingredientAppServiceMock.getIngredients()).thenReturn(Arrays.asList(tomato(), cucumber()));

        mvc.perform(get(INGREDIENTS_API_URL)).andExpect(status().isOk())
                .andDo(documentationHandler.document(
                        links(linkWithRel("self").description("This <<resources-ingredients,ingredients list>>")),
                        responseFields(
                                subsectionWithPath("_embedded.data")
                                        .description("An array of <<resources-ingredient, Ingredient resources>>"),
                                subsectionWithPath("_links")
                                        .description("<<resources-ingredients-links,Links>> to other resources"))));
    }

    @Test
    @WithMockUser(authorities = "INGREDIENT_MANAGER")
    public void ingredientsCreateExample() throws Exception {
        final ArgumentCaptor<CreateIngredient> commandCaptor = ArgumentCaptor.forClass(CreateIngredient.class);
        doNothing().when(ingredientAppServiceMock).createIngredient(commandCaptor.capture());

        ConstrainedFields fields = new ConstrainedFields(CreateIngredientRequest.class);
        mvc.perform(post(INGREDIENTS_API_URL).with(csrf()).contentType(APPLICATION_JSON).content(createTomatoRequest()))
                .andExpect(status().isCreated()).andDo(documentationHandler
                        .document(requestFields(fields.withPath("name").description("The name of the ingredient"))));
    }

    private String createTomatoRequest() {
        return asJson(CreateIngredientRequest.builder().name(TOMATO_NAME).build());
    }

}
