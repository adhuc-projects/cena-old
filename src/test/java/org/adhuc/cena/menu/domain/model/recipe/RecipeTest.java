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

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.MOZZARELLA_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_NAME;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_OLIVE_FETA_SALAD_CONTENT;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_OLIVE_FETA_SALAD_NAME;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSaladIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSaladWithIngredients;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.adhuc.cena.menu.domain.model.ingredient.IngredientId;
import org.adhuc.cena.menu.domain.model.recipe.ingredient.IngredientNotLinkedToRecipeException;
import org.adhuc.cena.menu.domain.model.recipe.ingredient.RecipeIngredientId;

/**
 * The {@link Recipe} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("domain")
@DisplayName("Recipe")
public class RecipeTest {

    @Test
    @DisplayName("cannot be created without id")
    public void recipeWithoutId() {
        assertThrows(IllegalArgumentException.class, () -> new Recipe(null, TOMATO_CUCUMBER_MOZZA_SALAD_NAME,
                TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT, TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR));
    }

    @Test
    @DisplayName("cannot be created without name")
    public void recipeWithoutName() {
        assertThrows(IllegalArgumentException.class, () -> new Recipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID, null,
                TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT, TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR));
    }

    @Test
    @DisplayName("cannot be created with empty name")
    public void recipeWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> new Recipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID, "",
                TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT, TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR));
    }

    @Test
    @DisplayName("cannot be created without content")
    public void recipeWithoutContent() {
        assertThrows(IllegalArgumentException.class, () -> new Recipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID,
                TOMATO_CUCUMBER_MOZZA_SALAD_NAME, null, TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR));
    }

    @Test
    @DisplayName("cannot be created with empty content")
    public void recipeWithEmptyContent() {
        assertThrows(IllegalArgumentException.class, () -> new Recipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID,
                TOMATO_CUCUMBER_MOZZA_SALAD_NAME, "", TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR));
    }

    @Test
    @DisplayName("cannot be created without author")
    public void recipeWithoutAuthor() {
        assertThrows(IllegalArgumentException.class, () -> new Recipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID,
                TOMATO_CUCUMBER_MOZZA_SALAD_NAME, TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT, null));
    }

    @Nested
    @DisplayName("tomato, cucumber and mozzarella salad")
    class TomatoCucumberMozza {

        private Recipe recipe = tomatoCucumberMozzaSalad();

        @Test
        @DisplayName("contains id, name and content used during creation")
        public void recipeWithValidValues() {
            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(recipe.id()).isEqualTo(TOMATO_CUCUMBER_MOZZA_SALAD_ID);
                softly.assertThat(recipe.name()).isEqualTo(TOMATO_CUCUMBER_MOZZA_SALAD_NAME);
                softly.assertThat(recipe.content()).isEqualTo(TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT);
            });
        }

        @Test
        @DisplayName("has an empty ingredients list after creation")
        public void recipeWithEmptyIngredientsAfterCreation() {
            assertThat(recipe.ingredients()).isEmpty();
        }

        @Test
        @DisplayName("has an unmodifiable ingredients list")
        public void recipeWithUnmodifiableIngredients() {
            assertThrows(UnsupportedOperationException.class,
                    () -> recipe.ingredients().add(new RecipeIngredientId(IngredientId.generate(), false)));
        }

        @Test
        @DisplayName("throws IngredientNotLinkedToRecipeException when getting ingredient")
        public void getIngredientNotLinked() {
            IngredientNotLinkedToRecipeException exception =
                    assertThrows(IngredientNotLinkedToRecipeException.class, () -> recipe.ingredient(TOMATO_ID));
            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(exception.recipeId()).isEqualTo(TOMATO_CUCUMBER_MOZZA_SALAD_ID);
                softly.assertThat(exception.ingredientId()).isEqualTo(TOMATO_ID);
            });
        }

        @Test
        @DisplayName("cannot have its name changed with null value")
        public void changeNameWithNullValue() {
            assertThrows(IllegalArgumentException.class, () -> recipe.name(null));
        }

        @Test
        @DisplayName("cannot have its name changed with empty value")
        public void changeNameWithEmptyValue() {
            assertThrows(IllegalArgumentException.class, () -> recipe.name(""));
        }

        @Test
        @DisplayName("has a new name after changing it")
        public void changeNameWithValidValue() {
            recipe.name(TOMATO_CUCUMBER_OLIVE_FETA_SALAD_NAME);
            assertThat(recipe.name()).isEqualTo(TOMATO_CUCUMBER_OLIVE_FETA_SALAD_NAME);
        }

        @Test
        @DisplayName("cannot have its content changed with null value")
        public void changeContentWithNullValue() {
            assertThrows(IllegalArgumentException.class, () -> recipe.content(null));
        }

        @Test
        @DisplayName("cannot have its content changed with empty value")
        public void changeContentWithEmptyValue() {
            assertThrows(IllegalArgumentException.class, () -> recipe.content(""));
        }

        @Test
        @DisplayName("has a new content after changing it")
        public void changeContentWithValidValue() {
            recipe.name(TOMATO_CUCUMBER_OLIVE_FETA_SALAD_CONTENT);
            assertThat(recipe.content()).isEqualTo(TOMATO_CUCUMBER_OLIVE_FETA_SALAD_CONTENT);
        }

        @Test
        @DisplayName("contains a new ingredient after adding it")
        public void addIngredientThenIngredientContained() {
            RecipeIngredientId ingredient = new RecipeIngredientId(IngredientId.generate(), false);
            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(recipe.addIngredient(ingredient)).isTrue();
                softly.assertThat(recipe.ingredients()).contains(ingredient);
            });
        }

        @Test
        @DisplayName("contains a new main ingredient after adding it")
        public void addMainIngredientThenIngredientContained() {
            RecipeIngredientId ingredient = new RecipeIngredientId(IngredientId.generate(), true);
            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(recipe.addIngredient(ingredient)).isTrue();
                softly.assertThat(recipe.ingredients()).contains(ingredient);
            });
        }

        @Nested
        @DisplayName("with tomato main ingredient")
        class WithTomatoMainIngredient {

            @BeforeEach
            public void setUp() {
                recipe.addIngredient(new RecipeIngredientId(TOMATO_ID, true));
            }

            @Test
            @DisplayName("add tomato main ingredient twice does not change recipe")
            public void addTomatoMainIngredientTwiceDoesNotChangeRecipe() {
                RecipeIngredientId ingredient = new RecipeIngredientId(TOMATO_ID, true);
                SoftAssertions.assertSoftly(softly -> {
                    softly.assertThat(recipe.addIngredient(ingredient)).isFalse();
                    softly.assertThat(recipe.ingredients()).contains(ingredient);
                });
            }

            @Test
            @DisplayName("remove tomato from main ingredients")
            public void removeTomatoFromMainIngredients() {
                RecipeIngredientId ingredient = new RecipeIngredientId(TOMATO_ID, false);
                SoftAssertions.assertSoftly(softly -> {
                    softly.assertThat(recipe.addIngredient(ingredient)).isTrue();
                    softly.assertThat(recipe.ingredients()).contains(ingredient)
                            .doesNotContain(new RecipeIngredientId(TOMATO_ID, true));
                });
            }

            @Test
            @DisplayName("finds ingredient from identity")
            public void getIngredientLinkedToRecipe() {
                assertThat(recipe.ingredient(TOMATO_ID)).isEqualTo(new RecipeIngredientId(TOMATO_ID, true));
            }

        }

        @Nested
        @DisplayName("with mozzarella basic ingredient")
        class WithMozzaBasicIngredient {

            @BeforeEach
            public void setUp() {
                recipe.addIngredient(new RecipeIngredientId(MOZZARELLA_ID, false));
            }

            @Test
            @DisplayName("add mozzarella basic ingredient twice does not change recipe")
            public void addMozzaNotMainIngredientTwiceDoesNotChangeRecipe() {
                RecipeIngredientId ingredient = new RecipeIngredientId(MOZZARELLA_ID, false);
                SoftAssertions.assertSoftly(softly -> {
                    softly.assertThat(recipe.addIngredient(ingredient)).isFalse();
                    softly.assertThat(recipe.ingredients()).contains(ingredient);
                });
            }

            @Test
            @DisplayName("add mozzarella to main ingredients")
            public void addMozzaToMainIngredients() {
                RecipeIngredientId ingredient = new RecipeIngredientId(MOZZARELLA_ID, true);
                SoftAssertions.assertSoftly(softly -> {
                    softly.assertThat(recipe.addIngredient(ingredient)).isTrue();
                    softly.assertThat(recipe.ingredients()).contains(ingredient)
                            .doesNotContain(new RecipeIngredientId(MOZZARELLA_ID, false));
                });
            }

        }

    }

    @Nested
    @DisplayName("tomato, cucumber and mozzarella salad with ingredients")
    class TomatoCucumberMozzaWithIngredients {

        private Recipe recipe = tomatoCucumberMozzaSaladWithIngredients();

        @Test
        @DisplayName("contains id, name, content and ingredients used during creation")
        public void recipeWithValidValues() {
            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(recipe.id()).isEqualTo(TOMATO_CUCUMBER_MOZZA_SALAD_ID);
                softly.assertThat(recipe.name()).isEqualTo(TOMATO_CUCUMBER_MOZZA_SALAD_NAME);
                softly.assertThat(recipe.content()).isEqualTo(TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT);
                softly.assertThat(recipe.ingredients()).containsAll(tomatoCucumberMozzaSaladIngredients());
            });

        }

        @Test
        @DisplayName("has an unmodifiable ingredients list")
        public void recipeWithUnmodifiableIngredients() {
            assertThrows(UnsupportedOperationException.class,
                    () -> recipe.ingredients().add(new RecipeIngredientId(IngredientId.generate(), false)));
        }

    }
}
