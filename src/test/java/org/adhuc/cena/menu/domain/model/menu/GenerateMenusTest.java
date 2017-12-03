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

import static org.adhuc.cena.menu.domain.model.menu.MenuMother.MENU_2017_01_01_DAYS;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.MENU_2017_01_01_FREQUENCY;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.MENU_2017_01_01_START_DATE;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.MENU_2017_01_02_DAYS;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.MENU_2017_01_02_FREQUENCY;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.MENU_2017_01_02_START_DATE;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The {@link GenerateMenus} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("domain")
@DisplayName("Generate menus command")
public class GenerateMenusTest {

    @Test
    @DisplayName("cannot be created with negative days")
    public void generateMenusWithNegativeDays() {
        assertThrows(IllegalArgumentException.class,
                () -> new GenerateMenus(-1, MENU_2017_01_02_START_DATE, MENU_2017_01_02_FREQUENCY));
    }

    @Test
    @DisplayName("cannot be created with 0 days")
    public void generateMenusWithZeroDays() {
        assertThrows(IllegalArgumentException.class,
                () -> new GenerateMenus(0, MENU_2017_01_02_START_DATE, MENU_2017_01_02_FREQUENCY));
    }

    @Test
    @DisplayName("cannot be created with more than 10 days")
    public void generateMenusWith11Days() {
        assertThrows(IllegalArgumentException.class,
                () -> new GenerateMenus(11, MENU_2017_01_02_START_DATE, MENU_2017_01_02_FREQUENCY));
    }

    @Test
    @DisplayName("cannot be created with invalid start date")
    public void generateMenusWithInvalidStartDate() {
        assertThrows(IllegalArgumentException.class,
                () -> new GenerateMenus(MENU_2017_01_02_DAYS, null, MENU_2017_01_02_FREQUENCY));
    }

    @Test
    @DisplayName("cannot be created with invalid meal frequency")
    public void generateMenusWithInvalidMealFrequency() {
        assertThrows(IllegalArgumentException.class,
                () -> new GenerateMenus(MENU_2017_01_02_DAYS, MENU_2017_01_02_START_DATE, null));
    }

    @Test
    @DisplayName("is composed of days, start date and frequency, with days higher or equal to 1")
    public void generateMenus1DayAt20170102WeekWorkingDays() {
        GenerateMenus command = MenuMother.generateMenus1DayAt20170102WeekWorkingDays();
        assertThat(command.days()).isEqualTo(MENU_2017_01_02_DAYS);
        assertThat(command.startDate()).isEqualTo(MENU_2017_01_02_START_DATE);
        assertThat(command.frequency()).isEqualTo(MENU_2017_01_02_FREQUENCY);
    }

    @Test
    @DisplayName("is composed of days, start date and frequency, with days higher or equal to 1")
    public void generateMenus10DaysAt20170101TwiceADay() {
        GenerateMenus command = MenuMother.generateMenus7DaysAt20170101TwiceADay();
        assertThat(command.days()).isEqualTo(MENU_2017_01_01_DAYS);
        assertThat(command.startDate()).isEqualTo(MENU_2017_01_01_START_DATE);
        assertThat(command.frequency()).isEqualTo(MENU_2017_01_01_FREQUENCY);
    }

}
