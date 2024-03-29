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
import org.adhuc.cena.menu.domain.model.menu.MealFrequency;

import cucumber.api.Transform;
import cucumber.api.java.en.Given;
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

    private static final String               START_DATE_REGEX =
            "(yesterday|next monday|next sunday|[0-9]{4}-[0-9]{2}-[0-9]{2})";

    @Steps
    private MenusGenerationServiceClientSteps menusGenerationServiceClient;

    @Given("^generated menus by an authenticated user for a period of time of (\\d+) days starting from "
            + START_DATE_REGEX + "$")
    public void menusGeneratedByAuthenticatedUser(int days,
            @Transform(LocalDateTransformer.class) LocalDate startDate) {
        menusGenerationServiceClient.setMenusGenerationDays(days);
        menusGenerationServiceClient.setMenusGenerationStartDate(startDate);
        menusGenerationServiceClient.generateMenusForAuthenticatedUser();
    }

    @When("^he specifies a period of time of (\\d+) days starting from " + START_DATE_REGEX + "$")
    public void specifyMenusGenerationOptions(int days, @Transform(LocalDateTransformer.class) LocalDate startDate) {
        menusGenerationServiceClient.setMenusGenerationDays(days);
        menusGenerationServiceClient.setMenusGenerationStartDate(startDate);
    }

    @When("^he specifies the frequency of meals as (.*)$")
    public void specifyMealsFrequency(@Transform(MealFrequencyTransformer.class) MealFrequency frequency) {
        menusGenerationServiceClient.setMenusGenerationMealFrequency(frequency);
    }

    @When("^he generates the menus$")
    public void generateMenus() {
        menusGenerationServiceClient.generateMenus();
    }

    @When("^he lists the menus for the same period of time starting from the same day$")
    public void listMenus() {
        menusGenerationServiceClient.listMenus();
    }

    @Then("^the menus have been generated$")
    public void menusGenerated() {
        menusGenerationServiceClient.assertMenusSuccessfullyGenerated();
    }

    @Then("^the number of meals in the list is (\\d+)$")
    public void exactNumberOfMealsInMenusList(int mealsCount) {
        menusGenerationServiceClient.assertExactNumberOfMealsInMenusList(mealsCount);
    }

    @Then("^the meals distribution corresponds to the specifications$")
    public void mealsDistributionCorrespondToSpecifications() {
        menusGenerationServiceClient.assertMealsDistributionCorrespondToSpecifications();
    }

    @Then("^no meal has redundant recipe$")
    public void menusWithNoRedundantRecipe() {
        menusGenerationServiceClient.assertMenusHaveNoRedundantRecipe();
    }

    @Then("^no meal has the same main ingredients as the previous nor next day$")
    public void menusWithNoMainRecipeIngredientWithinConsecutiveDays() {
        menusGenerationServiceClient.assertMenusUseNotSameMainIngredientsConsecutiveDays();
    }

    @Then("^an error notifies that menu cannot be generated in the past$")
    public void errorOnMenusGenerationNoGenerationInThePast() {
        menusGenerationServiceClient.assertNoGenerationInThePast();
    }

    @Then("^the menus list is empty or different from authenticated user list$")
    public void emptyOrDifferentListFromAuthenticatedUser() {
        menusGenerationServiceClient.assertDifferentMenusListForCurrentUserAndAuthenticatedUser();
    }

}
