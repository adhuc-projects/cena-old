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
package org.adhuc.cena.menu.acceptance.steps;

import org.adhuc.cena.menu.acceptance.steps.serenity.AuthenticationServiceClientSteps;

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
    AuthenticationServiceClientSteps authenticationServiceClient;

    @Given("^an authenticated ingredient manager$")
    public void authenticatedIngredientManager() {
        authenticationServiceClient.withIngredientManager();
    }

    @Given("^an anonymous user$")
    public void anonymousUser() {
        authenticationServiceClient.withAnonymousUser();
    }

    @Given("^an authenticated user$")
    public void authenticatedUser() {
        authenticationServiceClient.withAuthenticatedUser();
    }

    @Given("^another authenticated user$")
    public void anotherAuthenticatedUser() {
        authenticationServiceClient.withAnotherAuthenticatedUser();
    }

    @Given("^an authenticated user that is not ingredient manager$")
    public void authenticatedNotIngredientManager() {
        authenticationServiceClient.withAuthenticatedUser();
    }

    @Given("^an authenticated actuator manager$")
    public void authenticatedActuatorManager() {
        authenticationServiceClient.withActuatorManager();
    }

    @Then("^an error notifies that user is not authenticated$")
    public void errorUserNotAuthenticated() {
        authenticationServiceClient.assertUserNotAuthenticated();
    }

    @Then("^an error notifies that user does not have sufficient rights$")
    public void errorUserWithInsufficientRights() {
        authenticationServiceClient.assertUserWithInsufficientRights();
    }

}
