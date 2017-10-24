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
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_NAME;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSalad;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The {@link Recipe} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@DisplayName("Recipe")
public class RecipeTest {

    @Test
    @DisplayName("cannot be created without id")
    public void recipeWithoutId() {
        assertThrows(IllegalArgumentException.class,
                () -> new Recipe(null, TOMATO_CUCUMBER_MOZZA_SALAD_NAME, TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT));
    }

    @Test
    @DisplayName("cannot be created without name")
    public void recipeWithoutName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Recipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID, null, TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT));
    }

    @Test
    @DisplayName("cannot be created with empty name")
    public void recipeWithEmptyName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Recipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID, "", TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT));
    }

    @Test
    @DisplayName("cannot be created without content")
    public void recipeWithoutContent() {
        assertThrows(IllegalArgumentException.class,
                () -> new Recipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID, TOMATO_CUCUMBER_MOZZA_SALAD_NAME, null));
    }

    @Test
    @DisplayName("cannot be created with empty content")
    public void recipeWithEmptyContent() {
        assertThrows(IllegalArgumentException.class,
                () -> new Recipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID, TOMATO_CUCUMBER_MOZZA_SALAD_NAME, ""));
    }

    @Test
    @DisplayName("contains id, name and content used during creation")
    public void recipeWithValidValues() {
        Recipe recipe = tomatoCucumberMozzaSalad();
        assertThat(recipe.id()).isEqualTo(TOMATO_CUCUMBER_MOZZA_SALAD_ID);
        assertThat(recipe.name()).isEqualTo(TOMATO_CUCUMBER_MOZZA_SALAD_NAME);
        assertThat(recipe.content()).isEqualTo(TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT);
    }

}