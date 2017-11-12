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

import static net.serenitybdd.rest.SerenityRest.then;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.HttpStatus;

import org.adhuc.cena.menu.acceptance.support.authentication.AuthenticationType;
import org.adhuc.cena.menu.acceptance.support.authentication.RestAuthenticationProvider;
import org.adhuc.cena.menu.acceptance.support.resource.ApiClientResource;
import org.adhuc.cena.menu.exception.ExceptionCode;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.steps.ScenarioSteps;

/**
 * An abstract rest-service client steps definition.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@SuppressWarnings("serial")
public abstract class AbstractServiceClientSteps extends ScenarioSteps {

    private static final String                API_URL                    = "/api";

    protected final RestAuthenticationProvider restAuthenticationProvider = RestAuthenticationProvider.instance();

    protected final RequestSpecification rest() {
        return restAuthenticationProvider.rest();
    }

    protected final RequestSpecification restWithAuth(String authenticatedUser) {
        return restAuthenticationProvider.restWithAuth(authenticatedUser);
    }

    protected final RequestSpecification restWithAuth(AuthenticationType authenticationType) {
        return restAuthenticationProvider.restWithAuth(authenticationType);
    }

    protected final ApiClientResource getApiClientResource() {
        return getApiClientResource(rest());
    }

    protected final ApiClientResource getApiClientResource(RequestSpecification rest) {
        return rest.get(API_URL).then().statusCode(OK.value()).extract().as(ApiClientResource.class);
    }

    protected final ValidatableResponse assertOk() {
        return assertOk(then());
    }

    protected final ValidatableResponse assertOk(ValidatableResponse response) {
        return assertStatus(response, OK);
    }

    protected final ValidatableResponse assertCreated() {
        return assertCreated(then());
    }

    protected final ValidatableResponse assertCreated(ValidatableResponse response) {
        return assertStatus(response, CREATED);
    }

    protected final ValidatableResponse assertNoContent() {
        return assertNoContent(then());
    }

    protected final ValidatableResponse assertNoContent(ValidatableResponse response) {
        return assertStatus(response, NO_CONTENT);
    }

    protected final ValidatableResponse assertStatus(ValidatableResponse response, HttpStatus status) {
        return response.statusCode(status.value());
    }

    protected final ValidatableResponse assertException(final HttpStatus status) {
        return assertStatus(then(), status);
    }

    protected final ValidatableResponse assertException(final HttpStatus status, final ExceptionCode exceptionCode) {
        return assertException(status).and().body("code", equalTo(exceptionCode.code()));
    }

}
