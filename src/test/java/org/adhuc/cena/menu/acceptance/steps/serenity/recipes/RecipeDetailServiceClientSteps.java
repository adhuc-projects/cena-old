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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;

/**
 * The recipe detail rest-service client steps definition.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@SuppressWarnings("serial")
public class RecipeDetailServiceClientSteps extends AbstractRecipeServiceClientSteps {

    private static final String RECIPES_SESSION_KEY = "recipes";

    @Step("Get recipe from {0}")
    public RecipeValue getRecipeFromUrl(String recipeDetailUrl) {
        return recipe(recipeDetailUrl).orElseGet(
                () -> storeRecipe(recipeDetailUrl, rest().get(recipeDetailUrl).then().extract().as(RecipeValue.class)));
    }

    @Step("Assert recipe {1} corresponds to expected {0}")
    public void assertRecipeInfoIsEqualToExpected(RecipeValue expected, RecipeValue actual) {
        assertThat(actual.name()).isEqualTo(expected.name());
        assertThat(actual.content()).isEqualTo(expected.content());
    }

    private Map<String, RecipeValue> recipes() {
        Map<String, RecipeValue> recipes = Serenity.sessionVariableCalled(RECIPES_SESSION_KEY);
        return Optional.ofNullable(recipes).orElse(new HashMap<>());
    }

    private Optional<RecipeValue> recipe(String recipeDetailUrl) {
        return Optional.ofNullable(recipes().get(recipeDetailUrl));
    }

    private RecipeValue storeRecipe(String recipeDetailUrl, RecipeValue recipe) {
        Map<String, RecipeValue> recipes = recipes();
        recipes.put(recipeDetailUrl, recipe);
        Serenity.setSessionVariable(RECIPES_SESSION_KEY).to(recipes);
        return recipe;
    }

}
