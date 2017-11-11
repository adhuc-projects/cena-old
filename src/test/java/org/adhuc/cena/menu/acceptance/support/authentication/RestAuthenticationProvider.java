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

import static org.assertj.core.api.Assertions.assertThat;

import org.adhuc.cena.menu.acceptance.support.authentication.AcceptanceAuthenticationMother.AcceptanceAuthentication;

import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.rest.decorators.request.RequestSpecificationDecorated;

/**
 * Provides access to a {@link RequestSpecification} to call rest-services, managing the authentication process, and
 * abstracts the authentication definition.
 * <p>
 * This provider also manages the initialization of the underlying {@link SerenityRest#rest()}'s
 * {@link RequestSpecification}, to ensure that only one is created for each new test execution, and is used for each
 * call to {@link #rest()} or {@link #restWithAuth(AuthenticationType)}.
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

    private RequestSpecification     specification;
    private AcceptanceAuthentication authentication;

    /**
     * Cleans the currently defined authentication.
     */
    public void clean() {
        specification = SerenityRest.rest();
        withAnonymousUser();
    }

    /**
     * Provides a {@link RequestSpecification} with potential authentication process already defined.
     */
    public RequestSpecification rest() {
        return authentication.restWithAuth(cleanedSpecification());
    }

    /**
     * Provides a {@link RequestSpecification} with an authentication corresponding to the specified authentication
     * type. This method enables calling a rest-service with a specific authentication type to ensure the caller to call
     * the service with the right level of authentication (e.g. for assumptions).
     * <p>
     * This method does <b>not</b> set the authentication to be used for all calls, but provides a new request
     * specification with the authentication corresponding to the requested authentication type.
     */
    public RequestSpecification restWithAuth(AuthenticationType authenticationType) {
        return authenticationType.authentication().restWithAuth(cleanedSpecification());
    }

    /**
     * Indicates whether the authentication used during acceptance test is considered as authenticated.
     *
     * @return {@code true} if authenticated, {@code false} otherwise.
     */
    public boolean isAuthenticated() {
        return authentication.isAuthenticated();
    }

    /**
     * Gets the authenticated user. Only when {@link #isAuthenticated()}.
     *
     * @return the authenticated user.
     */
    public String getAuthenticatedUser() {
        assertThat(isAuthenticated());
        return authentication.getAuthenticatedUser();
    }

    /**
     * Defines an anonymous user, that is not authenticated.
     */
    public void withAnonymousUser() {
        withAuthentication(AcceptanceAuthenticationMother.anonymousUser());
    }

    /**
     * Defines an authenticated user, with no special roles.
     */
    public void withAuthenticatedUser() {
        withAuthentication(AcceptanceAuthenticationMother.authenticatedUser());
    }

    /**
     * Defines another authenticated user, with no special roles.
     */
    public void withAnotherAuthenticatedUser() {
        withAuthentication(AcceptanceAuthenticationMother.anotherAuthenticatedUser());
    }

    /**
     * Defines an ingredient manager.
     */
    public void withIngredientManager() {
        withAuthentication(AcceptanceAuthenticationMother.ingredientManager());
    }

    /**
     * Defines an actuator manager.
     */
    public void withActuatorManager() {
        withAuthentication(AcceptanceAuthenticationMother.actuatorManager());
    }

    private void withAuthentication(final AcceptanceAuthentication authentication) {
        this.authentication = authentication;
    }

    private RequestSpecification cleanedSpecification() {
        if (RequestSpecificationDecorated.class.isAssignableFrom(specification.getClass())) {
            RequestSpecificationDecorated decorated = (RequestSpecificationDecorated) specification;
            decorated.getCore().body("");
            decorated.getCore().removeCookies();
            decorated.getCore().removeHeaders();
        }
        return specification.auth().none();
    }

}
