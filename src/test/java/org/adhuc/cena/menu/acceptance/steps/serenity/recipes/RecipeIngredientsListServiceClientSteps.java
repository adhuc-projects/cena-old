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
import static org.junit.Assume.assumeFalse;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.adhuc.cena.menu.acceptance.steps.serenity.ingredients.IngredientListServiceClientSteps;
import org.adhuc.cena.menu.acceptance.steps.serenity.ingredients.IngredientValue;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientId;
import org.adhuc.cena.menu.domain.model.recipe.RecipeId;
import org.adhuc.cena.menu.exception.ExceptionCode;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.core.Serenity;
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

    private static final String              RECIPES_INGREDIENTS_SESSION_KEY = "recipesIngredients";

    @Steps
    private IngredientListServiceClientSteps ingredientListServiceClient;

    @Step("Add ingredient to recipe")
    public void addIngredientToRecipe() {
        addIngredientToRecipe(recipe(), ingredientListServiceClient.ingredient(), false);
    }

    @Step("Add main ingredient to recipe")
    public Response addMainIngredientToRecipe() {
        return addIngredientToRecipe(recipe(), ingredientListServiceClient.ingredient(), true);
    }

    @Step("Add ingredient {1} to recipe {0} (main = {2})")
    private Response addIngredientToRecipe(RecipeValue recipe, IngredientValue ingredient, boolean main) {
        return addIngredientToRecipe(recipe, ingredient, main, rest());
    }

    @Step("Add ingredient {1} to recipe {0} as recipe author")
    public Response addIngredientToRecipeAsRecipeAuthor(RecipeValue recipe, IngredientValue ingredient) {
        return addIngredientToRecipeAsRecipeAuthor(recipe, ingredient, false);
    }

    @Step("Add ingredient {1} to recipe {0} as recipe author (main = {2})")
    public Response addIngredientToRecipeAsRecipeAuthor(RecipeValue recipe, IngredientValue ingredient, boolean main) {
        return addIngredientToRecipe(recipe, ingredient, main, restWithAuth(recipe.author()));
    }

    @Step("Assert ingredient has been successfully added to recipe")
    public void assertIngredientAdditionIsSuccessful() {
        assertNoContent();
    }

    @Step("Assert ingredient addition to recipe result in an ingredient not found error")
    public void assertIngredientNotFoundError() {
        assertException(NOT_FOUND, ExceptionCode.ENTITY_NOT_FOUND);
    }

    @Step("Assert ingredient addition to recipe result in an recipe not found error")
    public void assertRecipeNotFoundError() {
        assertException(NOT_FOUND, ExceptionCode.ENTITY_NOT_FOUND);
    }

    @Step("Assume ingredient can be found in recipe's ingredients list")
    public void assumeIngredientInRecipeIngredientsList() {
        assumeIngredientInRecipeIngredientsList(recipe(), ingredientListServiceClient.ingredient());
    }

    @Step("Assume ingredient cannot be found in recipe's ingredients list")
    public void assumeIngredientNotInRecipeIngredientsList() {
        assumeIngredientNotInRecipeIngredientsList(recipe(), ingredientListServiceClient.ingredient());
    }

    @Step("Assume ingredient {1} can be found in recipe {0}'s ingredients list")
    public void assumeIngredientInRecipeIngredientsList(RecipeValue recipe, IngredientValue ingredient) {
        if (!isIngredientInRecipeIngredientsList(recipe, ingredient)) {
            addIngredientToRecipeAsRecipeAuthor(recipe, ingredient);
        }
    }

    @Step("Assume ingredient {1} cannot be found in recipe {0}'s ingredients list")
    public void assumeIngredientNotInRecipeIngredientsList(RecipeValue recipe, IngredientValue ingredient) {
        // TODO remove ingredient from recipe's ingredient
        assumeFalse(isIngredientInRecipeIngredientsList(recipe, ingredient));
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

    @Step("Assert ingredient is a main ingredient in recipe")
    public void assertIngredientMainIngredientInRecipe() {
        RecipeIngredientValue ingredient = ingredientListServiceClient.ingredient();
        assertThat(ingredient.isMainIngredient()).isTrue();
    }

    @Step("Assert ingredient is not a main ingredient in recipe ")
    public void assertIngredientNotMainIngredientInRecipe() {
        RecipeIngredientValue ingredient = ingredientListServiceClient.ingredient();
        assertThat(ingredient.isMainIngredient()).isFalse();
    }

    @Step("Get ingredients for recipe {0}")
    public List<RecipeIngredientValue> getIngredientsFromRecipe(RecipeValue recipe) {
        return recipeIngredients(recipe.id())
                .orElseGet(() -> storeRecipeIngredients(recipe.id(), ingredientListServiceClient
                        .getIngredients(recipe.getIngredientsListUrl(), RecipeIngredientValue.class)));
    }

    private Response addIngredientToRecipe(RecipeValue recipe, IngredientValue ingredient, boolean main,
            RequestSpecification rest) {
        final String recipeIngredientsResourceUrl =
                recipe.exists() ? recipe.getIngredientsListUrl() : determineUnknownRecipeIngredientsResourceUrl();
        String ingredientId = ingredient.id() != null ? ingredient.id() : IngredientId.generate().toString();
        return rest.param("main", main).put(recipeIngredientsResourceUrl + "/" + ingredientId).andReturn();
    }

    private boolean isIngredientInRecipeIngredientsList(RecipeValue recipe, IngredientValue ingredient) {
        return getIngredientFromRecipeIngredientsList(recipe, ingredient).isPresent();
    }

    private Optional<RecipeIngredientValue> getIngredientFromRecipeIngredientsList(RecipeValue recipe,
            IngredientValue ingredient) {
        String recipeIngredientsResourceUrl = recipe.getIngredientsListUrl();
        return ingredientListServiceClient.getIngredientFromIngredientsList(recipeIngredientsResourceUrl, ingredient,
                RecipeIngredientValue.class);
    }

    private String determineUnknownRecipeIngredientsResourceUrl() {
        return getRecipesResourceUrl() + "/" + RecipeId.generate() + "/ingredients";
    }

    private Map<String, List<RecipeIngredientValue>> recipesIngredients() {
        Map<String, List<RecipeIngredientValue>> recipesIngredients =
                Serenity.sessionVariableCalled(RECIPES_INGREDIENTS_SESSION_KEY);
        return Optional.ofNullable(recipesIngredients).orElse(new HashMap<>());
    }

    private Optional<List<RecipeIngredientValue>> recipeIngredients(String recipeId) {
        return Optional.ofNullable(recipesIngredients().get(recipeId));
    }

    private List<RecipeIngredientValue> storeRecipeIngredients(String recipeId,
            List<RecipeIngredientValue> recipeIngredients) {
        Map<String, List<RecipeIngredientValue>> recipesIngredients = recipesIngredients();
        recipesIngredients.put(recipeId, recipeIngredients);
        Serenity.setSessionVariable(RECIPES_INGREDIENTS_SESSION_KEY).to(recipesIngredients);
        return recipeIngredients;
    }

}
