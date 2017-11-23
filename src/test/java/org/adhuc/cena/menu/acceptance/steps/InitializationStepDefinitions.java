package org.adhuc.cena.menu.acceptance.steps;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.adhuc.cena.menu.acceptance.support.authentication.RestAuthenticationProvider;

import cucumber.api.java.Before;
import cucumber.runtime.java.StepDefAnnotation;
import io.restassured.internal.mapping.Jackson2Mapper;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.factory.Jackson2ObjectMapperFactory;
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
        SerenityRest.objectMapper(buildJsonMapper());

        RestAuthenticationProvider.instance().clean();
    }

    private int getPort() {
        return Integer.parseInt(System.getProperty(PORT_PROPERTY, DEFAULT_PORT));
    }

    private ObjectMapper buildJsonMapper() {
        Jackson2ObjectMapperFactory factory = new Jackson2ObjectMapperFactory() {
            @SuppressWarnings("rawtypes")
            @Override
            public com.fasterxml.jackson.databind.ObjectMapper create(Class arg0, String arg1) {
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
                return mapper;
            }
        };
        return new Jackson2Mapper(factory);
    }

}
