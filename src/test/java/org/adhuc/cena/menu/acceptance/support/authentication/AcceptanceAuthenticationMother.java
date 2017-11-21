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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import io.restassured.specification.RequestSpecification;

/**
 * An object mother providing authentication for acceptance tests.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public final class AcceptanceAuthenticationMother {

    public static enum AcceptanceAuthenticationKey {
        ANONYMOUS_USER,
        AUTHENTICATED_USER,
        ANOTHER_AUTHENTICATED_USER,
        INGREDIENT_MANAGER,
        ACTUATOR_MANAGER;
    }

    private static AcceptanceAuthenticationMother                      INSTANCE = new AcceptanceAuthenticationMother();

    private Map<AcceptanceAuthenticationKey, AcceptanceAuthentication> authentications;

    public static AcceptanceAuthenticationMother instance() {
        return INSTANCE;
    }

    private AcceptanceAuthenticationMother() {
        authentications = new HashMap<>();
        authentications.put(AcceptanceAuthenticationKey.ANONYMOUS_USER, new AnonymousAuthentication());
        authentications.put(AcceptanceAuthenticationKey.AUTHENTICATED_USER,
                new BasicAuthentication("authenticated-user", "authenticated-user"));
        authentications.put(AcceptanceAuthenticationKey.ANOTHER_AUTHENTICATED_USER,
                new BasicAuthentication("another-user", "another-user"));
        authentications.put(AcceptanceAuthenticationKey.INGREDIENT_MANAGER,
                new BasicAuthentication("ingredient-manager", "ingredient-manager"));
        authentications.put(AcceptanceAuthenticationKey.ACTUATOR_MANAGER,
                new BasicAuthentication("management", "management"));
    }

    protected AcceptanceAuthentication findByAuthenticationKey(AcceptanceAuthenticationKey authenticationKey) {
        return authentications.get(authenticationKey);
    }

    protected AcceptanceAuthentication findByAuthenticatedUser(String authenticatedUser) {
        Optional<AcceptanceAuthentication> authentication = authentications.values().stream()
                .filter(a -> a.isAuthenticated() && a.getAuthenticatedUser().equals(authenticatedUser)).findFirst();
        assertThat(authentication).isPresent();
        return authentication.get();
    }

    protected AcceptanceAuthentication anonymousUser() {
        return authentications.get(AcceptanceAuthenticationKey.ANONYMOUS_USER);
    }

    protected AcceptanceAuthentication authenticatedUser() {
        return authentications.get(AcceptanceAuthenticationKey.AUTHENTICATED_USER);
    }

    protected AcceptanceAuthentication anotherAuthenticatedUser() {
        return authentications.get(AcceptanceAuthenticationKey.ANOTHER_AUTHENTICATED_USER);
    }

    protected AcceptanceAuthentication ingredientManager() {
        return authentications.get(AcceptanceAuthenticationKey.INGREDIENT_MANAGER);
    }

    protected AcceptanceAuthentication actuatorManager() {
        return authentications.get(AcceptanceAuthenticationKey.ACTUATOR_MANAGER);
    }

    /**
     * Provides convenient methods to set the authentication process into a rest-assured {@link RequestSpecification}.
     *
     * @author Alexandre Carbenay
     *
     * @version 0.1.0
     * @since 0.1.0
     */
    protected static interface AcceptanceAuthentication {
        /**
         * Sets the authentication process into the specified request specification.
         *
         * @param specification
         *            the request specification.
         *
         * @return the request specification.
         */
        RequestSpecification restWithAuth(RequestSpecification specification);

        /**
         * Indicates whether the authentication process authenticates the user or not.
         *
         * @return {@code true} if the authentication process authenticates the user, {@code false} otherwise.
         */
        boolean isAuthenticated();

        /**
         * Gets the authenticated user if {@link #isAuthenticated()} is {@code true}.
         *
         * @return the authenticated user, if any.
         */
        String getAuthenticatedUser();
    }

    /**
     * An anonymous authentication process, responsible for ensuring that no authentication is set on a request
     * specification.
     *
     * @author Alexandre Carbenay
     *
     * @version 0.1.0
     * @since 0.1.0
     */
    private static class AnonymousAuthentication implements AcceptanceAuthentication {
        @Override
        public RequestSpecification restWithAuth(RequestSpecification specification) {
            return specification.auth().none();
        }

        @Override
        public boolean isAuthenticated() {
            return false;
        }

        @Override
        public String getAuthenticatedUser() {
            throw new UnsupportedOperationException("Not authenticated");
        }
    }

    /**
     * A basic authentication process.
     *
     * @author Alexandre Carbenay
     *
     * @version 0.1.0
     * @since 0.1.0
     */
    private static class BasicAuthentication implements AcceptanceAuthentication {
        private final String username;
        private final String password;

        public BasicAuthentication(final String username, final String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public RequestSpecification restWithAuth(RequestSpecification specification) {
            return specification.auth().preemptive().basic(username, password);
        }

        @Override
        public boolean isAuthenticated() {
            return true;
        }

        @Override
        public String getAuthenticatedUser() {
            return username;
        }
    }

}
