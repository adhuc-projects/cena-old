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
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.springframework.http.HttpStatus.OK;

import java.util.Optional;

import io.restassured.path.json.JsonPath;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

/**
 * The recipes list rest-service client steps definition.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@SuppressWarnings("serial")
public class RecipesListServiceClientSteps extends AbstractRecipeServiceClientSteps {

    @Steps
    private RecipeCreationServiceClientSteps recipeCreationServiceClient;

    @Step("Assume recipe {0} is in recipes list")
    public RecipeValue assumeRecipeInRecipesList(final RecipeValue recipe) {
        storeRecipeIfEmpty(recipe);
        Optional<RecipeValue> foundRecipe = getRecipeFromRecipesList(recipe);
        foundRecipe.ifPresent(r -> storeRecipe(r));
        return foundRecipe.orElseGet(() -> createRecipeAndAssumeRecipeInRecipesList(recipe));
    }

    @Step("Assume recipe {0} is in recipes list after creating it")
    public RecipeValue createRecipeAndAssumeRecipeInRecipesList(final RecipeValue recipe) {
        recipeCreationServiceClient.createRecipe(recipe);
        Optional<RecipeValue> foundRecipe = getRecipeFromRecipesList(recipe);
        assumeTrue(foundRecipe.isPresent(), "Could not find the recipe in recipes list");
        return storeRecipe(foundRecipe.get());
    }

    @Step("Assume recipe {0} is not in recipes list")
    public void assumeRecipeNotInRecipesList(final RecipeValue recipe) {
        storeRecipeIfEmpty(recipe);
        assumeFalse(isRecipeInRecipesList(recipe));
    }

    @Step("Assume there are at least {0} recipes in list")
    public void assumeAtLeastExistingRecipes(int recipesCount) {
        // TODO assume that there are enough recipes
    }

    @Step("Assert recipe is in recipes list")
    public RecipeValue assertRecipeInRecipesList() {
        return assertRecipeInRecipesList(recipe());
    }

    @Step("Assert recipe is not in recipes list")
    public void assertRecipeNotInRecipesList() {
        assertRecipeNotInRecipesList(recipe());
    }

    @Step("Assert recipe {0} is in recipes list")
    public RecipeValue assertRecipeInRecipesList(final RecipeValue recipe) {
        storeRecipeIfEmpty(recipe);
        Optional<RecipeValue> foundRecipe = getRecipeFromRecipesList(recipe);
        assertThat(foundRecipe).isPresent();
        return foundRecipe.get();
    }

    @Step("Assert recipe {0} is not in recipes list")
    public void assertRecipeNotInRecipesList(final RecipeValue recipe) {
        assertThat(isRecipeInRecipesList(recipe)).isFalse();
    }

    private boolean isRecipeInRecipesList(final RecipeValue recipe) {
        return getRecipeFromRecipesList(recipe).isPresent();
    }

    private Optional<RecipeValue> getRecipeFromRecipesList(final RecipeValue recipe) {
        final String recipesResourceUrl = getRecipesResourceUrl();
        final String recipeName = recipe.name();
        final JsonPath jsonPath = rest().get(recipesResourceUrl).then().statusCode(OK.value()).extract().jsonPath();
        return Optional.ofNullable(jsonPath.param("name", recipeName)
                .getObject("_embedded.data.find { recipe->recipe.name == name }", RecipeValue.class));
    }

}
