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

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.adhuc.cena.menu.exception.ExceptionCode;
import org.adhuc.cena.menu.port.adapter.rest.ingredient.CreateIngredientRequest;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

/**
 * The ingredient creation rest-service client steps definition.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@SuppressWarnings("serial")
public class IngredientCreationServiceClientSteps extends AbstractIngredientServiceClientSteps {

    @Steps
    private IngredientDetailServiceClientSteps ingredientDetailServiceClient;

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

    @Step("Assert ingredient has been successfully created")
    public void assertIngredientSuccessfullyCreated() {
        String ingredientLocation = assertCreated().extract().header(LOCATION);
        IngredientValue ingredient = ingredientDetailServiceClient.getIngredientFromUrl(ingredientLocation);
        ingredientDetailServiceClient.assertIngredientInfoIsEqualToExpected(ingredient(), ingredient);
    }

    @Step("Assert ingredient creation results in invalid request error")
    public void assertInvalidRequestError() {
        assertException(BAD_REQUEST, ExceptionCode.INVALID_REQUEST);
    }

    @Step("Assert ingredient creation results in already used name error")
    public void assertNameAlreadyUsed() {
        assertException(BAD_REQUEST, ExceptionCode.INGREDIENT_NAME_ALREADY_USED);
    }

}
