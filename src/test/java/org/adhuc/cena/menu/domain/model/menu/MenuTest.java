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

import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_02_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_02_RECIPE;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.dinner20170101;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.dinner20170102;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.lunch20170101;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.lunch20170102;

import java.util.stream.Stream;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * The {@link Menu} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("domain")
@DisplayName("Menu")
public class MenuTest {

    @Test
    @DisplayName("cannot be created without id")
    public void menuWithoutId() {
        assertThrows(IllegalArgumentException.class, () -> new Menu(null, DINNER_2017_01_02_RECIPE));
    }

    @Test
    @DisplayName("cannot be created without recipe")
    public void recipeWithoutRecipe() {
        assertThrows(IllegalArgumentException.class, () -> new Menu(DINNER_2017_01_02_ID, null));
    }

    @Nested
    @DisplayName("dinner at 2017à-01-02")
    class Dinner20170102 {

        private Menu menu = dinner20170102();

        @Test
        @DisplayName("contains id and recipe used during creation")
        public void menuWithValidValues() {
            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(menu.id()).isEqualTo(DINNER_2017_01_02_ID);
                softly.assertThat(menu.recipe()).isEqualTo(DINNER_2017_01_02_RECIPE);
            });
        }

    }

    @ParameterizedTest
    @MethodSource("comparableMenus")
    @DisplayName("compared with other")
    public void compareMenus(Menu m1, Menu m2, int result) {
        if (result < 0) {
            assertThat(m1.compareTo(m2)).isLessThan(0);
        } else if (result > 0) {
            assertThat(m1.compareTo(m2)).isGreaterThan(0);
        } else {
            assertThat(m1.compareTo(m2)).isEqualTo(0);
        }
    }

    static Stream<Arguments> comparableMenus() {
        return Stream.of(Arguments.of(lunch20170101(), lunch20170101(), 0),
                Arguments.of(dinner20170101(), dinner20170101(), 0),
                Arguments.of(lunch20170101(), dinner20170101(), -1), Arguments.of(lunch20170101(), lunch20170102(), -1),
                Arguments.of(dinner20170101(), lunch20170102(), -1), Arguments.of(lunch20170102(), lunch20170101(), 1),
                Arguments.of(dinner20170102(), lunch20170101(), 1));
    }

}
