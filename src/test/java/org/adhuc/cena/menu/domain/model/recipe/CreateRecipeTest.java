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

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_NAME;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.createTomatoCucumberMozzaSalad;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The {@link CreateRecipe} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("domain")
@DisplayName("Create recipe command")
public class CreateRecipeTest {

    @Test
    @DisplayName("cannot be created without id")
    public void createRecipeWithoutId() {
        assertThrows(IllegalArgumentException.class, () -> new CreateRecipe(null, TOMATO_CUCUMBER_MOZZA_SALAD_NAME,
                TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT, TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR));
    }

    @Test
    @DisplayName("cannot be created without name")
    public void createRecipeWithoutName() {
        assertThrows(IllegalArgumentException.class, () -> new CreateRecipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID, null,
                TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT, TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR));
    }

    @Test
    @DisplayName("cannot be created with empty name")
    public void createRecipeWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> new CreateRecipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID, "",
                TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT, TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR));
    }

    @Test
    @DisplayName("cannot be created without content")
    public void createRecipeWithoutContent() {
        assertThrows(IllegalArgumentException.class, () -> new CreateRecipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID,
                TOMATO_CUCUMBER_MOZZA_SALAD_NAME, null, TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR));
    }

    @Test
    @DisplayName("cannot be created with empty content")
    public void createRecipeWithEmptyContent() {
        assertThrows(IllegalArgumentException.class, () -> new CreateRecipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID,
                TOMATO_CUCUMBER_MOZZA_SALAD_NAME, "", TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR));
    }

    @Test
    @DisplayName("cannot be created without author")
    public void createRecipeWithoutAuthor() {
        assertThrows(IllegalArgumentException.class, () -> new CreateRecipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID,
                TOMATO_CUCUMBER_MOZZA_SALAD_NAME, TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT, null));
    }

    @Test
    @DisplayName("is composed of the id, name and content used during construction")
    public void createRecipeWithValidValues() {
        CreateRecipe command = createTomatoCucumberMozzaSalad();
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(command.recipeId()).isEqualTo(TOMATO_CUCUMBER_MOZZA_SALAD_ID);
            softly.assertThat(command.recipeName()).isEqualTo(TOMATO_CUCUMBER_MOZZA_SALAD_NAME);
            softly.assertThat(command.recipeContent()).isEqualTo(TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT);
            softly.assertThat(command.recipeAuthor()).isEqualTo(TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR);
        });
    }

}
