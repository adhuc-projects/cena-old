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

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.CUCUMBER_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.CUCUMBER_NAME;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_NAME;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.cucumber;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.tomato;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.adhuc.cena.menu.domain.model.EntityNotFoundException;
import org.adhuc.cena.menu.domain.model.ingredient.Ingredient;

/**
 * The {@link InMemoryIngredientRepository} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("inMemoryRepository")
@DisplayName("In-memory ingredient repository")
public class InMemoryIngredientRepositoryTest {

    private InMemoryIngredientRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new InMemoryIngredientRepository();
    }

    @Test
    @DisplayName("throws IllegalArgumentException when saving null ingredient")
    public void saveNullIngredient() {
        assertThrows(IllegalArgumentException.class, () -> repository.save(null));
    }

    @Test
    @DisplayName("throws EntityNotFoundException when finding unknown ingredient (not null required)")
    public void findOneNotNullUnknown() {
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> repository.findOneNotNull(TOMATO_ID));
        assertThat(exception.entityType()).isEqualTo(Ingredient.class);
        assertThat(exception.identity()).isEqualTo(TOMATO_ID);
    }

    @Nested
    @DisplayName("with no ingredient")
    class WithNoIngredient {

        @Test
        @DisplayName("returns empty list")
        public void findAllEmpty() {
            assertThat(repository.findAll()).isEmpty();
        }

    }

    @Nested
    @DisplayName("with tomato")
    class WithTomato {

        @BeforeEach
        public void setUp() {
            repository.save(tomato());
        }

        @Test
        @DisplayName("returns a list containing tomato")
        public void findAllAfterSaveContainsSavedIngredient() {
            assertThat(repository.findAll()).containsExactly(tomato());
        }

        @Test
        @DisplayName("returns an empty ingredient when finding by unknown id")
        public void findOneNotExisting() {
            assertThat(repository.findOne(CUCUMBER_ID)).isNotPresent();
        }

        @Test
        @DisplayName("returns an empty ingredient when finding by unknown name")
        public void findOneByNameNotExisting() {
            assertThat(repository.findOneByName(CUCUMBER_NAME)).isNotPresent();
        }

        @Test
        @DisplayName("returns tomato ingredient when finding by tomato id")
        public void findOneExisting() {
            assertThat(repository.findOne(TOMATO_ID)).isPresent().contains(tomato());
        }

        @Test
        @DisplayName("returns tomato ingredient when finding by tomato name")
        public void findOneByNameExisting() {
            assertThat(repository.findOneByName(TOMATO_NAME)).isPresent().contains(tomato());
        }

        @Test
        @DisplayName("overwrites known ingredient when saving with same id")
        public void saveExistingIngredientOverwritePreviousValue() {
            repository.save(tomato().name(CUCUMBER_NAME));
            assertThat(repository.findAll()).containsExactly(new Ingredient(TOMATO_ID, CUCUMBER_NAME));
        }

        @Nested
        @DisplayName("and cucumber")
        class AndCucumber {

            @BeforeEach
            public void setUp() {
                repository.save(cucumber());
            }

            @Test
            @DisplayName("returns a list containing both tomato and cucumber")
            public void findAllAfterMultipleSaveContainsSavedIngredients() {
                assertThat(repository.findAll()).containsExactlyInAnyOrder(tomato(), cucumber());
            }

        }

    }

}
