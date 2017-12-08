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

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.AUBERGINE_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.MOZZARELLA_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.POTATO_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_OLIVE_FETA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_OLIVE_FETA_SALAD_NAME;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.moussakaWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSaladWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberOliveFetaSaladWithIngredients;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.adhuc.cena.menu.domain.model.EntityNotFoundException;
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

    @Test
    @DisplayName("throws EntityNotFoundException when finding unknown recipe (not null required)")
    public void findOneNotNullUnknown() {
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> repository.findOneNotNull(TOMATO_CUCUMBER_MOZZA_SALAD_ID));
        assertThat(exception.entityType()).isEqualTo(Recipe.class);
        assertThat(exception.identity()).isEqualTo(TOMATO_CUCUMBER_MOZZA_SALAD_ID.toString());
    }

    @Nested
    @DisplayName("with no recipe")
    class WithNoRecipe {

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
            repository.save(tomatoCucumberMozzaSaladWithIngredients());
        }

        @Test
        @DisplayName("returns a recipes list containing saved recipe")
        public void findAllAfterSaveContainsSavedRecipe() {
            assertThat(repository.findAll()).usingFieldByFieldElementComparator()
                    .containsExactly(tomatoCucumberMozzaSaladWithIngredients());
        }

        @Test
        @DisplayName("returns an empty recipe when finding by unknown id")
        public void findOneNotExisting() {
            assertThat(repository.findOne(TOMATO_CUCUMBER_OLIVE_FETA_SALAD_ID)).isNotPresent();
        }

        @Test
        @DisplayName("returns tomato, cucumber and mozzarella salad when finding by id")
        public void findOneExisting() {
            assertThat(repository.findOne(TOMATO_CUCUMBER_MOZZA_SALAD_ID)).isPresent()
                    .usingFieldByFieldValueComparator().contains(tomatoCucumberMozzaSaladWithIngredients());
        }

        @Test
        @DisplayName("returns a recipes list containing saved recipe with new value")
        public void saveExistingRecipeOverwritePreviousValue() {
            repository.save(tomatoCucumberMozzaSalad().name(TOMATO_CUCUMBER_OLIVE_FETA_SALAD_NAME));
            assertThat(repository.findAll()).usingFieldByFieldElementComparator()
                    .containsExactly(new Recipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID, TOMATO_CUCUMBER_OLIVE_FETA_SALAD_NAME,
                            TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT, TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR));
        }

        @Test
        @DisplayName("finding recipes not using potato main ingredient returns tomato, cucumber and mozzarella salad")
        public void findByMainIngredientsNotInPotato() {
            assertThat(repository.findByMainIngredientsNotIn(Collections.singleton(POTATO_ID)))
                    .usingFieldByFieldElementComparator().containsExactly(tomatoCucumberMozzaSaladWithIngredients());
        }

        @Test
        @DisplayName("finding recipes not using mozza main ingredient returns tomato, cucumber and mozzarella salad")
        public void findByMainIngredientsNotInMozza() {
            assertThat(repository.findByMainIngredientsNotIn(Collections.singleton(MOZZARELLA_ID)))
                    .usingFieldByFieldElementComparator().containsExactly(tomatoCucumberMozzaSaladWithIngredients());
        }

        @Test
        @DisplayName("finding recipes not using tomato main ingredient returns empty list")
        public void findByMainIngredientsNotInTomato() {
            assertThat(repository.findByMainIngredientsNotIn(Collections.singleton(TOMATO_ID))).isEmpty();
        }

        @Nested
        @DisplayName("and tomato, cucumber, olive and feta salad")
        class AndTomatoCucumberOliveFetaSalad {

            @BeforeEach
            public void setUp() {
                repository.save(tomatoCucumberOliveFetaSaladWithIngredients());
            }

            @Test
            @DisplayName("returns a recipes list containing all saved recipes")
            public void findAllAfterMultipleSaveContainsSavedRecipes() {
                assertThat(repository.findAll()).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(
                        tomatoCucumberMozzaSaladWithIngredients(), tomatoCucumberOliveFetaSaladWithIngredients());
            }

        }

        @Nested
        @DisplayName("and moussaka")
        class AndMoussaka {

            @BeforeEach
            public void setUp() {
                repository.save(moussakaWithIngredients());
            }

            @Test
            @DisplayName("finding recipes not using potato main ingredient returns tomato, cucumber and mozzarella salad and moussaka")
            public void findByMainIngredientsNotInPotato() {
                assertThat(repository.findByMainIngredientsNotIn(Collections.singleton(POTATO_ID)))
                        .usingFieldByFieldElementComparator().containsExactlyInAnyOrder(
                                tomatoCucumberMozzaSaladWithIngredients(), moussakaWithIngredients());
            }

            @Test
            @DisplayName("finding recipes not using mozza main ingredient returns tomato, cucumber and mozzarella salad and moussaka")
            public void findByMainIngredientsNotInMozza() {
                assertThat(repository.findByMainIngredientsNotIn(Collections.singleton(MOZZARELLA_ID)))
                        .usingFieldByFieldElementComparator().containsExactlyInAnyOrder(
                                tomatoCucumberMozzaSaladWithIngredients(), moussakaWithIngredients());
            }

            @Test
            @DisplayName("finding recipes not using tomato main ingredient returns moussaka")
            public void findByMainIngredientsNotInTomato() {
                assertThat(repository.findByMainIngredientsNotIn(Collections.singleton(TOMATO_ID)))
                        .containsExactly(moussakaWithIngredients());
            }

            @Test
            @DisplayName("finding recipes not using tomato or aubergine main ingredient returns empty list")
            public void findByMainIngredientsNotInTomatoOrAubergine() {
                assertThat(repository.findByMainIngredientsNotIn(Arrays.asList(TOMATO_ID, AUBERGINE_ID))).isEmpty();
            }

        }

    }

}
