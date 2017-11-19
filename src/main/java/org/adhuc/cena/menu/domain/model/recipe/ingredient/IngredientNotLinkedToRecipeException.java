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
package org.adhuc.cena.menu.domain.model.recipe.ingredient;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.adhuc.cena.menu.domain.model.ingredient.IngredientId;
import org.adhuc.cena.menu.domain.model.recipe.RecipeId;
import org.adhuc.cena.menu.exception.CenaException;
import org.adhuc.cena.menu.exception.ExceptionCode;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * An exception occurring while requesting an ingredient that is not linked to a recipe.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Value
@EqualsAndHashCode(callSuper = false)
@Accessors(fluent = true)
@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class IngredientNotLinkedToRecipeException extends CenaException {

    private static final ExceptionCode EXCEPTION_CODE = ExceptionCode.INGREDIENT_NOT_LINKED_TO_RECIPE;

    private final RecipeId             recipeId;
    private final IngredientId         ingredientId;

    /**
     * Constructs a new {@code IngredientNotLinkedToRecipeException} with the specified recipe and ingredient
     * identities.
     *
     * @param recipeId
     *            the recipe identity.
     *
     * @param ingredientId
     *            the ingredient identity.
     */
    public IngredientNotLinkedToRecipeException(RecipeId recipeId, IngredientId ingredientId) {
        super("Cannot find ingredient " + ingredientId + " linked to recipe " + recipeId, EXCEPTION_CODE);
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
    }

}
