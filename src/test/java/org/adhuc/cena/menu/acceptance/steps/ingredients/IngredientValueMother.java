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
package org.adhuc.cena.menu.acceptance.steps.ingredients;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.adhuc.cena.menu.acceptance.steps.serenity.ingredients.IngredientValue;
import org.adhuc.cena.menu.domain.model.ingredient.Ingredient;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientMother;

/**
 * An object mother to create testing elements related to {@link IngredientValue}s.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 *
 * @see https://www.martinfowler.com/bliki/ObjectMother.html
 */
public class IngredientValueMother {

    private static final Map<String, IngredientValue> INGREDIENTS = new HashMap<>();

    static {
        for (Ingredient ingredient : IngredientMother.allIngredients()) {
            addIngredient(ingredient);
        }
    }

    public static Optional<IngredientValue> getIngredient(String ingredientName) {
        return Optional.ofNullable(INGREDIENTS.get(ingredientName));
    }

    private static void addIngredient(Ingredient ingredient) {
        INGREDIENTS.put(ingredient.name(), new IngredientValue(ingredient.name()));
    }

}
