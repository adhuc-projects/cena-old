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

import static org.junit.Assume.assumeFalse;
import static org.springframework.http.HttpStatus.OK;

import java.util.Optional;

import org.adhuc.cena.menu.model.Ingredient;

import io.restassured.path.json.JsonPath;
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

    @Step("Given an ingredient named \"{0}\"")
    public Ingredient withIngredient(final String ingredientName) {
        return new Ingredient(ingredientName);
    }

    @Step("Assume ingredient {0} is not in ingredients list")
    public void assumeIngredientNotInIngredientsList(final Ingredient ingredient) {
        assumeFalse("Ingredient " + ingredient.name() + " should not exist", isIngredientInIngredientsList(ingredient));
    }

    private boolean isIngredientInIngredientsList(final Ingredient ingredient) {
        return getIngredientFromIngredientsList(ingredient).isPresent();
    }

    private Optional<Ingredient> getIngredientFromIngredientsList(final Ingredient ingredient) {
        final String ingredientsResourceUrl = getIngredientsResourceUrl();
        final String ingredientName = ingredient.name();
        final JsonPath jsonPath = rest().get(ingredientsResourceUrl).then().statusCode(OK.value()).extract().jsonPath();
        return Optional.ofNullable(jsonPath.param("name", ingredientName)
                .getObject("data.find { ingredient->ingredient.name == name }", Ingredient.class));
    }

    private String getIngredientsResourceUrl() {
        return "/api/ingredients";
    }

}
