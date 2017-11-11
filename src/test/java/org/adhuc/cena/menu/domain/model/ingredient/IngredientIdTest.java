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
package org.adhuc.cena.menu.domain.model.ingredient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_ID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.adhuc.cena.menu.domain.model.EntityNotFoundException;

/**
 * The {@link IngredientId} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("domain")
@DisplayName("Ingredient identity")
public class IngredientIdTest {

    @Test
    @DisplayName("cannot be created from null value")
    public void createIngredientIdFromNullString() {
        assertThrows(EntityNotFoundException.class, () -> new IngredientId(null));
    }

    @Test
    @DisplayName("cannot be created from invalid value")
    public void createIngredientIdFromInvalidString() {
        assertThrows(EntityNotFoundException.class, () -> new IngredientId("invalid"));
    }

    @Test
    @DisplayName("contains identity value used during construction")
    public void createIngredientIdFromString() {
        final IngredientId createdId = new IngredientId(TOMATO_ID.toString());
        assertThat(createdId).isEqualTo(TOMATO_ID);
    }

}
