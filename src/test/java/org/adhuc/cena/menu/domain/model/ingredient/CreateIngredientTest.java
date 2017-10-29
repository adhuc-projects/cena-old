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
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_NAME;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.createTomato;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The {@link CreateIngredient} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("domain")
@DisplayName("Create ingredient command")
public class CreateIngredientTest {

    @Test
    @DisplayName("cannot be created without id")
    public void createIngredientWithoutId() {
        assertThrows(IllegalArgumentException.class, () -> new CreateIngredient(null, TOMATO_NAME));
    }

    @Test
    @DisplayName("cannot be created without name")
    public void createIngredientWithoutName() {
        assertThrows(IllegalArgumentException.class, () -> new CreateIngredient(TOMATO_ID, null));
    }

    @Test
    @DisplayName("cannot be created with empty name")
    public void createIngredientWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> new CreateIngredient(TOMATO_ID, ""));
    }

    @Test
    @DisplayName("is composed of the id and name used during construction")
    public void createIngredientWithValidValues() {
        CreateIngredient command = createTomato();
        assertThat(command.ingredientId()).isEqualTo(TOMATO_ID);
        assertThat(command.ingredientName()).isEqualTo(TOMATO_NAME);
    }

}
