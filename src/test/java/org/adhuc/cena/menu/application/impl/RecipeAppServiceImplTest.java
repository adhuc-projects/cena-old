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

import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_OLIVE_FETA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.createTomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.createTomatoCucumberOliveFetaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberOliveFetaSalad;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.adhuc.cena.menu.domain.model.EntityNotFoundException;
import org.adhuc.cena.menu.port.adapter.persistence.memory.InMemoryRecipeRepository;

/**
 * The {@link RecipeAppServiceImpl} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("appService")
@DisplayName("Recipe service")
public class RecipeAppServiceImplTest {

    private RecipeAppServiceImpl service;

    @BeforeEach
    public void setUp() {
        service = new RecipeAppServiceImpl(new InMemoryRecipeRepository());
    }

    @Test
    @DisplayName("returns unmodifiable list of recipes")
    public void getRecipesIsNotModifiable() {
        assertThrows(UnsupportedOperationException.class, () -> {
            service.getRecipes().add(tomatoCucumberMozzaSalad());
        });
    }

    @Test
    @DisplayName("throws IllegalArgumentException when getting recipe from null identity")
    public void getRecipeNullId() {
        assertThrows(IllegalArgumentException.class, () -> service.getRecipe(null));
    }

    @Test
    @DisplayName("throws IllegalArgumentException when creating recipe from null command")
    public void createIngredientNullCommand() {
        assertThrows(IllegalArgumentException.class, () -> service.createRecipe(null));
    }

    @Nested
    @DisplayName("with no recipe")
    class WithNoRecipe {

        @Test
        @DisplayName("returns empty list")
        public void getRecipesEmpty() {
            assertThat(service.getRecipes()).isEmpty();
        }

    }

    @Nested
    @DisplayName("with tomato, cucumber and mozzarella salad")
    class WithTomatoCucumberMozzaSalad {

        @BeforeEach
        public void setUp() {
            service.createRecipe(createTomatoCucumberMozzaSalad());
        }

        @Test
        @DisplayName("returns list containing recipe")
        public void getRecipesContainsCreatedRecipe() {
            assertThat(service.getRecipes()).isNotEmpty().usingFieldByFieldElementComparator()
                    .containsExactly(tomatoCucumberMozzaSalad());
        }

        @Test
        @DisplayName("cannot provide recipe for unknown tomato, cucumber, olive and feta salad identity")
        public void getUnknownRecipe() {
            assertThrows(EntityNotFoundException.class, () -> service.getRecipe(TOMATO_CUCUMBER_OLIVE_FETA_SALAD_ID));
        }

        @Test
        @DisplayName("returns recipe from known tomato, cucumber and mozza salad identity")
        public void getCreatedRecipe() {
            assertThat(service.getRecipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID))
                    .isEqualToComparingFieldByField(tomatoCucumberMozzaSalad());
        }

        @Nested
        @DisplayName("and tomato, cucumber, olive and feta salad")
        class AndCucumber {

            @BeforeEach
            public void setUp() {
                service.createRecipe(createTomatoCucumberOliveFetaSalad());
            }

            @Test
            @DisplayName("returns list containing all recipes")
            public void getRecipesContainsAllCreatedRecipes() {
                assertThat(service.getRecipes()).isNotEmpty().usingFieldByFieldElementComparator()
                        .containsExactlyInAnyOrder(tomatoCucumberMozzaSalad(), tomatoCucumberOliveFetaSalad());
            }

        }

    }

}
