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
package org.adhuc.cena.menu.acceptance.steps.actuator;

import org.adhuc.cena.menu.acceptance.steps.serenity.ActuatorServiceClientSteps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.StepDefAnnotation;
import net.thucydides.core.annotations.Steps;

/**
 * The health steps definitions for rest-services acceptance tests.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@StepDefAnnotation
public class HealthStepDefinitions {

    @Steps
    private ActuatorServiceClientSteps actuatorServiceClient;

    @Given("^a running service$")
    public void runningService() {
        // nothing to do here
    }

    @When("^I check the service health$")
    public void checkServiceHealth() {
        actuatorServiceClient.callHealthCheckService();
    }

    @Then("^the service health is ok$")
    public void serviceHealthIsOk() {
        actuatorServiceClient.assertResponseIsOk();
    }

    @Then("^the health detail is available$")
    public void healthDetailIsAvailable() {
        actuatorServiceClient.assertDiskUsageIsAvailable();
    }

}
