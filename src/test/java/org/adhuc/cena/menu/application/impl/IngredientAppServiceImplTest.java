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
package org.adhuc.cena.menu.application.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.CUCUMBER_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_NAME;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.createCucumber;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.createTomato;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.cucumber;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.tomato;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.adhuc.cena.menu.domain.model.EntityNotFoundException;
import org.adhuc.cena.menu.domain.model.ingredient.CreateIngredient;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientId;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientNameAlreadyUsedException;
import org.adhuc.cena.menu.port.adapter.persistence.memory.InMemoryIngredientRepository;

/**
 * The {@link IngredientAppServiceImpl} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("appService")
@DisplayName("Ingredient service")
public class IngredientAppServiceImplTest {

    private IngredientAppServiceImpl service;

    @BeforeEach
    public void setUp() {
        service = new IngredientAppServiceImpl(new InMemoryIngredientRepository());
    }

    @Test
    @DisplayName("returns unmodifiable list of ingredients")
    public void getIngredientsIsNotModifiable() {
        assertThrows(UnsupportedOperationException.class, () -> {
            service.getIngredients().add(tomato());
        });
    }

    @Test
    @DisplayName("throws IllegalArgumentException when getting ingredient from null identity")
    public void getIngredientNullId() {
        assertThrows(IllegalArgumentException.class, () -> service.getIngredient(null));
    }

    @Test
    @DisplayName("throws IllegalArgumentException when creating ingredient from null command")
    public void createIngredientNullCommand() {
        assertThrows(IllegalArgumentException.class, () -> service.createIngredient(null));
    }

    @Nested
    @DisplayName("with no ingredient")
    class WithNoIngredient {

        @Test
        @DisplayName("returns empty list")
        public void getIngredientsEmpty() {
            assertThat(service.getIngredients()).isEmpty();
        }

    }

    @Nested
    @DisplayName("with tomato")
    class WithTomato {

        @BeforeEach
        public void setUp() {
            service.createIngredient(createTomato());
        }

        @Test
        @DisplayName("returns list containing ingredient")
        public void getIngredientsContainsCreatedIngredient() {
            assertThat(service.getIngredients()).isNotEmpty().usingFieldByFieldElementComparator()
                    .containsExactly(tomato());
        }

        @Test
        @DisplayName("cannot provide ingredient for unknown cucumber identity")
        public void getUnknownIngredient() {
            assertThrows(EntityNotFoundException.class, () -> service.getIngredient(CUCUMBER_ID));
        }

        @Test
        @DisplayName("returns ingredient from known tomato identity")
        public void getCreatedIngredient() {
            assertThat(service.getIngredient(TOMATO_ID)).isEqualToComparingFieldByField(tomato());
        }

        @Test
        @DisplayName("throws IngredientNameAlreadyUsedException when creating new ingredient with already known name")
        public void createIngredientWithAlreadyUsedName() {
            assertThrows(IngredientNameAlreadyUsedException.class, () -> {
                service.createIngredient(new CreateIngredient(IngredientId.generate(), TOMATO_NAME));
            });
        }

        @Nested
        @DisplayName("and cucumber")
        class AndCucumber {

            @BeforeEach
            public void setUp() {
                service.createIngredient(createCucumber());
            }

            @Test
            @DisplayName("returns list containing all ingredients")
            public void getIngredientsContainsAllCreatedIngredients() {
                assertThat(service.getIngredients()).isNotEmpty().usingFieldByFieldElementComparator()
                        .containsExactlyInAnyOrder(tomato(), cucumber());
            }

        }

    }

}
