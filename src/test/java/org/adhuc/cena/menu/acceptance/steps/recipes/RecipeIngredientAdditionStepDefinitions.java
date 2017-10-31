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

import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT;

import org.adhuc.cena.menu.acceptance.steps.serenity.recipes.AbstractRecipeStorageSteps.RecipeValue;
import org.adhuc.cena.menu.acceptance.steps.serenity.recipes.RecipesListServiceClientSteps;

import cucumber.api.java.en.Given;
import cucumber.runtime.java.StepDefAnnotation;
import net.thucydides.core.annotations.Steps;

/**
 * The recipe ingredient addition steps definitions for rest-services acceptance tests.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@StepDefAnnotation
public class RecipeIngredientAdditionStepDefinitions {

    @Steps
    private RecipesListServiceClientSteps recipesListServiceClient;

    @Given("^an existing \"(.*)\" recipe created by this user$")
    public void recipeCreatedByAuthenticatedUser(String recipeName) {
        // TODO get recipe from recipeName through cucumber transformer
        RecipeValue recipe = new RecipeValue(recipeName, TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT);
        recipe = recipesListServiceClient.assumeRecipeInRecipesList(recipe);
        // TODO get authenticated user information from world
        recipesListServiceClient.assumeRecipeCreatedByAuthenticatedUser(recipe, "authenticated-user");
    }

}