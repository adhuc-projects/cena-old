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
package org.adhuc.cena.menu.acceptance.support.authentication;

import org.adhuc.cena.menu.acceptance.support.authentication.AcceptanceAuthenticationMother.AcceptanceAuthentication;

import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;

/**
 * Provides access to a {@link RequestSpecification} to call rest-services, managing the authentication process, and
 * abstracts the authentication definition.
 * <p>
 * Must be used as a singleton, using {@link #instance()} to ensure that it is shared between all steps.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public class RestAuthenticationProvider {

    private static RestAuthenticationProvider INSTANCE = new RestAuthenticationProvider();

    public static RestAuthenticationProvider instance() {
        return INSTANCE;
    }

    private AcceptanceAuthentication authentication;

    /**
     * Cleans the currently defined authentication.
     */
    public void clean() {
        authentication = null;
    }

    /**
     * Provides a {@link RequestSpecification} with potential authentication process already defined.
     */
    public RequestSpecification rest() {
        return authentication != null ? authentication.restWithAuth() : SerenityRest.rest();
    }

    /**
     * Defines an anonymous user, that is not authenticated.
     */
    public void withAnonymousUser() {
        withAuthentication(null);
    }

    /**
     * Defines an authenticated user, with no special roles.
     */
    public void withAuthenticatedUser() {
        withAuthentication(AcceptanceAuthenticationMother.authenticatedUser());
    }

    /**
     * Defines an ingredient manager.
     */
    public void withIngredientManager() {
        withAuthentication(AcceptanceAuthenticationMother.ingredientManager());
    }

    private void withAuthentication(final AcceptanceAuthentication authentication) {
        this.authentication = authentication;
    }

}
