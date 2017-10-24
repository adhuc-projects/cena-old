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

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.CUCUMBER_NAME;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_NAME;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.tomato;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * The {@link Ingredient} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@DisplayName("Ingredient")
public class IngredientTest {

    @Test
    @DisplayName("cannot be created without id")
    public void ingredientWithoutId() {
        assertThrows(IllegalArgumentException.class, () -> new Ingredient(null, TOMATO_NAME));
    }

    @Test
    @DisplayName("cannot be created without name")
    public void ingredientWithoutName() {
        assertThrows(IllegalArgumentException.class, () -> new Ingredient(TOMATO_ID, null));
    }

    @Test
    @DisplayName("cannot be created with empty name")
    public void ingredientWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> new Ingredient(TOMATO_ID, ""));
    }

    @Test
    @DisplayName("contains id and name used during creation")
    public void ingredientWithValidValues() {
        Ingredient ingredient = tomato();
        assertThat(ingredient.id()).isEqualTo(TOMATO_ID);
        assertThat(ingredient.name()).isEqualTo(TOMATO_NAME);
    }

    @Nested
    @DisplayName("tomato")
    class Tomato {

        private Ingredient ingredient = tomato();

        @Test
        @DisplayName("cannot have its name changed with null value")
        public void changeNameWithNullValue() {
            assertThrows(IllegalArgumentException.class, () -> ingredient.name(null));
        }

        @Test
        @DisplayName("cannot have its name changed with empty value")
        public void changeNameWithEmptyValue() {
            assertThrows(IllegalArgumentException.class, () -> ingredient.name(""));
        }

        @Test
        @DisplayName("has a new name after changing it")
        public void changeNameWithValidValue() {
            ingredient.name(CUCUMBER_NAME);
            assertThat(ingredient.name()).isEqualTo(CUCUMBER_NAME);
        }

    }

}
