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

import java.util.Iterator;
import java.util.TreeSet;

import lombok.NonNull;

/**
 * A menus iterations generator based on meal frequency.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public interface MealFrequencyIterationGenerator {

    /**
     * Generates the menus iterations.
     *
     * @param command
     *            the command.
     *
     * @return the menu identities corresponding to the iterations.
     */
    TreeSet<MenuId> generateIterations(GenerateMenus command);

    /**
     * Determines which iteration the specified menu corresponds to.
     *
     * @param menuId
     *            the menu identity.
     *
     * @param command
     *            the command.
     *
     * @return the iteration for the menu.
     */
    default int determineIteration(@NonNull MenuId menuId, @NonNull GenerateMenus command) {
        Iterator<MenuId> iterations = generateIterations(command).iterator();
        int iterationIndex = 1;
        while (iterations.hasNext()) {
            MenuId iteration = iterations.next();
            int comparison = iteration.compareTo(menuId);
            if (comparison > 0) {
                break;
            } else if (comparison < 0) {
                iterationIndex++;
            } else {
                return iterationIndex;
            }
        }
        throw new IllegalArgumentException("Cannot determine iteration for menu " + menuId
                + " that does not correspond to any iteration based on command " + command);
    }

}
