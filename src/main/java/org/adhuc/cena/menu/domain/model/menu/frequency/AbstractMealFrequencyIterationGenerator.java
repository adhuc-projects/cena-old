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

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.adhuc.cena.menu.domain.model.menu.GenerateMenus;
import org.adhuc.cena.menu.domain.model.menu.MealFrequencyIterationGenerator;
import org.adhuc.cena.menu.domain.model.menu.MenuId;

/**
 * An abstract {@link MealFrequencyIterationGenerator} implementation, iterating over the days to generate menus for,
 * and delegating to concrete implementations for deciding about the iterations to generate, based on the frequency and
 * the day.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public abstract class AbstractMealFrequencyIterationGenerator implements MealFrequencyIterationGenerator {

    @Override
    public Set<MenuId> generateIterations(GenerateMenus command) {
        Set<MenuId> menuIds = new HashSet<>();
        for (int daysIncrement = 0; daysIncrement < command.days(); daysIncrement++) {
            LocalDate date = command.startDate().plusDays(daysIncrement);
            menuIds.addAll(generateIterations(date));
        }
        return menuIds;
    }

    /**
     * Generates the menus iterations for a single date.
     *
     * @param date
     *            the date.
     *
     * @return the menu identities corresponding to the date.
     */
    protected abstract Set<MenuId> generateIterations(LocalDate date);

}
