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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_02_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_02_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.generateMenus1DayAt20170102WeekWorkingDays;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.lunch20170102;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSalad;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.adhuc.cena.menu.domain.model.menu.frequency.CompositeMealFrequencyIterationGenerator;
import org.adhuc.cena.menu.domain.model.recipe.RecipeRepository;
import org.adhuc.cena.menu.port.adapter.persistence.memory.InMemoryMenuRepository;
import org.adhuc.cena.menu.port.adapter.persistence.memory.InMemoryRecipeRepository;

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
    private RecipeRepository                recipeRepository;
    private MealFrequencyIterationGenerator mealFrequencyIterationGenerator;
    private MenuGenerationService           service;

    @BeforeEach
    public void setUp() {
        menuRepository = new InMemoryMenuRepository();
        recipeRepository = new InMemoryRecipeRepository();
        mealFrequencyIterationGenerator = new CompositeMealFrequencyIterationGenerator();
        service = new MenuGenerationService(menuRepository, recipeRepository, mealFrequencyIterationGenerator);
    }

    @DisplayName("throws IllegalArgumentException while generating menus without recipe")
    public void generateMenusWithoutRecipe() {
        assertThrows(IllegalArgumentException.class,
                () -> service.generateMenus(generateMenus1DayAt20170102WeekWorkingDays()));
    }

    @Nested
    @DisplayName("with recipes")
    public class WithRecipes {

        @BeforeEach
        public void setUp() {
            recipeRepository.save(tomatoCucumberMozzaSalad());
        }

        @Test
        @DisplayName("generating one menu for one day saves the menu for the requested meal")
        public void generateMenusForOneDaySaveMenuForRequestedMeal() {
            assumeFalse(menuRepository.findOne(DINNER_2017_01_02_ID).isPresent());
            service.generateMenus(generateMenus1DayAt20170102WeekWorkingDays());

            Optional<Menu> menu = menuRepository.findOne(DINNER_2017_01_02_ID);
            assertThat(menu).isNotEmpty();
            assertThat(menu.get().recipe()).isNotNull();
        }

        @Test
        @DisplayName("generating one menu for dinner does not generate menu for lunch")
        public void generateMenusDinnerDoesNotGenerateLunch() {
            service.generateMenus(generateMenus1DayAt20170102WeekWorkingDays());

            assertThat(menuRepository.findOne(LUNCH_2017_01_02_ID)).isEmpty();
        }

        @Test
        @DisplayName("generating one menu for dinner does not change menu for lunch")
        public void generateMenusDinnerDoesNotChangeLunch() {
            Menu lunch = lunch20170102();
            menuRepository.save(lunch);
            service.generateMenus(generateMenus1DayAt20170102WeekWorkingDays());

            assertThat(menuRepository.findOne(LUNCH_2017_01_02_ID)).isNotEmpty().containsSame(lunch);
        }

        @Test
        @DisplayName("generating one menu for one day generates the menu with recipe")
        public void generateMenusForOneDayMenuHasRecipe() {
            service.generateMenus(generateMenus1DayAt20170102WeekWorkingDays());

            Optional<Menu> menu = menuRepository.findOne(DINNER_2017_01_02_ID);
            assertThat(menu.get().recipe()).isNotNull();
        }

    }

}
