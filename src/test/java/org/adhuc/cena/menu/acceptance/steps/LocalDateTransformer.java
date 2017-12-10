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
package org.adhuc.cena.menu.acceptance.steps;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import cucumber.api.Transformer;

/**
 * A cucumber {@link Transformer} implementation for {@link LocalDate}s.
 * <p>
 * Inputs must be formatted using {@link java.time.format.DateTimeFormatter#ISO_LOCAL_DATE ISO_LOCAL_DATE}.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public class LocalDateTransformer extends Transformer<LocalDate> {

    private static final String YESTERDAY_KEYWORD = "yesterday";
    private static final String NEXT_DAY_KEYWORD  = "next ";

    @Override
    public LocalDate transform(String value) {
        if (YESTERDAY_KEYWORD.equals(value)) {
            return yesterday();
        }
        if (value.startsWith(NEXT_DAY_KEYWORD)) {
            return nextDay(value);
        }
        return LocalDate.parse(value);
    }

    private LocalDate yesterday() {
        return LocalDate.now().minusDays(1);
    }

    private LocalDate nextDay(String value) {
        DayOfWeek dayOfWeek =
                DayOfWeek.valueOf(value.substring(NEXT_DAY_KEYWORD.length(), value.length()).toUpperCase());
        return LocalDate.now().with(TemporalAdjusters.next(dayOfWeek));
    }

}
