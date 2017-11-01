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
package org.adhuc.cena.menu.acceptance.steps.ingredients;

import org.adhuc.cena.menu.acceptance.steps.serenity.ingredients.IngredientCreationServiceClientSteps;
import org.adhuc.cena.menu.acceptance.steps.serenity.ingredients.IngredientListServiceClientSteps;
import org.adhuc.cena.menu.acceptance.steps.serenity.ingredients.IngredientValue;

import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.StepDefAnnotation;
import net.thucydides.core.annotations.Steps;

/**
 * The ingredient creation steps definitions for rest-services acceptance tests.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@StepDefAnnotation
public class IngredientCreationStepDefinitions {

    @Steps
    private IngredientListServiceClientSteps     ingredientListServiceClient;
    @Steps
    private IngredientCreationServiceClientSteps ingredientCreationServiceClient;

    @Given("^a non-existent \"(.*)\" ingredient$")
    public void nonExistentIngredient(@Transform(IngredientValueTransformer.class) IngredientValue ingredient) {
        ingredientListServiceClient.storeIngredient(ingredient);
        ingredientListServiceClient.assumeIngredientNotInIngredientsList(ingredient);
    }

    @Given("^an existing \"(.*)\" ingredient$")
    public void existingIngredient(@Transform(IngredientValueTransformer.class) IngredientValue ingredient) {
        ingredientListServiceClient.storeIngredient(ingredient);
        ingredientListServiceClient.assumeIngredientInIngredientsList(ingredient);
    }

    @When("^he creates the ingredient$")
    public void createIngredient() {
        ingredientCreationServiceClient.createIngredient();
    }

    @When("^he creates an ingredient without name$")
    public void createIngredientWithoutName() {
        ingredientCreationServiceClient.createIngredientWithoutName();
    }

    @Then("^the ingredient is created$")
    public void ingredientCreated() {
        ingredientCreationServiceClient.assertIngredientSuccessfullyCreated();
    }

    @Then("^an error notifies that ingredient must have a name$")
    public void errorOnIngredientCreationWithoutName() {
        ingredientCreationServiceClient.assertInvalidRequestError();
    }

    @Then("^an error notifies that ingredient already exists$")
    public void errorOnIngredientCreationNameAlreadyUsed() {
        ingredientCreationServiceClient.assertNameAlreadyUsed();
    }

    @Then("^the ingredient can be found in the list$")
    public void ingredientFoundInList() {
        ingredientListServiceClient.assertIngredientInIngredientsList();
    }

    @Then("^the ingredient cannot be found in the list$")
    public void ingredientNotFoundInList() {
        ingredientListServiceClient.assertIngredientNotInIngredientsList();
    }

}
