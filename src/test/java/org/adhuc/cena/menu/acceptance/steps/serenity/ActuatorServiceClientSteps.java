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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.boot.actuate.health.Status;

import org.adhuc.cena.menu.acceptance.support.resource.ManagementClientResource;

import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Step;

/**
 * The actuator rest-service client steps definition.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@SuppressWarnings("serial")
public class ActuatorServiceClientSteps extends AbstractServiceClientSteps {

    private ValidatableResponse healtCheckResponse;

    @Step("Call health check service")
    public void callHealthCheckService() {
        healtCheckResponse = rest().get(getHealthResourceUrl()).then();
    }

    @Step("Assert rest-service response is OK")
    public void assertResponseIsOk() {
        assertOk(healtCheckResponse);
    }

    @Step("Assert disk usage detail is available")
    public void assertDiskUsageIsAvailable() {
        healtCheckResponse.assertThat().body("diskSpace.status", equalTo(Status.UP.getCode()));
    }

    private String getManagementResourceUrl() {
        return getApiClientResource().getManagement().getHref();
    }

    private String getHealthResourceUrl() {
        return getManagementClientResource().getHealth().getHref();
    }

    private ManagementClientResource getManagementClientResource() {
        return rest().get(getManagementResourceUrl()).then().statusCode(OK.value()).extract()
                .as(ManagementClientResource.class);
    }

}
