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
package org.adhuc.cena.menu.acceptance.steps.serenity.recipes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.adhuc.cena.menu.acceptance.steps.serenity.ingredients.IngredientListServiceClientSteps;
import org.adhuc.cena.menu.acceptance.steps.serenity.ingredients.IngredientValue;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

/**
 * The recipe ingredients list rest-service client steps definition.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@SuppressWarnings("serial")
public class RecipeIngredientsListServiceClientSteps extends AbstractRecipeServiceClientSteps {

    @Steps
    private IngredientListServiceClientSteps ingredientListServiceClient;

    @Step("Add ingredient to recipe")
    public void addIngredientToRecipe() {
        addIngredientToRecipe(recipe(), ingredientListServiceClient.ingredient());
    }

    @Step("Add ingredient {1} to recipe {0}")
    private void addIngredientToRecipe(RecipeValue recipe, IngredientValue ingredient) {
        final String recipeIngredientsResourceUrl = recipe().getIngredientsListUrl();
        rest().put(recipeIngredientsResourceUrl + "/" + ingredient.id()).andReturn();
    }

    @Step("Assert ingredient has been successfully added to recipe")
    public void assertIngredientAdditionIsSuccessful() {
        assertNoContent();
    }

    @Step("Assert ingredient can be found in recipe's ingredients list")
    public void assertIngredientInRecipeIngredientsList() {
        assertIngredientInRecipeIngredientsList(recipe(), ingredientListServiceClient.ingredient());
    }

    @Step("Assert ingredient cannot be found in recipe's ingredients list")
    public void assertIngredientNotInRecipeIngredientsList() {
        assertIngredientNotInRecipeIngredientsList(recipe(), ingredientListServiceClient.ingredient());
    }

    @Step("Assert ingredient {1} can be found in recipe {0}'s ingredients list")
    private void assertIngredientInRecipeIngredientsList(RecipeValue recipe, IngredientValue ingredient) {
        assertThat(isIngredientInRecipeIngredientsList(recipe, ingredient)).isTrue();
    }

    @Step("Assert ingredient {1} cannot be found in recipe {0}'s ingredients list")
    private void assertIngredientNotInRecipeIngredientsList(RecipeValue recipe, IngredientValue ingredient) {
        assertThat(isIngredientInRecipeIngredientsList(recipe, ingredient)).isFalse();
    }

    private boolean isIngredientInRecipeIngredientsList(RecipeValue recipe, IngredientValue ingredient) {
        return getIngredientFromRecipeIngredientsList(recipe, ingredient).isPresent();
    }

    private Optional<IngredientValue> getIngredientFromRecipeIngredientsList(RecipeValue recipe,
            IngredientValue ingredient) {
        String recipeIngredientsResourceUrl = recipe.getIngredientsListUrl();
        return ingredientListServiceClient.getIngredientFromIngredientsList(recipeIngredientsResourceUrl, ingredient);
    }

}
