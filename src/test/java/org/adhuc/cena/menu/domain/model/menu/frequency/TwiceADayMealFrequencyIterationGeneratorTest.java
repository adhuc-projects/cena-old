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
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_01_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_02_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_03_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_04_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_05_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_06_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_07_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_01_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_02_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_03_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_04_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_05_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_06_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_07_ID;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.adhuc.cena.menu.domain.model.menu.GenerateMenus;
import org.adhuc.cena.menu.domain.model.menu.MealFrequency;

/**
 * The {@link TwiceADayMealFrequencyIterationGenerator} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("domain")
@DisplayName("Twice a day meal frequency iteration generator")
public class TwiceADayMealFrequencyIterationGeneratorTest {

    private TwiceADayMealFrequencyIterationGenerator generator;

    @BeforeEach
    public void setUp() {
        generator = new TwiceADayMealFrequencyIterationGenerator();
    }

    @Test
    @DisplayName("generates menus iterations for 1 working day")
    public void generateIterations1WorkingDay() {
        assertThat(generator
                .generateIterations(new GenerateMenus(1, LocalDate.parse("2017-01-02"), MealFrequency.TWICE_A_DAY)))
                        .containsExactly(LUNCH_2017_01_02_ID, DINNER_2017_01_02_ID);
    }

    @Test
    @DisplayName("generates menus iterations for 5 working days")
    public void generateIterations5WorkingDays() {
        assertThat(generator
                .generateIterations(new GenerateMenus(5, LocalDate.parse("2017-01-02"), MealFrequency.TWICE_A_DAY)))
                        .containsExactly(LUNCH_2017_01_02_ID, DINNER_2017_01_02_ID, LUNCH_2017_01_03_ID,
                                DINNER_2017_01_03_ID, LUNCH_2017_01_04_ID, DINNER_2017_01_04_ID, LUNCH_2017_01_05_ID,
                                DINNER_2017_01_05_ID, LUNCH_2017_01_06_ID, DINNER_2017_01_06_ID);
    }

    @Test
    @DisplayName("generates menus iterations for a week")
    public void generateIterationsWeek() {
        assertThat(generator
                .generateIterations(new GenerateMenus(7, LocalDate.parse("2017-01-01"), MealFrequency.TWICE_A_DAY)))
                        .containsExactly(LUNCH_2017_01_01_ID, DINNER_2017_01_01_ID, LUNCH_2017_01_02_ID,
                                DINNER_2017_01_02_ID, LUNCH_2017_01_03_ID, DINNER_2017_01_03_ID, LUNCH_2017_01_04_ID,
                                DINNER_2017_01_04_ID, LUNCH_2017_01_05_ID, DINNER_2017_01_05_ID, LUNCH_2017_01_06_ID,
                                DINNER_2017_01_06_ID, LUNCH_2017_01_07_ID, DINNER_2017_01_07_ID);
    }

    @Test
    @DisplayName("determines iteration for menu occurring before iterations")
    public void determineIterationForUnknownMenuBeforeIterations() {
        assertThrows(IllegalArgumentException.class, () -> generator.determineIteration(LUNCH_2017_01_01_ID,
                new GenerateMenus(1, LocalDate.parse("2017-01-02"), MealFrequency.TWICE_A_DAY)));
    }

    @Test
    @DisplayName("determines iteration for menu occurring after iterations")
    public void determineIterationForUnknownMenuAfterIterations() {
        assertThrows(IllegalArgumentException.class, () -> generator.determineIteration(LUNCH_2017_01_03_ID,
                new GenerateMenus(1, LocalDate.parse("2017-01-02"), MealFrequency.TWICE_A_DAY)));
    }

    @Test
    @DisplayName("determines iteration for 1st menu during 1 working day")
    public void determineFirstMenuIteration1WorkingDay() {
        assertThat(generator.determineIteration(LUNCH_2017_01_02_ID,
                new GenerateMenus(1, LocalDate.parse("2017-01-02"), MealFrequency.TWICE_A_DAY))).isEqualTo(1);
    }

    @Test
    @DisplayName("determines iteration for 3rd menu during 5 working days")
    public void determineThirdMenuIteration5WorkingDays() {
        assertThat(generator.determineIteration(LUNCH_2017_01_03_ID,
                new GenerateMenus(5, LocalDate.parse("2017-01-02"), MealFrequency.TWICE_A_DAY))).isEqualTo(3);
    }

    @Test
    @DisplayName("determines iteration for last menu during 5 working days")
    public void determineLastMenuIteration5WorkingDays() {
        assertThat(generator.determineIteration(DINNER_2017_01_06_ID,
                new GenerateMenus(5, LocalDate.parse("2017-01-02"), MealFrequency.TWICE_A_DAY))).isEqualTo(10);
    }

}
