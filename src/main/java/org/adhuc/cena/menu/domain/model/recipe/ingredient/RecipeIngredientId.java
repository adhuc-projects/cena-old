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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.adhuc.cena.menu.domain.model.ingredient.IngredientId;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * A relation between a recipe and an ingredient, that indicates whether the ingredient is a main ingredient of the
 * recipe.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Value
@Accessors(fluent = true)
public class RecipeIngredientId {

    @NonNull
    @JsonIgnore
    private final IngredientId ingredientId;
    @JsonProperty("mainIngredient")
    private final boolean      isMainIngredient;

}
