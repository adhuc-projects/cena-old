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
package org.adhuc.cena.menu.domain.model.menu.generation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_01_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_03_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_04_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_05_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_06_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_07_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_01_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_02_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_03_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_04_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.generateMenus2DaysAt20170103TwiceADay;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.generateMenus7DaysAt20170101TwiceADay;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.CAESAR_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.DUCK_BREAST_FILLET_WITH_TURNIPS_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.DUCK_PARMENTIER_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.GAZPACHO_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.JAPANESE_TUNA_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.LASAGNE_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.LEEKS_WITH_HAM_AND_BECHAMEL_SAUCE_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.MOUSSAKA_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.NORVEGIAN_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.OMELETTE_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.QUICHE_LORRAINE_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.RACLETTE_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.SAUERKRAUT_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.WATERCRESS_SOUP_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.allRecipesWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.chiliConCarneWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.gazpachoWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.lasagneWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.leeksWithHamAndBechamelSauceWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCantalPieWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSaladWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberOliveFetaSaladWithIngredients;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.ThrowingConsumer;

import org.adhuc.cena.menu.domain.model.menu.Menu;
import org.adhuc.cena.menu.domain.model.menu.MenuGenerationState;
import org.adhuc.cena.menu.domain.model.recipe.Recipe;
import org.adhuc.cena.menu.domain.model.recipe.RecipeRepository;
import org.adhuc.cena.menu.port.adapter.persistence.memory.InMemoryRecipeRepository;

/**
 * The {@link RandomMenuRecipeDefinerStrategy} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("domain")
@DisplayName("Random menu recipe definer strategy")
public class RandomMenuRecipeDefinerStrategyTest {

    private RecipeRepository                recipeRepository;
    private RandomMenuRecipeDefinerStrategy strategy;

    @BeforeEach
    public void setUp() {
        recipeRepository = new InMemoryRecipeRepository();
        strategy = new RandomMenuRecipeDefinerStrategy(recipeRepository);
    }

    @Test
    @DisplayName("cannot define recipe for menu with no existing recipe")
    public void defineRecipeForMenuNoDefinedRecipe() {
        assertThrows(IllegalStateException.class, () -> strategy.defineRecipeForMenu(LUNCH_2017_01_01_ID,
                new MenuGenerationState(generateMenus7DaysAt20170101TwiceADay())));
    }

    @Nested
    @DisplayName("with existing recipes containing already used main ingredient")
    class WithExistingRecipesAlreadyUsedMainIngredient {

        private List<Recipe>        allRecipes = Arrays.asList(tomatoCucumberMozzaSaladWithIngredients(),
                tomatoCucumberOliveFetaSaladWithIngredients(), tomatoCantalPieWithIngredients(),
                gazpachoWithIngredients(), chiliConCarneWithIngredients(), lasagneWithIngredients());
        private MenuGenerationState state;

        @BeforeEach
        public void setUp() {
            allRecipes.forEach(recipe -> recipeRepository.save(recipe));
            state = new MenuGenerationState(generateMenus2DaysAt20170103TwiceADay())
                    .addMenu(new Menu(LUNCH_2017_01_03_ID, LASAGNE_ID))
                    .addMenu(new Menu(DINNER_2017_01_04_ID, GAZPACHO_ID));
        }

        @Test
        @DisplayName("cannot define recipe for menu")
        public void defineRecipeForMenuNoUsableRecipe() {
            assertThrows(IllegalStateException.class, () -> strategy.defineRecipeForMenu(LUNCH_2017_01_04_ID, state));
        }

        @Nested
        @DisplayName("except one")
        class ExceptOne {

            @BeforeEach
            public void setUp() {
                recipeRepository.save(leeksWithHamAndBechamelSauceWithIngredients());
            }

            @Test
            @DisplayName("defines leeks with ham and bechamel sauce")
            public void defineRecipeForMenu() {
                assertThat(strategy.defineRecipeForMenu(LUNCH_2017_01_04_ID, state).menus())
                        .contains(new Menu(LUNCH_2017_01_04_ID, LEEKS_WITH_HAM_AND_BECHAMEL_SAUCE_ID));
            }

        }

    }

    @Nested
    @DisplayName("with existing recipes")
    class WithExistingRecipes {

        private MenuGenerationState state;

        @BeforeEach
        public void setUp() {
            allRecipesWithIngredients().forEach(recipe -> recipeRepository.save(recipe));
            state = new MenuGenerationState(generateMenus7DaysAt20170101TwiceADay())
                    .addMenu(new Menu(LUNCH_2017_01_01_ID, DUCK_BREAST_FILLET_WITH_TURNIPS_ID))
                    .addMenu(new Menu(DINNER_2017_01_01_ID, NORVEGIAN_SALAD_ID))
                    .addMenu(new Menu(LUNCH_2017_01_02_ID, TOMATO_CUCUMBER_MOZZA_SALAD_ID))
                    .addMenu(new Menu(LUNCH_2017_01_03_ID, LASAGNE_ID))
                    .addMenu(new Menu(DINNER_2017_01_03_ID, OMELETTE_ID))
                    .addMenu(new Menu(DINNER_2017_01_04_ID, GAZPACHO_ID))
                    .addMenu(new Menu(DINNER_2017_01_05_ID, RACLETTE_ID))
                    .addMenu(new Menu(DINNER_2017_01_06_ID, QUICHE_LORRAINE_ID))
                    .addMenu(new Menu(DINNER_2017_01_07_ID, WATERCRESS_SOUP_ID));
        }

        @TestFactory
        public Stream<DynamicTest> defineRecipeForMenu() {
            Iterator<Integer> inputGenerator = new Iterator<Integer>() {
                int iterations = 20;
                int current    = 0;

                @Override
                public boolean hasNext() {
                    current += 1;
                    return current <= iterations;
                }

                @Override
                public Integer next() {
                    return current;
                }
            };

            Function<Integer, String> displayNameGenerator =
                    (input) -> "define recipe randomly (iteration " + input + ")";

            ThrowingConsumer<Integer> testExecutor = (input) -> SoftAssertions.assertSoftly(softly -> {
                MenuGenerationState nextState = strategy.defineRecipeForMenu(LUNCH_2017_01_04_ID, state);
                assertThat(nextState.menu(LUNCH_2017_01_04_ID)).isPresent();
                assertThat(nextState.menu(LUNCH_2017_01_04_ID).get().recipe()).as(
                        "Recipe %s is either sauerkraut, leeks with ham and bechamel sauce, moussaka or duck parmentier",
                        recipeRepository.findOneNotNull(nextState.menu(LUNCH_2017_01_04_ID).get().recipe()))
                        .isIn(Arrays.asList(SAUERKRAUT_ID, LEEKS_WITH_HAM_AND_BECHAMEL_SAUCE_ID, MOUSSAKA_ID,
                                DUCK_PARMENTIER_ID, CAESAR_SALAD_ID, JAPANESE_TUNA_ID));
            });

            return DynamicTest.stream(inputGenerator, displayNameGenerator, testExecutor);
        }

    }

}
