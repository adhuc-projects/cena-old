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
package org.adhuc.cena.menu.application;

import java.util.List;
import java.util.Optional;

import org.adhuc.cena.menu.domain.model.ingredient.CreateIngredient;
import org.adhuc.cena.menu.domain.model.ingredient.Ingredient;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientId;

/**
 * An application service for ingredients.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public interface IngredientAppService {

    /**
     * Gets the ingredients.
     *
     * @return the ingredients (not modifiable).
     */
    List<Ingredient> getIngredients();

    /**
     * Gets the ingredient corresponding to the specified identity, if any.
     *
     * @param ingredientId
     *            the ingredient identity.
     *
     * @return the ingredient, or empty.
     */
    Optional<Ingredient> getIngredient(IngredientId ingredientId);

    /**
     * Creates an ingredient.
     *
     * @param command
     *            the ingredient creation command.
     */
    void createIngredient(CreateIngredient command);

}
