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
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_NAME;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.createTomato;

import org.junit.Test;

/**
 * The {@link CreateIngredient} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public class CreateIngredientTest {

    @Test(expected = IllegalArgumentException.class)
    public void createIngredientWithoutId() {
        new CreateIngredient(null, TOMATO_NAME);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createIngredientWithoutName() {
        new CreateIngredient(TOMATO_ID, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createIngredientWithEmptyName() {
        new CreateIngredient(TOMATO_ID, "");
    }

    @Test
    public void createIngredientWithValidValues() {
        CreateIngredient command = createTomato();
        assertThat(command.ingredientId()).isEqualTo(TOMATO_ID);
        assertThat(command.ingredientName()).isEqualTo(TOMATO_NAME);
    }

}
