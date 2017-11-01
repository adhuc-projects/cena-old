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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.adhuc.cena.menu.acceptance.steps.serenity.ingredients.IngredientValue;

import cucumber.api.Transformer;

/**
 * A cucumber {@link Transformer} implementation for {@link IngredientValue}s.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public class IngredientValueTransformer extends Transformer<IngredientValue> {

    @Override
    public IngredientValue transform(String value) {
        Optional<IngredientValue> ingredient = IngredientValueMother.getIngredient(value);
        assertThat(ingredient).as("Cannot find ingredient corresponding to \"" + value + "\"").isPresent();
        return ingredient.get();
    }

}
