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

    protected static AcceptanceAuthentication anonymousUser() {
        return new AcceptanceAuthentication() {
            @Override
            public RequestSpecification restWithAuth(RequestSpecification specification) {
                return specification.auth().none();
            }
        };
    }

    protected static AcceptanceAuthentication authenticatedUser() {
        return new BasicAuthentication("authenticated-user", "authenticated-user");
    }

    protected static AcceptanceAuthentication ingredientManager() {
        return new BasicAuthentication("ingredient-manager", "ingredient-manager");
    }

    protected static AcceptanceAuthentication actuatorManager() {
        return new BasicAuthentication("management", "management");
    }

    protected static interface AcceptanceAuthentication {
        RequestSpecification restWithAuth(RequestSpecification specification);
    }

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
    }

}
