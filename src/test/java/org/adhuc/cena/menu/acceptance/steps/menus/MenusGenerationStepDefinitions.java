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
package org.adhuc.cena.menu.acceptance.steps.menus;

import java.time.LocalDate;

import org.adhuc.cena.menu.acceptance.steps.LocalDateTransformer;
import org.adhuc.cena.menu.acceptance.steps.serenity.menus.MenusGenerationServiceClientSteps;
import org.adhuc.cena.menu.domain.model.menu.MealFrequence;

import cucumber.api.Transform;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.StepDefAnnotation;
import net.thucydides.core.annotations.Steps;

/**
 * The menus generation steps definitions for rest-services acceptance tests.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@StepDefAnnotation
public class MenusGenerationStepDefinitions {

    @Steps
    private MenusGenerationServiceClientSteps menusGenerationServiceClient;

    @When("^he specifies a period of time of (\\d+) days starting from ([0-9]{4}-[0-9]{2}-[0-9]{2})$")
    public void specifyMenusGenerationOptions(int days, @Transform(LocalDateTransformer.class) LocalDate startDate) {
        menusGenerationServiceClient.setMenusGenerationDays(days);
        menusGenerationServiceClient.setMenusGenerationStartDate(startDate);
    }

    @When("^he specifies the frequence of meals as (.*)$")
    public void specifyMealsFrequence(@Transform(MealFrequenceTransformer.class) MealFrequence frequence) {
        menusGenerationServiceClient.setMenusGenerationMealFrequence(frequence);
    }

    @When("^he generates the menus$")
    public void generateMenus() {
        menusGenerationServiceClient.generateMenus();
    }

    @Then("^the menus have been generated$")
    public void menusGenerated() {
        menusGenerationServiceClient.assertMenusSuccessfullyGenerated();
    }

}
