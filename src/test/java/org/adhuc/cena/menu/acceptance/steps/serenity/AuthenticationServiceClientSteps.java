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

import org.springframework.http.HttpStatus;

import net.thucydides.core.annotations.Step;

/**
 * The authentication rest-service client steps definition.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@SuppressWarnings("serial")
public class AuthenticationServiceClientSteps extends AbstractServiceClientSteps {

    @Step("Given an anonymous user")
    public void withAnonymousUser() {
        restAuthenticationProvider.withAnonymousUser();
    }

    @Step("Given an authenticated user")
    public void withAuthenticatedUser() {
        restAuthenticationProvider.withAuthenticatedUser();
    }

    @Step("Given another authenticated user")
    public void withAnotherAuthenticatedUser() {
        restAuthenticationProvider.withAnotherAuthenticatedUser();
    }

    @Step("Given an ingredient manager")
    public void withIngredientManager() {
        restAuthenticationProvider.withIngredientManager();
    }

    @Step("Given an actuator manager")
    public void withActuatorManager() {
        restAuthenticationProvider.withActuatorManager();
    }

    @Step("Assert user is not authenticated")
    public void assertUserNotAuthenticated() {
        assertException(HttpStatus.UNAUTHORIZED);
    }

    @Step("Assert user does not have sufficient rights")
    public void assertUserWithInsufficientRights() {
        assertException(HttpStatus.FORBIDDEN);
    }

}
