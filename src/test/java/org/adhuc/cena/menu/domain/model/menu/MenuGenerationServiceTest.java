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
package org.adhuc.cena.menu.domain.model.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_02_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_03_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_04_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_02_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_03_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_04_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.generateMenus1DayAt20170102WeekWorkingDays;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.generateMenus2DaysAt20170103TwiceADay;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.lunch20170102;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.QUICHE_LORRAINE_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CANTAL_PIE_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_OLIVE_FETA_SALAD_ID;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.adhuc.cena.menu.domain.model.menu.frequency.CompositeMealFrequencyIterationGenerator;
import org.adhuc.cena.menu.port.adapter.persistence.memory.InMemoryMenuRepository;

/**
 * The {@link MenuGenerationService} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("domain")
@DisplayName("Menu generation service")
public class MenuGenerationServiceTest {

    private MenuRepository                  menuRepository;
    private MealFrequencyIterationGenerator mealFrequencyIterationGenerator;
    private MenuRecipeDefinerStrategy       menuRecipeDefinerStrategy;
    private MenuGenerationService           service;

    @BeforeEach
    public void setUp() {
        menuRepository = new InMemoryMenuRepository();
        mealFrequencyIterationGenerator = new CompositeMealFrequencyIterationGenerator();
        menuRecipeDefinerStrategy = mock(MenuRecipeDefinerStrategy.class);
        service = new MenuGenerationService(menuRepository, mealFrequencyIterationGenerator, menuRecipeDefinerStrategy);
    }

    @Nested
    @DisplayName("generating one menu for one day")
    public class Generate1MenuFor1Day {

        private GenerateMenus command;

        @BeforeEach
        public void setUp() {
            command = generateMenus1DayAt20170102WeekWorkingDays();

            MenuGenerationState state = new MenuGenerationState(command);
            when(menuRecipeDefinerStrategy.defineRecipeForMenu(any(), any()))
                    .thenReturn(state.addMenu(new Menu(DINNER_2017_01_02_ID, TOMATO_CUCUMBER_MOZZA_SALAD_ID)));
        }

        @Test
        @DisplayName("saves the menu for the requested meal")
        public void generateMenusForOneDaySaveMenuForRequestedMeal() {
            assumeFalse(menuRepository.findOne(DINNER_2017_01_02_ID).isPresent());
            service.generateMenus(command);

            Optional<Menu> menu = menuRepository.findOne(DINNER_2017_01_02_ID);
            assertThat(menu).isNotEmpty();
            assertThat(menu.get().recipe()).isNotNull();
        }

        @Test
        @DisplayName("does not generate menu for lunch")
        public void generateMenusDinnerDoesNotGenerateLunch() {
            service.generateMenus(command);

            assertThat(menuRepository.findOne(LUNCH_2017_01_02_ID)).isEmpty();
        }

        @Test
        @DisplayName("does not change menu for lunch")
        public void generateMenusDinnerDoesNotChangeLunch() {
            Menu lunch = lunch20170102();
            menuRepository.save(lunch);
            service.generateMenus(command);

            assertThat(menuRepository.findOne(LUNCH_2017_01_02_ID)).isNotEmpty().containsSame(lunch);
        }

        @Test
        @DisplayName("generates the menu with recipe")
        public void generateMenusForOneDayMenuHasRecipe() {
            service.generateMenus(command);

            Optional<Menu> menu = menuRepository.findOne(DINNER_2017_01_02_ID);
            assertThat(menu.get().recipe()).isNotNull();
        }

    }

    @Nested
    @DisplayName("generating menus for multiple days")
    public class GenerateMenusForMultipleDays {

        private GenerateMenus command;

        @BeforeEach
        public void setUp() {
            command = generateMenus2DaysAt20170103TwiceADay();

            MenuGenerationState originalState = new MenuGenerationState(command);
            MenuGenerationState state1 =
                    originalState.addMenu(new Menu(LUNCH_2017_01_03_ID, TOMATO_CUCUMBER_MOZZA_SALAD_ID));
            MenuGenerationState state2 =
                    state1.addMenu(new Menu(DINNER_2017_01_03_ID, TOMATO_CUCUMBER_OLIVE_FETA_SALAD_ID));
            MenuGenerationState state3 = state2.addMenu(new Menu(LUNCH_2017_01_04_ID, TOMATO_CANTAL_PIE_ID));
            MenuGenerationState state4 = state3.addMenu(new Menu(DINNER_2017_01_04_ID, QUICHE_LORRAINE_ID));
            when(menuRecipeDefinerStrategy.defineRecipeForMenu(any(), any())).thenReturn(state1, state2, state3,
                    state4);
        }

        @Test
        @DisplayName("generates the menus with different recipes")
        public void generateMenusForMultipleDaysMenusHaveDifferentRecipes() {
            service.generateMenus(command);

            Menu lunch20170103 = menuRepository.findOneNotNull(LUNCH_2017_01_03_ID);
            Menu dinner20170103 = menuRepository.findOneNotNull(DINNER_2017_01_03_ID);
            Menu lunch20170104 = menuRepository.findOneNotNull(LUNCH_2017_01_04_ID);
            Menu dinner20170104 = menuRepository.findOneNotNull(DINNER_2017_01_04_ID);
            assertThat(Arrays.asList(lunch20170103, dinner20170103, lunch20170104, dinner20170104).stream()
                    .map(m -> m.recipe()).collect(Collectors.toSet())).hasSize(4);
        }

    }

}
