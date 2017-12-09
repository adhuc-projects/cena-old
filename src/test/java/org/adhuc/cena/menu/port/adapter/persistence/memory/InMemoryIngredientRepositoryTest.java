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

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.CUCUMBER_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.CUCUMBER_NAME;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_NAME;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.cucumber;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.tomato;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

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

    @Nested
    @DisplayName("with no ingredient")
    class WithNoIngredient {

        @Test
        @DisplayName("returns empty list from a list of ingredient identities containing tomato and cucumber ids")
        public void findAllByIdEmpty() {
            assertThat(repository.findAll(Arrays.asList(TOMATO_ID, CUCUMBER_ID))).isEmpty();
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
        @DisplayName("returns a list containing tomato from a list of ingredient identities containing tomato and cucumber ids")
        public void findAllById() {
            assertThat(repository.findAll(Arrays.asList(TOMATO_ID, CUCUMBER_ID))).usingFieldByFieldElementComparator()
                    .containsExactly(tomato());
        }

        @Test
        @DisplayName("returns an empty ingredient when finding by unknown name")
        public void findOneByNameNotExisting() {
            assertThat(repository.findOneByName(CUCUMBER_NAME)).isNotPresent();
        }

        @Test
        @DisplayName("returns tomato ingredient when finding by tomato name")
        public void findOneByNameExisting() {
            assertThat(repository.findOneByName(TOMATO_NAME)).isPresent().usingFieldByFieldValueComparator()
                    .contains(tomato());
        }

        @Nested
        @DisplayName("and cucumber")
        class AndCucumber {

            @BeforeEach
            public void setUp() {
                repository.save(cucumber());
            }

            @Test
            @DisplayName("returns a list containing tomato from a list of ingredient identities containing tomato id")
            public void findAllByIdWithTomatoOnly() {
                assertThat(repository.findAll(Arrays.asList(TOMATO_ID))).usingFieldByFieldElementComparator()
                        .containsExactly(tomato());
            }

            @Test
            @DisplayName("returns a list containing both tomato and cucumber from a list of ingredient identities containing tomato and cucumber ids")
            public void findAllById() {
                assertThat(repository.findAll(Arrays.asList(TOMATO_ID, CUCUMBER_ID)))
                        .usingFieldByFieldElementComparator().containsExactlyInAnyOrder(tomato(), cucumber());
            }

        }

    }

}
