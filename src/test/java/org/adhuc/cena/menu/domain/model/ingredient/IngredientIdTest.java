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

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_ID;

import java.util.UUID;

import org.junit.Test;

/**
 * The {@link IngredientId} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public class IngredientIdTest {

    @Test(expected = NullPointerException.class)
    public void createIngredientIdFromNullString() {
        new IngredientId(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createIngredientIdFromInvalidString() {
        new IngredientId("invalid");
    }

    @Test
    public void createIngredientIdFromString() {
        final IngredientId createdId = new IngredientId(TOMATO_ID.toString());
        assertThat(createdId).isEqualTo(TOMATO_ID);
    }

    @Test
    public void isWellFormedNullIsFalse() {
        assertThat(IngredientId.isWellFormed(null)).isEqualTo(false);
    }

    @Test
    public void isWellFormedEmptyIsFalse() {
        assertThat(IngredientId.isWellFormed("")).isEqualTo(false);
    }

    @Test
    public void isWellFormedInvalidIsFalse() {
        assertThat(IngredientId.isWellFormed("invalid")).isEqualTo(false);
    }

    @Test
    public void isWellFormedUuidIsTrue() {
        assertThat(IngredientId.isWellFormed(UUID.randomUUID().toString())).isEqualTo(true);
    }

}
