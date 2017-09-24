package org.adhuc.cena.menu.acceptance;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

/**
 * The menu generation API acceptance tests main executor.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/features")
public class MenuGenerationAcceptanceTest {

}
