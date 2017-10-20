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
package org.adhuc.cena.menu.domain.model.recipe;

import static org.assertj.core.api.Assertions.assertThat;

import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_ID;

import org.junit.Test;

/**
 * The {@link RecipeId} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public class RecipeIdTest {

    @Test(expected = NullPointerException.class)
    public void createRecipeIdFromNullString() {
        new RecipeId(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createRecipeIdFromInvalidString() {
        new RecipeId("invalid");
    }

    @Test
    public void createRecipeIdFromString() {
        final RecipeId createdId = new RecipeId(TOMATO_CUCUMBER_MOZZA_SALAD_ID.toString());
        assertThat(createdId).isEqualTo(TOMATO_CUCUMBER_MOZZA_SALAD_ID);
    }

}
