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
package org.adhuc.cena.menu.acceptance.steps.recipes;

import org.adhuc.cena.menu.acceptance.steps.serenity.recipes.RecipesListServiceClientSteps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.runtime.java.StepDefAnnotation;
import net.thucydides.core.annotations.Steps;

/**
 * The recipe list steps definitions for rest-services acceptance tests.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@StepDefAnnotation
public class RecipesListStepDefinitions {

    @Steps
    private RecipesListServiceClientSteps recipesListServiceClient;

    @Given("^a list of existing recipes with at least (\\d+) elements$")
    public void existingRecipes(int recipesCount) {
        recipesListServiceClient.assumeAtLeastExistingRecipes(recipesCount);
    }

    @Then("^the recipe can be found in the list$")
    public void recipeFoundInList() {
        recipesListServiceClient.assertRecipeInRecipesList();
    }

    @Then("^the recipe cannot be found in the list$")
    public void recipeNotFoundInList() {
        recipesListServiceClient.assertRecipeNotInRecipesList();
    }

}
