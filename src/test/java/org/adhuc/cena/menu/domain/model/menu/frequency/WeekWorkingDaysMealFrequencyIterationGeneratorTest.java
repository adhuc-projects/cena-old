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
package org.adhuc.cena.menu.domain.model.menu.frequency;

import static org.assertj.core.api.Assertions.assertThat;

import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_01_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_02_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_03_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_04_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_05_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_06_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_07_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_01_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_07_ID;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.adhuc.cena.menu.domain.model.menu.GenerateMenus;
import org.adhuc.cena.menu.domain.model.menu.MealFrequency;

/**
 * The {@link WeekWorkingDaysMealFrequencyIterationGenerator} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("domain")
@DisplayName("Week working days meal frequency iteration generator")
public class WeekWorkingDaysMealFrequencyIterationGeneratorTest {

    private WeekWorkingDaysMealFrequencyIterationGenerator generator;

    @BeforeEach
    public void setUp() {
        generator = new WeekWorkingDaysMealFrequencyIterationGenerator();
    }

    @Test
    @DisplayName("generates menus iterations for 1 working day")
    public void generateIterations1WorkingDay() {
        assertThat(generator.generateIterations(
                new GenerateMenus(1, LocalDate.parse("2017-01-02"), MealFrequency.WEEK_WORKING_DAYS)))
                        .containsExactlyInAnyOrder(DINNER_2017_01_02_ID);
    }

    @Test
    @DisplayName("generates menus iterations for 5 working days")
    public void generateIterations5WorkingDays() {
        assertThat(generator.generateIterations(
                new GenerateMenus(5, LocalDate.parse("2017-01-02"), MealFrequency.WEEK_WORKING_DAYS)))
                        .containsExactlyInAnyOrder(DINNER_2017_01_02_ID, DINNER_2017_01_03_ID, DINNER_2017_01_04_ID,
                                DINNER_2017_01_05_ID, DINNER_2017_01_06_ID);
    }

    @Test
    @DisplayName("generates menus iterations for a week")
    public void generateIterationsWeek() {
        assertThat(generator.generateIterations(
                new GenerateMenus(7, LocalDate.parse("2017-01-01"), MealFrequency.WEEK_WORKING_DAYS)))
                        .containsExactlyInAnyOrder(LUNCH_2017_01_01_ID, DINNER_2017_01_01_ID, DINNER_2017_01_02_ID,
                                DINNER_2017_01_03_ID, DINNER_2017_01_04_ID, DINNER_2017_01_05_ID, DINNER_2017_01_06_ID,
                                LUNCH_2017_01_07_ID, DINNER_2017_01_07_ID);
    }

}
