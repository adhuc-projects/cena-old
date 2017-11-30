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

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.adhuc.cena.menu.domain.model.menu.MealType;
import org.adhuc.cena.menu.domain.model.menu.MenuId;

/**
 * A {@link org.adhuc.cena.menu.domain.model.menu.MealFrequencyIterationGenerator MealFrequencyIterationGenerator}
 * implementation for {@link org.adhuc.cena.menu.domain.model.menu.MealFrequency#WEEK_WORKING_DAYS WEEK_WORKING_DAYS}
 * frequency.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public class WeekWorkingDaysMealFrequencyIterationGenerator extends AbstractMealFrequencyIterationGenerator {

    private static Set<DayOfWeek> WEEK_END_DAYS = new HashSet<>(Arrays.asList(SATURDAY, SUNDAY));

    @Override
    protected Set<MenuId> generateIterations(LocalDate date) {
        Set<MenuId> iterations = new HashSet<>();
        if (WEEK_END_DAYS.contains(date.getDayOfWeek())) {
            iterations.add(new MenuId(date, MealType.LUNCH));
        }
        iterations.add(new MenuId(date, MealType.DINNER));
        return iterations;
    }

}
