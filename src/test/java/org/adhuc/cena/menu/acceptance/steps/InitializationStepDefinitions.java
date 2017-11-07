package org.adhuc.cena.menu.acceptance.steps;

import org.adhuc.cena.menu.acceptance.support.authentication.RestAuthenticationProvider;

import cucumber.api.java.Before;
import cucumber.runtime.java.StepDefAnnotation;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;

/**
 * An initialization step definitions, configuring rest-assured properties.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Slf4j
@StepDefAnnotation
public class InitializationStepDefinitions {

    private static final String PORT_PROPERTY = "acceptance.rest.port";
    private static final String DEFAULT_PORT  = "8080";

    @Before
    public void init() {
        int port = getPort();
        log.info("Run tests with port {}", port);
        Serenity.initializeTestSession();
        SerenityRest.reset();
        SerenityRest.setDefaultPort(port);

        RestAuthenticationProvider.instance().clean();
    }

    private int getPort() {
        return Integer.parseInt(System.getProperty(PORT_PROPERTY, DEFAULT_PORT));
    }

}
