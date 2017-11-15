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
package org.adhuc.cena.menu.acceptance.steps.serenity.ingredients;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.adhuc.cena.menu.acceptance.steps.serenity.AbstractServiceClientSteps;

import net.serenitybdd.core.Serenity;

/**
 * An abstract ingredient storage steps definition, providing convenient methods to store ingredient information between
 * steps.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@SuppressWarnings("serial")
public abstract class AbstractIngredientStorageSteps extends AbstractServiceClientSteps {

    private static final String INGREDIENT_SESSION_KEY = "ingredient";

    public final <I extends IngredientValue> I storeIngredient(I ingredient) {
        Serenity.setSessionVariable(INGREDIENT_SESSION_KEY).to(ingredient);
        return ingredient;
    }

    protected final <I extends IngredientValue> I storeIngredientIfEmpty(I ingredient) {
        Optional<I> optional = optionalIngredient();
        if (!optional.isPresent()) {
            return storeIngredient(ingredient);
        }
        return optional.get();
    }

    public final <I extends IngredientValue> I ingredient() {
        Optional<I> recipe = optionalIngredient();
        assertThat(recipe).isPresent();
        return recipe.get();
    }

    protected final <I extends IngredientValue> Optional<I> optionalIngredient() {
        return Optional.ofNullable(Serenity.sessionVariableCalled(INGREDIENT_SESSION_KEY));
    }

}
