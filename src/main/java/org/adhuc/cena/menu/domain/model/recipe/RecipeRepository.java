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
import java.util.Optional;

import org.adhuc.cena.menu.domain.model.EntityNotFoundException;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientId;

/**
 * A {@link Recipe} repository.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public interface RecipeRepository {

    /**
     * Finds all the recipes.
     *
     * @return all the recipes.
     */
    List<Recipe> findAll();

    /**
     * Finds all the recipes that do not use one of the specified ingredients as a main ingredient.
     *
     * @param ingredientIds
     *            the banned main ingredient identities.
     *
     * @return the recipes that do not use one of the ingredients as main ingredient.
     */
    List<Recipe> findByMainIngredientsNotIn(Collection<IngredientId> ingredientIds);

    /**
     * Finds the recipe corresponding to the specified identity.
     *
     * @param recipeId
     *            the recipe identity.
     *
     * @return the recipe if existing, empty otherwise.
     */
    Optional<Recipe> findOne(RecipeId recipeId);

    /**
     * Finds the recipe corresponding to the specified identity.
     *
     * @param recipeId
     *            the recipe identity.
     *
     * @return the recipe if existing.
     *
     * @throws EntityNotFoundException
     *             if no recipe could be found for identity.
     */
    default Recipe findOneNotNull(RecipeId recipeId) {
        Optional<Recipe> recipe = findOne(recipeId);
        if (recipe.isPresent()) {
            return recipe.get();
        }
        throw new EntityNotFoundException(Recipe.class, recipeId);
    }

    /**
     * Saves the specified recipe.
     *
     * @param recipe
     *            the recipe to save.
     *
     * @return the saved recipe.
     */
    <I extends Recipe> I save(I recipe);

}
