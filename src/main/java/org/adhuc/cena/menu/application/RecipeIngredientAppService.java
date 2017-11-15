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

import org.adhuc.cena.menu.domain.model.recipe.RecipeId;
import org.adhuc.cena.menu.domain.model.recipe.ingredient.AddIngredientToRecipe;
import org.adhuc.cena.menu.domain.model.recipe.ingredient.RecipeIngredient;

/**
 * An application service for link between recipes and ingredients.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public interface RecipeIngredientAppService {

    /**
     * Gets the list of ingredients linked to a recipe.
     *
     * @param recipeId
     *            the recipe identity.
     *
     * @return the ingredients linked to recipe (not modifiable).
     */
    List<RecipeIngredient> getRecipeIngredients(RecipeId recipeId);

    /**
     * Adds an ingredient to a recipe.
     *
     * @param command
     *            the ingredient addition to recipe command.
     */
    void addIngredientToRecipe(AddIngredientToRecipe command);

}
