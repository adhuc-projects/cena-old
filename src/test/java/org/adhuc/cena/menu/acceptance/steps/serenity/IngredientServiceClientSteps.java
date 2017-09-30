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

import static net.serenitybdd.rest.SerenityRest.rest;
import static net.serenitybdd.rest.SerenityRest.then;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assume.assumeFalse;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Optional;

import org.adhuc.cena.menu.port.adapter.rest.ingredient.CreateIngredientRequest;

import io.restassured.path.json.JsonPath;
import lombok.Data;
import lombok.experimental.Accessors;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

/**
 * The ingredient rest-service client steps definition.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@SuppressWarnings("serial")
public class IngredientServiceClientSteps extends ScenarioSteps {

    private IngredientValue ingredient;

    @Step("Given an ingredient named \"{0}\"")
    public IngredientValue withIngredient(final String ingredientName) {
        ingredient = new IngredientValue(ingredientName);
        return ingredient;
    }

    @Step("Assume ingredient {0} is not in ingredients list")
    public void assumeIngredientNotInIngredientsList(final IngredientValue ingredient) {
        assumeFalse("Ingredient " + ingredient.name() + " should not exist", isIngredientInIngredientsList(ingredient));
    }

    @Step("Creates the ingredient")
    public void createIngredient() {
        createIngredient(ingredient);
    }

    @Step("Creates the ingredient {0}")
    public void createIngredient(final IngredientValue ingredient) {
        final String ingredientsResourceUrl = getIngredientsResourceUrl();
        rest().body(new CreateIngredientRequest(ingredient.name())).header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .post(ingredientsResourceUrl).andReturn();
    }

    @Step("Assert ingredient has been successfully created")
    public void assertIngredientSuccessfullyCreated() {
        then().statusCode(CREATED.value()).header(LOCATION, containsString("/api/ingredients/"));
        // TODO get ingredient calling url from location header, and comparing information
    }

    private boolean isIngredientInIngredientsList(final IngredientValue ingredient) {
        return getIngredientFromIngredientsList(ingredient).isPresent();
    }

    private Optional<IngredientValue> getIngredientFromIngredientsList(final IngredientValue ingredient) {
        final String ingredientsResourceUrl = getIngredientsResourceUrl();
        final String ingredientName = ingredient.name();
        final JsonPath jsonPath = rest().get(ingredientsResourceUrl).then().statusCode(OK.value()).extract().jsonPath();
        return Optional.ofNullable(jsonPath.param("name", ingredientName)
                .getObject("data.find { ingredient->ingredient.name == name }", IngredientValue.class));
    }

    private String getIngredientsResourceUrl() {
        return "/api/ingredients";
    }

    @Data
    @Accessors(fluent = true)
    public static class IngredientValue {
        private final String name;
    }

}
