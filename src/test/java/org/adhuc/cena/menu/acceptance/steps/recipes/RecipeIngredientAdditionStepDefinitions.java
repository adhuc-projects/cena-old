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

import org.adhuc.cena.menu.acceptance.steps.serenity.AuthenticationServiceClientSteps;
import org.adhuc.cena.menu.acceptance.steps.serenity.recipes.RecipeIngredientsListServiceClientSteps;
import org.adhuc.cena.menu.acceptance.steps.serenity.recipes.RecipeValue;
import org.adhuc.cena.menu.acceptance.steps.serenity.recipes.RecipesListServiceClientSteps;

import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
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
    private RecipesListServiceClientSteps           recipesListServiceClient;
    @Steps
    private RecipeIngredientsListServiceClientSteps recipeIngredientsListServiceClient;
    @Steps
    private AuthenticationServiceClientSteps        authenticationServiceClientSteps;

    @Given("^an existing \"(.*)\" recipe$")
    public void existingRecipe(@Transform(RecipeValueTransformer.class) RecipeValue recipe) {
        recipe = recipesListServiceClient.assumeRecipeInRecipesList(recipe);
    }

    @Given("^an existing \"(.*)\" recipe authored by this user$")
    public void existingRecipeAuthoredByAuthenticatedUser(@Transform(RecipeValueTransformer.class) RecipeValue recipe) {
        recipe = recipesListServiceClient.assumeRecipeInRecipesList(recipe);
        recipesListServiceClient.assumeRecipeAuthoredByAuthenticatedUser(recipe,
                authenticationServiceClientSteps.getAuthenticatedUser());
    }

    @Given("^an existing \"(.*)\" recipe authored by another user$")
    public void existingRecipeAuthoredByAnotherUser(@Transform(RecipeValueTransformer.class) RecipeValue recipe) {
        recipe = recipesListServiceClient.assumeRecipeInRecipesList(recipe);
        recipesListServiceClient.assumeRecipeAuthoredByAnotherUser(recipe,
                authenticationServiceClientSteps.getAuthenticatedUser());
    }

    @Given("^a non-existent \"(.*)\" recipe$")
    public void nonExistentRecipe(@Transform(RecipeValueTransformer.class) RecipeValue recipe) {
        recipesListServiceClient.assumeRecipeNotInRecipesList(recipe);
    }

    @Given("^the ingredient is in the recipe's ingredients list$")
    public void givenIngredientInRecipeIngredientsList() {
        recipeIngredientsListServiceClient.assumeIngredientInRecipeIngredientsList();
    }

    @Given("^the ingredient is not in the recipe's ingredients list$")
    public void givenIngredientNotInRecipeIngredientsList() {
        recipeIngredientsListServiceClient.assumeIngredientNotInRecipeIngredientsList();
    }

    @When("^he adds the ingredient to the recipe$")
    public void addIngredientToRecipe() {
        recipeIngredientsListServiceClient.addIngredientToRecipe();
    }

    @Then("^the ingredient is added to recipe$")
    public void ingredientAddedToRecipe() {
        recipeIngredientsListServiceClient.assertIngredientAdditionIsSuccessful();
    }

    @Then("^the ingredient can be found in the recipe's ingredients list$")
    public void ingredientInRecipeIngredientsList() {
        recipeIngredientsListServiceClient.assertIngredientInRecipeIngredientsList();
    }

    @Then("^the ingredient cannot be found in the recipe's ingredients list$")
    public void ingredientNotInRecipeIngredientsList() {
        recipeIngredientsListServiceClient.assertIngredientNotInRecipeIngredientsList();
    }

    @Then("^an error notifies that ingredient to add does not exist$")
    public void ingredientDoesNotExist() {
        recipeIngredientsListServiceClient.assertIngredientNotFoundError();
    }

    @Then("^an error notifies that recipe to add ingredient to does not exist$")
    public void recipeDoesNotExist() {
        recipeIngredientsListServiceClient.assertRecipeNotFoundError();
    }

}
