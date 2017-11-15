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
package org.adhuc.cena.menu.port.adapter.rest.recipes;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.endsWith;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.adhuc.cena.menu.port.adapter.rest.ingredient.IngredientJsonAssertion.assertJsonContainsIngredient;

import org.springframework.test.web.servlet.ResultActions;

import org.adhuc.cena.menu.domain.model.recipe.Recipe;
import org.adhuc.cena.menu.domain.model.recipe.RecipeId;
import org.adhuc.cena.menu.domain.model.recipe.ingredient.RecipeIngredient;

/**
 * Provides assertions on recipes JSON representation.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public class RecipeJsonAssertion {

    private static final String RECIPES_API_URL = "/api/recipes";

    public static void assertJsonContainsRecipe(ResultActions resultActions, String jsonPath, Recipe recipe)
            throws Exception {
        resultActions.andExpect(jsonPath(jsonPath + ".id").exists())
                .andExpect(jsonPath(jsonPath + ".id", equalTo(recipe.id().toString())))
                .andExpect(jsonPath(jsonPath + ".name").exists())
                .andExpect(jsonPath(jsonPath + ".name", equalTo(recipe.name())))
                .andExpect(jsonPath(jsonPath + ".content").exists())
                .andExpect(jsonPath(jsonPath + ".content", equalTo(recipe.content())))
                .andExpect(jsonPath(jsonPath + ".author").exists())
                .andExpect(jsonPath(jsonPath + ".author", equalTo(recipe.author().toString())))
                .andExpect(jsonPath(jsonPath + ".ingredients").doesNotExist())
                .andExpect(jsonPath(jsonPath + "._links.self.href").exists())
                .andExpect(jsonPath(jsonPath + "._links.self.href", endsWith(buildRecipeSelfLink(recipe.id()))));
    }

    public static void assertJsonContainsRecipeIngredient(ResultActions resultActions, String jsonPath,
            RecipeIngredient recipeIngredient) throws Exception {
        assertJsonContainsIngredient(resultActions, jsonPath, recipeIngredient.ingredient(), false);
        resultActions.andExpect(jsonPath(jsonPath + ".mainIngredient").exists())
                .andExpect(jsonPath(jsonPath + ".mainIngredient", equalTo(recipeIngredient.id().isMainIngredient())));
    }

    public static String buildRecipeSelfLink(RecipeId recipeId) {
        return RECIPES_API_URL + "/" + recipeId.id();
    }

}
