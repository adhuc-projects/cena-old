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

import static org.springframework.util.Assert.isTrue;

import java.time.LocalDate;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * A menus generation command.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Value
@Accessors(fluent = true)
public class GenerateMenus {

    public static final int     MAX_MENUS_GENERATION_DAYS         = 10;
    private static final String MAX_MENUS_GENERATION_DAYS_MESSAGE =
            "Cannot generate menus for more than " + MAX_MENUS_GENERATION_DAYS + " days";

    private int                 days;
    private LocalDate           startDate;
    private MealFrequency       frequency;

    public GenerateMenus(int days, @NonNull LocalDate startDate, @NonNull MealFrequency frequency) {
        isTrue(days >= 1, "Cannot generate menus for negative days count");
        isTrue(days <= MAX_MENUS_GENERATION_DAYS, MAX_MENUS_GENERATION_DAYS_MESSAGE);
        this.days = days;
        this.startDate = startDate;
        this.frequency = frequency;
    }

}
