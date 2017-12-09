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

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.CUCUMBER_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.cucumber;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.mustard;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CANTAL_PIE_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.addCucumberToTomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.addMustardToTomatoCantalPie;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.cucumberInTomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.mustardInTomatoCantalPie;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCantalPie;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSalad;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.adhuc.cena.menu.domain.model.EntityNotFoundException;
import org.adhuc.cena.menu.domain.model.ingredient.Ingredient;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientRepository;
import org.adhuc.cena.menu.domain.model.recipe.ingredient.RecipeIngredientAdditionService;
import org.adhuc.cena.menu.port.adapter.persistence.memory.InMemoryIngredientRepository;
import org.adhuc.cena.menu.port.adapter.persistence.memory.InMemoryRecipeRepository;

/**
 * The {@link RecipeIngredientAdditionService} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("domain")
@DisplayName("Recipe ingredient addition service")
public class RecipeIngredientAdditionServiceTest {

    private RecipeRepository                recipeRepository;
    private IngredientRepository            ingredientRepository;
    private RecipeIngredientAdditionService service;

    @BeforeEach
    public void setUp() {
        recipeRepository = new InMemoryRecipeRepository();
        ingredientRepository = new InMemoryIngredientRepository();
        service = new RecipeIngredientAdditionService(recipeRepository, ingredientRepository);
    }

    @Test
    @DisplayName("throws EntityNotFoundException when adding ingredient to unknown recipe")
    public void addIngredientToUnknownRecipe() {
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.addIngredientToRecipe(addCucumberToTomatoCucumberMozzaSalad()));
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(exception.entityType()).isEqualTo(Recipe.class);
            softly.assertThat(exception.identity()).isEqualTo(TOMATO_CUCUMBER_MOZZA_SALAD_ID.toString());
        });
    }

    @Nested
    @DisplayName("with tomato, cucumber and mozzarella salad")
    public class WithTomatoCucumberMozzarellaSalad {

        private Recipe recipe;

        @BeforeEach
        public void setUp() {
            recipe = tomatoCucumberMozzaSalad();
            recipeRepository.save(recipe);
        }

        @Test
        @DisplayName("throws EntityNotFoundException when adding unknown ingredient to recipe")
        public void addUnknownIngredientToRecipe() {
            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> service.addIngredientToRecipe(addCucumberToTomatoCucumberMozzaSalad()));
            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(exception.entityType()).isEqualTo(Ingredient.class);
                softly.assertThat(exception.identity()).isEqualTo(CUCUMBER_ID.toString());
            });
        }

        @Nested
        @DisplayName("with cucumber")
        public class WithCucumber {

            @BeforeEach
            public void setUp() {
                ingredientRepository.save(cucumber());
            }

            @Test
            @DisplayName("adds correctly ingredient to recipe")
            public void addIngredientToRecipe() {
                service.addIngredientToRecipe(addCucumberToTomatoCucumberMozzaSalad());

                Recipe recipe = recipeRepository.findOneNotNull(TOMATO_CUCUMBER_MOZZA_SALAD_ID);
                assertThat(recipe.ingredients()).contains(cucumberInTomatoCucumberMozzaSalad());
            }

        }

    }

    @Nested
    @DisplayName("with tomato and cantal pie recipe and mustard ingredient")
    public class WithTomatoCantalPie {

        private Recipe recipe;

        @BeforeEach
        public void setUp() {
            recipe = tomatoCantalPie();
            recipeRepository.save(recipe);
            ingredientRepository.save(mustard());
        }

        @Test
        @DisplayName("adds correctly ingredient to recipe")
        public void addIngredientToRecipe() {
            service.addIngredientToRecipe(addMustardToTomatoCantalPie());

            Recipe recipe = recipeRepository.findOneNotNull(TOMATO_CANTAL_PIE_ID);
            assertThat(recipe.ingredients()).contains(mustardInTomatoCantalPie());
        }

    }

}
