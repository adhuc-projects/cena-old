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

import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_07_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_01_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_02_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.generateMenus7DaysAt20170101TwiceADay;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.menuGeneration7DaysAt20170101TwiceADayCurrentState;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.LASAGNE_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CANTAL_PIE_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.chiliConCarne;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.croqueMonsieur;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.duckBreastFilletWithTurnips;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.duckParmentier;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.gazpacho;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.lasagne;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.leeksWithHamAndBechamelSauce;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.moussaka;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.norvegianSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.omelette;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.poachedEggsSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.quicheLorraine;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.raclette;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.sauerkraut;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCantalPie;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberOliveFetaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.watercressSoup;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.adhuc.cena.menu.domain.model.menu.MealFrequencyIterationGenerator;
import org.adhuc.cena.menu.domain.model.menu.Menu;
import org.adhuc.cena.menu.domain.model.menu.MenuGenerationState;
import org.adhuc.cena.menu.domain.model.menu.frequency.CompositeMealFrequencyIterationGenerator;
import org.adhuc.cena.menu.domain.model.recipe.Recipe;
import org.adhuc.cena.menu.domain.model.recipe.RecipeRepository;
import org.adhuc.cena.menu.port.adapter.persistence.memory.InMemoryRecipeRepository;

/**
 * The {@link DumbMenuRecipeDefinerStrategy} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@DisplayName("Dumb menu recipe definer strategy")
public class DumbMenuRecipeDefinerStrategyTest {

    private MealFrequencyIterationGenerator mealFrequencyIterationGenerator;
    private RecipeRepository                recipeRepository;

    private DumbMenuRecipeDefinerStrategy   strategy;

    @BeforeEach
    public void setUp() {
        mealFrequencyIterationGenerator = new CompositeMealFrequencyIterationGenerator();
        recipeRepository = new InMemoryRecipeRepository();
        strategy = new DumbMenuRecipeDefinerStrategy(mealFrequencyIterationGenerator, recipeRepository);
    }

    @Test
    @DisplayName("cannot define recipe for menu with no existing recipe")
    public void defineRecipeForMenuNoDefinedRecipe() {
        assertThrows(IllegalStateException.class, () -> strategy.defineRecipeForMenu(LUNCH_2017_01_01_ID,
                new MenuGenerationState(generateMenus7DaysAt20170101TwiceADay())));
    }

    @Nested
    @DisplayName("with existing recipes")
    public class WithExistingRecipes {

        private List<Recipe> allRecipes = Arrays.asList(tomatoCucumberMozzaSalad(), tomatoCucumberOliveFetaSalad(),
                tomatoCantalPie(), quicheLorraine(), watercressSoup(), gazpacho(), poachedEggsSalad(), norvegianSalad(),
                omelette(), chiliConCarne(), sauerkraut(), leeksWithHamAndBechamelSauce(), moussaka(), lasagne(),
                duckBreastFilletWithTurnips(), croqueMonsieur(), raclette(), duckParmentier());

        @BeforeEach
        public void setUp() {
            allRecipes.forEach(recipe -> recipeRepository.save(recipe));
        }

        @Test
        public void defineRecipeForMenuFirstMenuInGeneration() {
            assertThat(strategy.defineRecipeForMenu(LUNCH_2017_01_01_ID,
                    menuGeneration7DaysAt20170101TwiceADayCurrentState()))
                            .isEqualTo(menuGeneration7DaysAt20170101TwiceADayCurrentState()
                                    .addMenu(new Menu(LUNCH_2017_01_01_ID, TOMATO_CUCUMBER_MOZZA_SALAD_ID)));
        }

        @Test
        public void defineRecipeForMenuThirdMenuInGeneration() {
            assertThat(strategy.defineRecipeForMenu(LUNCH_2017_01_02_ID,
                    menuGeneration7DaysAt20170101TwiceADayCurrentState()))
                            .isEqualTo(menuGeneration7DaysAt20170101TwiceADayCurrentState()
                                    .addMenu(new Menu(LUNCH_2017_01_02_ID, TOMATO_CANTAL_PIE_ID)));
        }

        @Test
        public void defineRecipeForMenuLastMenuInGeneration() {
            assertThat(strategy.defineRecipeForMenu(DINNER_2017_01_07_ID,
                    menuGeneration7DaysAt20170101TwiceADayCurrentState()))
                            .isEqualTo(menuGeneration7DaysAt20170101TwiceADayCurrentState()
                                    .addMenu(new Menu(DINNER_2017_01_07_ID, LASAGNE_ID)));
        }

    }

    @Nested
    @DisplayName("with not enough existing recipes")
    public class WithNotEnoughExistingRecipes {

        private List<Recipe> allRecipes = Arrays.asList(tomatoCucumberMozzaSalad(), tomatoCucumberOliveFetaSalad());

        @BeforeEach
        public void setUp() {
            allRecipes.forEach(recipe -> recipeRepository.save(recipe));
        }

        @Test
        @DisplayName("cannot define recipe for menu requiring one more recipe")
        public void defineRecipeForMenuThirdMenuInGenerationNotEnoughRecipes() {
            assertThrows(IllegalStateException.class, () -> strategy.defineRecipeForMenu(LUNCH_2017_01_02_ID,
                    menuGeneration7DaysAt20170101TwiceADayCurrentState()));
        }

    }

}
