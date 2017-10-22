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
package org.adhuc.cena.menu.acceptance.steps.serenity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.adhuc.cena.menu.exception.ExceptionCode;
import org.adhuc.cena.menu.port.adapter.rest.recipe.CreateRecipeRequest;

import io.restassured.path.json.JsonPath;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import net.thucydides.core.annotations.Step;

/**
 * The recipe rest-service client steps definition.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@SuppressWarnings("serial")
public class RecipeServiceClientSteps extends AbstractServiceClientSteps {

    private RecipeValue recipe;

    @Step("Given a recipe \"{0}\"")
    public RecipeValue withRecipe(final RecipeValue recipe) {
        this.recipe = recipe;
        return recipe;
    }

    @Step("Creates the recipe")
    public void createRecipe() {
        createRecipe(recipe);
    }

    @Step("Creates the recipe {0}")
    public void createRecipe(final RecipeValue recipe) {
        final String recipesResourceUrl = getRecipesResourceUrl();
        rest().body(new CreateRecipeRequest(recipe.name(), recipe.content)).header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .post(recipesResourceUrl).andReturn();
    }

    @Step("Assert recipe is in recipes list")
    public void assertRecipeInRecipesList() {
        assertRecipeInRecipesList(recipe);
    }

    @Step("Assert recipe is not in recipes list")
    public void assertRecipeNotInRecipesList() {
        assertRecipeNotInRecipesList(recipe);
    }

    @Step("Assert recipe {0} is in recipes list")
    public void assertRecipeInRecipesList(final RecipeValue recipe) {
        assertThat(isRecipeInRecipesList(recipe)).isTrue();
    }

    @Step("Assert recipe {0} is not in recipes list")
    public void assertRecipeNotInRecipesList(final RecipeValue recipe) {
        assertThat(isRecipeInRecipesList(recipe)).isFalse();
    }

    @Step("Assert recipe has been successfully created")
    public void assertRecipeSuccessfullyCreated() {
        String recipeLocation = assertCreated().extract().header(LOCATION);
        // TODO compare created recipe with creation info, getting recipe detail from location
    }

    @Step("Assert recipe creation results in invalid request error")
    public void assertInvalidRequestError() {
        assertException(BAD_REQUEST, ExceptionCode.INVALID_REQUEST);
    }

    @Step("Get recipe from {0}")
    public RecipeValue getRecipeFromUrl(String recipeDetailUrl) {
        return rest().get(recipeDetailUrl).then().extract().as(RecipeValue.class);
    }

    private boolean isRecipeInRecipesList(final RecipeValue recipe) {
        return getRecipeFromRecipesList(recipe).isPresent();
    }

    private Optional<RecipeValue> getRecipeFromRecipesList(final RecipeValue recipe) {
        final String recipesResourceUrl = getRecipesResourceUrl();
        final String recipeName = recipe.name();
        final JsonPath jsonPath = rest().get(recipesResourceUrl).then().statusCode(OK.value()).extract().jsonPath();
        return Optional.ofNullable(jsonPath.param("name", recipeName)
                .getObject("_embedded.data.find { recipe->recipe.name == name }", RecipeValue.class));
    }

    private String getRecipesResourceUrl() {
        return getApiClientResource().getRecipes().getHref();
    }

    @Data
    @ToString(exclude = { "content" }, includeFieldNames = false)
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(fluent = true)
    @JsonAutoDetect(fieldVisibility = Visibility.ANY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RecipeValue {
        private String name;
        private String content;
    }

}
