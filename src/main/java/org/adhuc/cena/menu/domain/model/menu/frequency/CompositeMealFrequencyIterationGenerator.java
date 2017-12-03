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

import static org.springframework.util.Assert.notNull;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import org.adhuc.cena.menu.domain.model.menu.GenerateMenus;
import org.adhuc.cena.menu.domain.model.menu.MealFrequency;
import org.adhuc.cena.menu.domain.model.menu.MealFrequencyIterationGenerator;
import org.adhuc.cena.menu.domain.model.menu.MenuId;

/**
 * A {@link MealFrequencyIterationGenerator} implementation, composed of a dedicated implementation for each meal
 * frequency.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Component
public class CompositeMealFrequencyIterationGenerator implements MealFrequencyIterationGenerator {

    private Map<MealFrequency, MealFrequencyIterationGenerator> generators;

    public CompositeMealFrequencyIterationGenerator() {
        generators = new HashMap<>();
        generators.put(MealFrequency.WEEK_WORKING_DAYS, new WeekWorkingDaysMealFrequencyIterationGenerator());
        generators.put(MealFrequency.TWICE_A_DAY, new TwiceADayMealFrequencyIterationGenerator());
    }

    @Override
    public TreeSet<MenuId> generateIterations(GenerateMenus command) {
        MealFrequencyIterationGenerator generator = generators.get(command.frequency());
        notNull(generator, "Could not find generator for meal frequency " + command.frequency());
        return generator.generateIterations(command);
    }

}
