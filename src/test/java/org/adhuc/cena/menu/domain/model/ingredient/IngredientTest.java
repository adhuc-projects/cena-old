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

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.CUCUMBER_NAME;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_NAME;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.tomato;

import org.junit.Test;

/**
 * The {@link Ingredient} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public class IngredientTest {

    @Test(expected = IllegalArgumentException.class)
    public void ingredientWithoutId() {
        new Ingredient(null, TOMATO_NAME);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ingredientWithoutName() {
        new Ingredient(TOMATO_ID, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ingredientWithEmptyName() {
        new Ingredient(TOMATO_ID, "");
    }

    @Test
    public void ingredientWithValidValues() {
        Ingredient ingredient = tomato();
        assertThat(ingredient.id()).isEqualTo(TOMATO_ID);
        assertThat(ingredient.name()).isEqualTo(TOMATO_NAME);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeNameWithNullValue() {
        tomato().name(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeNameWithEmptyValue() {
        tomato().name("");
    }

    @Test
    public void changeNameWithValidValue() {
        Ingredient ingredient = tomato();
        ingredient.name(CUCUMBER_NAME);
        assertThat(ingredient.name()).isEqualTo(CUCUMBER_NAME);
    }

}
