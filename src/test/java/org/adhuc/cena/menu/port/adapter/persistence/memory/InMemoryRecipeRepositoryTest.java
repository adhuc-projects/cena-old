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
package org.adhuc.cena.menu.port.adapter.persistence.memory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_OLIVE_FETA_SALAD_NAME;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberOliveFetaSalad;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.adhuc.cena.menu.domain.model.recipe.Recipe;

/**
 * The {@link InMemoryRecipeRepository} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("inMemoryRepository")
@DisplayName("In-memory recipe repository")
public class InMemoryRecipeRepositoryTest {

    private InMemoryRecipeRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new InMemoryRecipeRepository();
    }

    @Test
    @DisplayName("throws IllegalArgumentException when saving null recipe")
    public void saveNullRecipe() {
        assertThrows(IllegalArgumentException.class, () -> repository.save(null));
    }

    @Nested
    @DisplayName("with no recipe")
    class WithNoIngredient {

        @Test
        @DisplayName("returns empty list")
        public void findAllEmpty() {
            assertThat(repository.findAll()).isEmpty();
        }

    }

    @Nested
    @DisplayName("with tomato, cucumber and mozzarella salad")
    class WithTomatoCucumberMozzarellaSalad {

        @BeforeEach
        public void setUp() {
            repository.save(tomatoCucumberMozzaSalad());
        }

        @Test
        public void findAllAfterSaveContainsSavedRecipe() {
            assertThat(repository.findAll()).containsExactly(tomatoCucumberMozzaSalad());
        }

        @Test
        public void saveExistingRecipeOverwritePreviousValue() {
            repository.save(tomatoCucumberMozzaSalad().name(TOMATO_CUCUMBER_OLIVE_FETA_SALAD_NAME));
            assertThat(repository.findAll()).containsExactly(new Recipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID,
                    TOMATO_CUCUMBER_OLIVE_FETA_SALAD_NAME, TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT));
        }

        @Nested
        @DisplayName("and tomato, cucumber, olive and feta salad")
        class AndTomatoCucumberOliveFetaSalad {

            @BeforeEach
            public void setUp() {
                repository.save(tomatoCucumberOliveFetaSalad());
            }

            @Test
            public void findAllAfterMultipleSaveContainsSavedRecipes() {
                assertThat(repository.findAll()).containsExactlyInAnyOrder(tomatoCucumberMozzaSalad(),
                        tomatoCucumberOliveFetaSalad());
            }

        }

    }

}
