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
package org.adhuc.cena.menu.acceptance.steps.serenity.recipes;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.adhuc.cena.menu.acceptance.support.authentication.AuthenticationType;
import org.adhuc.cena.menu.exception.ExceptionCode;
import org.adhuc.cena.menu.port.adapter.rest.recipe.CreateRecipeRequest;

import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

/**
 * The recipe rest-service client steps definition.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@SuppressWarnings("serial")
public class RecipeCreationServiceClientSteps extends AbstractRecipeServiceClientSteps {

    @Steps
    private RecipeDetailServiceClientSteps recipeDetailServiceClient;

    @Step("Create the recipe {0}")
    public void createRecipe(RecipeValue recipe) {
        storeRecipeIfEmpty(recipe);
        createRecipe(recipe, rest());
    }

    @Step("Create the recipe {0} as authenticated user")
    public void createRecipeAsAuthenticatedUser(RecipeValue recipe) {
        createRecipe(recipe, restWithAuth(AuthenticationType.AUTHENTICATED_USER));
    }

    @Step("Assert recipe has been successfully created")
    public void assertRecipeSuccessfullyCreated() {
        String recipeLocation = assertCreated().extract().header(LOCATION);
        RecipeValue recipe = recipeDetailServiceClient.getRecipeFromUrl(recipeLocation);
        recipeDetailServiceClient.assertRecipeInfoIsEqualToExpected(recipe(), recipe);
    }

    @Step("Assert recipe creation results in invalid request error")
    public void assertInvalidRequestError() {
        assertException(BAD_REQUEST, ExceptionCode.INVALID_REQUEST);
    }

    @Step("Get recipe from {0}")
    public RecipeValue getRecipeFromUrl(String recipeDetailUrl) {
        return rest().get(recipeDetailUrl).then().extract().as(RecipeValue.class);
    }

    private void createRecipe(RecipeValue recipe, RequestSpecification rest) {
        String recipesResourceUrl = getRecipesResourceUrl(rest);
        rest.header(CONTENT_TYPE, APPLICATION_JSON_VALUE).body(new CreateRecipeRequest(recipe.name(), recipe.content()))
                .post(recipesResourceUrl).andReturn();
    }

}
