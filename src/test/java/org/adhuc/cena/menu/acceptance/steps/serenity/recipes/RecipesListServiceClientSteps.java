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
import static org.junit.Assume.assumeTrue;
import static org.springframework.http.HttpStatus.OK;

import java.util.Optional;

import io.restassured.path.json.JsonPath;
import net.thucydides.core.annotations.Step;

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

    @Step("Assert recipe is in recipes list")
    public RecipeValue assertRecipeInRecipesList() {
        return assertRecipeInRecipesList(recipe());
    }

    @Step("Assert recipe is not in recipes list")
    public void assertRecipeNotInRecipesList() {
        assertRecipeNotInRecipesList(recipe());
    }

    @Step("Assume recipe {0} is in recipes list")
    public RecipeValue assumeRecipeInRecipesList(final RecipeValue recipe) {
        withRecipeIfEmpty(recipe);
        Optional<RecipeValue> foundRecipe = getRecipeFromRecipesList(recipe);
        assumeTrue("Could not find the recipe in recipes list", foundRecipe.isPresent());
        return foundRecipe.get();
    }

    @Step("Assert recipe {0} is in recipes list")
    public RecipeValue assertRecipeInRecipesList(final RecipeValue recipe) {
        withRecipeIfEmpty(recipe);
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