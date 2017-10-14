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
package org.adhuc.cena.menu.acceptance.steps.authentication;

import static org.adhuc.cena.menu.acceptance.steps.serenity.AcceptanceAuthenticationMother.authenticatedUser;
import static org.adhuc.cena.menu.acceptance.steps.serenity.AcceptanceAuthenticationMother.ingredientManager;

import org.adhuc.cena.menu.acceptance.steps.serenity.IngredientServiceClientSteps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Steps;

/**
 * The authentication steps definitions for rest-services acceptance tests.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public class AuthenticationStepDefinitions {

    @Steps
    IngredientServiceClientSteps ingredientServiceClient;

    @Given("^an authenticated ingredient manager$")
    public void authenticatedIngredientManager() {
        ingredientServiceClient.withIngredientManager(ingredientManager());
    }

    @Given("^an anonymous user$")
    public void anonymousUser() {
        ingredientServiceClient.withAnonymousUser();
    }

    @Given("^an authenticated user that is not ingredient manager$")
    public void authenticatedNotIngredientManager() {
        ingredientServiceClient.withIngredientManager(authenticatedUser());
    }

    @Then("^an error notifies that user is not authenticated$")
    public void errorUserNotAuthenticated() {
        ingredientServiceClient.assertUserNotAuthenticated();
    }

    @Then("^an error notifies that user does not have sufficient rights$")
    public void errorUserWithInsufficientRights() {
        ingredientServiceClient.assertUserWithInsufficientRights();
    }

}
