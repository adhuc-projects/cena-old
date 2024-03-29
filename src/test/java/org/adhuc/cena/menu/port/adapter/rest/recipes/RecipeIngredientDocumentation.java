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

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.CUCUMBER_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.cucumber;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.cucumberInTomatoCucumberMozzaSalad;

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

import org.adhuc.cena.menu.application.RecipeIngredientAppService;
import org.adhuc.cena.menu.configuration.MenuGenerationProperties;
import org.adhuc.cena.menu.configuration.WebSecurityConfiguration;
import org.adhuc.cena.menu.domain.model.recipe.RecipeId;
import org.adhuc.cena.menu.domain.model.recipe.ingredient.RecipeIngredient;
import org.adhuc.cena.menu.port.adapter.rest.ControllerTestSupport;
import org.adhuc.cena.menu.port.adapter.rest.ResultHandlerConfiguration;
import org.adhuc.cena.menu.port.adapter.rest.ingredient.IngredientDocumentation;
import org.adhuc.cena.menu.port.adapter.rest.recipe.RecipeIngredientController;
import org.adhuc.cena.menu.port.adapter.rest.recipe.RecipeIngredientResourceAssembler;
import org.adhuc.cena.menu.port.adapter.web.WebIndexController;
import org.adhuc.cena.menu.support.security.WithAuthenticatedUser;

/**
 * The recipe ingredient related rest-services documentation.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("integration")
@Tag("documentation")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = { RecipeIngredientController.class, WebIndexController.class }, includeFilters = {
        @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = RecipeIngredientResourceAssembler.class) })
@ContextConfiguration(classes = ResultHandlerConfiguration.class)
@EnableConfigurationProperties(MenuGenerationProperties.class)
@Import(WebSecurityConfiguration.class)
@AutoConfigureRestDocs("target/generated-snippets")
@DisplayName("Recipe ingredient resource documentation")
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class RecipeIngredientDocumentation extends ControllerTestSupport {

    private static final String            RECIPE_INGREDIENT_API_URL =
            "/api/recipes/{recipeId}/ingredients/{ingredientId}";

    @Autowired
    private MockMvc                        mvc;
    @Autowired
    private RestDocumentationResultHandler documentationHandler;

    @MockBean
    private RecipeIngredientAppService     recipeIngredientAppServiceMock;

    @Test
    @DisplayName("generates recipe ingredient detail example")
    public void recipeIngredientDetailExample() throws Exception {
        RecipeId recipeId = TOMATO_CUCUMBER_MOZZA_SALAD_ID;
        RecipeIngredient cucumber = new RecipeIngredient(recipeId, cucumberInTomatoCucumberMozzaSalad(), cucumber());
        when(recipeIngredientAppServiceMock.getRecipeIngredient(anyObject(), anyObject())).thenReturn(cucumber);

        mvc.perform(get(RECIPE_INGREDIENT_API_URL, recipeId, CUCUMBER_ID)).andExpect(status().isOk())
                .andDo(documentationHandler.document(
                        pathParameters(parameterWithName("recipeId").description("The recipe identity"),
                                parameterWithName("ingredientId").description("The ingredient identity")),
                        links(linkWithRel("self")
                                .description("This <<resources-recipe-ingredient,recipe ingredient detail>>"),
                                linkWithRel("list")
                                        .description("The <<resources-recipe-ingredients,recipe ingredients list>>")),
                        IngredientDocumentation
                                .ingredientResponseFields(
                                        "<<resources-recipe-ingredient-links,Links>> to other resources")
                                .and(fieldWithPath("mainIngredient").description("Main ingredient of the recipe"))));
    }

    @Test
    @DisplayName("generates recipe ingredient addition example")
    @WithAuthenticatedUser
    public void recipeIngredientsAddExample() throws Exception {
        doNothing().when(recipeIngredientAppServiceMock).addIngredientToRecipe(anyObject());

        mvc.perform(put(RECIPE_INGREDIENT_API_URL, TOMATO_CUCUMBER_MOZZA_SALAD_ID, CUCUMBER_ID).param("main", "true"))
                .andExpect(status().isNoContent())
                .andDo(documentationHandler.document(
                        pathParameters(parameterWithName("recipeId").description("The recipe identity"),
                                parameterWithName("ingredientId").description("The ingredient identity")),
                        requestParameters(parameterWithName("main").description("Main ingredient of the recipe"))));
    }

}
