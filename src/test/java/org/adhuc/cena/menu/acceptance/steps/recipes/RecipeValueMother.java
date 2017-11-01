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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.adhuc.cena.menu.acceptance.steps.serenity.recipes.RecipeValue;
import org.adhuc.cena.menu.domain.model.recipe.Recipe;
import org.adhuc.cena.menu.domain.model.recipe.RecipeMother;

/**
 * An object mother to create testing elements related to {@link RecipeValue}s.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 *
 * @see https://www.martinfowler.com/bliki/ObjectMother.html
 */
public class RecipeValueMother {

    private static final Map<String, RecipeValue> RECIPES = new HashMap<>();

    static {
        for (Recipe recipe : RecipeMother.allRecipes()) {
            addRecipe(recipe);
        }
    }

    public static Optional<RecipeValue> getRecipe(String recipeName) {
        return Optional.ofNullable(RECIPES.get(recipeName));
    }

    private static void addRecipe(Recipe recipe) {
        RECIPES.put(recipe.name(), new RecipeValue(recipe.name(), recipe.content()));
    }

}
