package org.adhuc.cena.menu.acceptance.steps.serenity;

import static net.serenitybdd.rest.SerenityRest.rest;
import static net.serenitybdd.rest.SerenityRest.then;

import static org.springframework.http.HttpStatus.OK;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

/**
 * The actuator rest-service client steps definition.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@SuppressWarnings("serial")
public class ActuatorServiceClientSteps extends ScenarioSteps {

    @Step("Call health check service")
    public void callHealthCheckService() {
        rest().get("/health").andReturn();
    }

    @Step("Assert rest-service response is OK")
    public void assertResponseIsOk() {
        then().statusCode(OK.value());
    }

}
