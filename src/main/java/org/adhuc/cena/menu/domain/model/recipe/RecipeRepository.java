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
package org.adhuc.cena.menu.domain.model.recipe;

import java.util.Collection;
import java.util.List;

import org.adhuc.cena.menu.domain.model.WritableRepository;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientId;

/**
 * A {@link Recipe} repository.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public interface RecipeRepository extends WritableRepository<Recipe, RecipeId> {

    /**
     * Finds all the recipes that do not use one of the specified ingredients as a main ingredient.
     *
     * @param ingredientIds
     *            the banned main ingredient identities.
     *
     * @return the recipes that do not use one of the ingredients as main ingredient.
     */
    List<Recipe> findByMainIngredientsNotIn(Collection<IngredientId> ingredientIds);

}
