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
package org.adhuc.cena.menu.acceptance.steps.serenity.ingredients;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Optional;

import org.adhuc.cena.menu.exception.ExceptionCode;
import org.adhuc.cena.menu.port.adapter.rest.ingredient.CreateIngredientRequest;

import io.restassured.path.json.JsonPath;
import net.thucydides.core.annotations.Step;

/**
 * The ingredient rest-service client steps definition.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@SuppressWarnings("serial")
public class IngredientServiceClientSteps extends AbstractIngredientStorageSteps {

    @Step("Assume ingredient {0} is not in ingredients list")
    public void assumeIngredientNotInIngredientsList(final IngredientValue ingredient) {
        assumeFalse("Ingredient " + ingredient.name() + " should not exist", isIngredientInIngredientsList(ingredient));
    }

    @Step("Assume ingredient {0} is in ingredients list")
    public void assumeIngredientInIngredientsList(final IngredientValue ingredient) {
        if (!isIngredientInIngredientsList(ingredient)) {
            createIngredient(ingredient);
        }
        assumeTrue(isIngredientInIngredientsList(ingredient));
    }

    @Step("Creates the ingredient")
    public void createIngredient() {
        createIngredient(ingredient());
    }

    @Step("Creates an ingredient without name")
    public void createIngredientWithoutName() {
        storeIngredient(new IngredientValue());
        createIngredient(ingredient());
    }

    @Step("Creates the ingredient {0}")
    public void createIngredient(final IngredientValue ingredient) {
        final String ingredientsResourceUrl = getIngredientsResourceUrl();
        rest().body(new CreateIngredientRequest(ingredient.name())).header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .post(ingredientsResourceUrl).andReturn();
    }

    @Step("Assert ingredient is in ingredients list")
    public void assertIngredientInIngredientsList() {
        assertIngredientInIngredientsList(ingredient());
    }

    @Step("Assert ingredient is not in ingredients list")
    public void assertIngredientNotInIngredientsList() {
        assertIngredientNotInIngredientsList(ingredient());
    }

    @Step("Assert ingredient {0} is in ingredients list")
    public void assertIngredientInIngredientsList(final IngredientValue ingredient) {
        assertThat(isIngredientInIngredientsList(ingredient)).isTrue();
    }

    @Step("Assert ingredient {0} is not in ingredients list")
    public void assertIngredientNotInIngredientsList(final IngredientValue ingredient) {
        assertThat(isIngredientInIngredientsList(ingredient)).isFalse();
    }

    @Step("Assert ingredient has been successfully created")
    public void assertIngredientSuccessfullyCreated() {
        String ingredientLocation = assertCreated().extract().header(LOCATION);
        IngredientValue ingredient = getIngredientFromUrl(ingredientLocation);
        assertIngredientInfoIsEqualToExpected(ingredient(), ingredient);
    }

    @Step("Get ingredient from {0}")
    public IngredientValue getIngredientFromUrl(String ingredientDetailUrl) {
        return rest().get(ingredientDetailUrl).then().extract().as(IngredientValue.class);
    }

    @Step("Assert ingredient {1} corresponds to expected {0}")
    public void assertIngredientInfoIsEqualToExpected(IngredientValue expected, IngredientValue actual) {
        assertThat(actual).isEqualTo(expected);
    }

    @Step("Assert ingredient creation results in invalid request error")
    public void assertInvalidRequestError() {
        assertException(BAD_REQUEST, ExceptionCode.INVALID_REQUEST);
    }

    @Step("Assert ingredient creation results in already used name error")
    public void assertNameAlreadyUsed() {
        assertException(BAD_REQUEST, ExceptionCode.INGREDIENT_NAME_ALREADY_USED);
    }

    private boolean isIngredientInIngredientsList(final IngredientValue ingredient) {
        return getIngredientFromIngredientsList(ingredient).isPresent();
    }

    private Optional<IngredientValue> getIngredientFromIngredientsList(final IngredientValue ingredient) {
        final String ingredientsResourceUrl = getIngredientsResourceUrl();
        final String ingredientName = ingredient.name();
        final JsonPath jsonPath = rest().get(ingredientsResourceUrl).then().statusCode(OK.value()).extract().jsonPath();
        return Optional.ofNullable(jsonPath.param("name", ingredientName)
                .getObject("_embedded.data.find { ingredient->ingredient.name == name }", IngredientValue.class));
    }

    private String getIngredientsResourceUrl() {
        return getApiClientResource().getIngredients().getHref();
    }

}