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

import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_02_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.dinner20170102;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.dinner20170103;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.generateMenus1DayAt20170102WeekWorkingDays;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.lunch20170103;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.lunch20170104;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.menuGeneration2DaysAt20170103TwiceADayCurrentState;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.allRecipesWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.croqueMonsieur;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.lasagne;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.omelette;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.raclette;

import java.util.Arrays;
import java.util.Collections;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * The {@link MenuGenerationState} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@DisplayName("Menu generation state")
public class MenuGenerationStateTest {

    @Test
    @DisplayName("cannot be created with null command")
    public void createWithNullCommand() {
        assertThrows(IllegalArgumentException.class, () -> new MenuGenerationState(null));
    }

    @Nested
    @DisplayName("after initialization")
    public class AfterInitialization {

        private MenuGenerationState state;

        @BeforeEach
        public void setUp() {
            state = new MenuGenerationState(generateMenus1DayAt20170102WeekWorkingDays());
        }

        @Test
        @DisplayName("cannot be add null menu")
        public void addNullMenu() {
            assertThrows(IllegalArgumentException.class, () -> state.addMenu(null));
        }

        @Test
        @DisplayName("has no menu after initialization")
        public void hasNoMenuAfterInitialization() {
            assertThat(state.menus()).isNotNull().isEmpty();
        }

        @Test
        @DisplayName("has immutable list of menus")
        public void hasImmutableList() {
            assertThrows(UnsupportedOperationException.class, () -> state.menus().add(dinner20170102()));
        }

        @Test
        @DisplayName("is not modified after adding a menu")
        public void notModifiedAfterAddMenu() {
            state.addMenu(dinner20170102());
            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(state.command()).isEqualTo(generateMenus1DayAt20170102WeekWorkingDays());
                softly.assertThat(state.menus()).isEmpty();
            });
        }

        @Test
        @DisplayName("cannot filter null recipes list")
        public void filterAlreadyUsedRecipesNullList() {
            assertThrows(IllegalArgumentException.class, () -> state.filterAlreadyUsedRecipes(null));
        }

        @Test
        @DisplayName("filter already used recipes from empty list")
        public void filterAlreadyUsedRecipesEmptyList() {
            assertThat(state.filterAlreadyUsedRecipes(Collections.emptyList())).isEmpty();
        }

        @Test
        @DisplayName("filter already used recipes returns all recipes")
        public void filterAlreadyUsedRecipes() {
            assertThat(state.filterAlreadyUsedRecipes(allRecipesWithIngredients()))
                    .containsExactlyElementsOf(allRecipesWithIngredients());
        }

    }

    @Nested
    @DisplayName("with menus")
    public class WithMenus {

        private MenuGenerationState state;

        @BeforeEach
        public void setUp() {
            state = menuGeneration2DaysAt20170103TwiceADayCurrentState();
        }

        @Test
        @DisplayName("returns empty from unknown id")
        public void getMenuUnknown() {
            assertThat(state.menu(LUNCH_2017_01_02_ID)).isNotPresent();
        }

        @Test
        @DisplayName("returns known menu from id")
        public void getMenuKnown() {
            assertThat(state.menu(lunch20170103().id())).isPresent().contains(lunch20170103());
        }

        @Test
        @DisplayName("has menus after initialization")
        public void hasNoMenuAfterInitialization() {
            assertThat(state.menus()).isNotNull().isNotEmpty().contains(lunch20170103(), dinner20170103());
        }

        @Test
        @DisplayName("has immutable list of menus")
        public void hasImmutableList() {
            assertThrows(UnsupportedOperationException.class, () -> state.menus().add(lunch20170104()));
        }

        @Test
        @DisplayName("is not modified after adding a menu")
        public void notModifiedAfterAddMenu() {
            state.addMenu(dinner20170102());
            SoftAssertions.assertSoftly(softly -> {
                MenuGenerationState originalState = menuGeneration2DaysAt20170103TwiceADayCurrentState();
                softly.assertThat(state.command()).isEqualTo(originalState.command());
                softly.assertThat(state.menus()).containsExactlyElementsOf(originalState.menus());
            });
        }

        @Test
        @DisplayName("filter already used recipes from recipes list containing only used recipes returns empty list")
        public void filterAlreadyUsedRecipesAll() {
            assertThat(state.filterAlreadyUsedRecipes(Arrays.asList(raclette(), croqueMonsieur()))).isEmpty();
        }

        @Test
        @DisplayName("filter already used recipes from recipes list containing a subset of used recipes returns the filtered list")
        public void filterAlreadyUsedRecipesSubset() {
            assertThat(
                    state.filterAlreadyUsedRecipes(Arrays.asList(raclette(), croqueMonsieur(), omelette(), lasagne())))
                            .containsExactlyInAnyOrder(omelette(), lasagne());
        }

    }

}
