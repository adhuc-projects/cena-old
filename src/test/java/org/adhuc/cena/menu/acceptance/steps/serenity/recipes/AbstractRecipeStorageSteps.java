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

import org.adhuc.cena.menu.acceptance.steps.serenity.AbstractServiceClientSteps;

import net.serenitybdd.core.Serenity;

/**
 * An abstract recipe storage steps definition, providing convenient methods to store recipe information between steps.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@SuppressWarnings("serial")
public abstract class AbstractRecipeStorageSteps extends AbstractServiceClientSteps {

    private static final String RECIPE_SESSION_KEY = "recipe";

    public final RecipeValue storeRecipe(final RecipeValue recipe) {
        Serenity.setSessionVariable(RECIPE_SESSION_KEY).to(recipe);
        return recipe;
    }

    protected final RecipeValue storeRecipeIfEmpty(final RecipeValue recipe) {
        Optional<RecipeValue> optional = optionalRecipe();
        if (!optional.isPresent()) {
            return storeRecipe(recipe);
        }
        return optional.get();
    }

    public final RecipeValue recipe() {
        Optional<RecipeValue> recipe = optionalRecipe();
        assertThat(recipe).isPresent();
        return recipe.get();
    }

    protected final Optional<RecipeValue> optionalRecipe() {
        return Optional.ofNullable(Serenity.sessionVariableCalled(RECIPE_SESSION_KEY));
    }

}
