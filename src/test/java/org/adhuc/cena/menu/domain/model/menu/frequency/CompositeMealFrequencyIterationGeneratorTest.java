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

import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_02_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_02_ID;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.adhuc.cena.menu.domain.model.menu.GenerateMenus;
import org.adhuc.cena.menu.domain.model.menu.MealFrequency;

/**
 * The {@link CompositeMealFrequencyIterationGenerator} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("domain")
@DisplayName("Composite meal frequency iteration generator")
public class CompositeMealFrequencyIterationGeneratorTest {

    private CompositeMealFrequencyIterationGenerator generator;

    @BeforeEach
    public void setUp() {
        generator = new CompositeMealFrequencyIterationGenerator();
    }

    @Test
    @DisplayName("generates menus iterations for 1 working day with week working days frequency")
    public void generateIterations1WorkingDayWithWeekWorkingDaysFrequency() {
        assertThat(generator.generateIterations(
                new GenerateMenus(1, LocalDate.parse("2017-01-02"), MealFrequency.WEEK_WORKING_DAYS)))
                        .containsExactlyInAnyOrder(DINNER_2017_01_02_ID);
    }

    @Test
    @DisplayName("generates menus iterations for 1 working day with twice a day frequency")
    public void generateIterations1WorkingDayWithTwiceADayFrequency() {
        assertThat(generator
                .generateIterations(new GenerateMenus(1, LocalDate.parse("2017-01-02"), MealFrequency.TWICE_A_DAY)))
                        .containsExactlyInAnyOrder(LUNCH_2017_01_02_ID, DINNER_2017_01_02_ID);
    }

}
