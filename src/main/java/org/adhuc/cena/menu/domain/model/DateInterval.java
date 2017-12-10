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
package org.adhuc.cena.menu.domain.model;

import static org.springframework.util.Assert.isTrue;

import java.time.LocalDate;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Defines a date interval, between lower and upper boundaries inclusively.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Value
@Accessors(fluent = true)
public class DateInterval {

    private final LocalDate lower;
    private final LocalDate upper;

    public DateInterval(@NonNull LocalDate lower, @NonNull LocalDate upper) {
        isTrue(!lower.isAfter(upper), "Cannot create interval with lower boudary after upper boundary");
        this.lower = lower;
        this.upper = upper;
    }

    /**
     * Indicates whether the specified date is between the interval boundaries inclusively.
     *
     * @param date
     *            the date.
     *
     * @return {@code true} if date is in interval.
     */
    public boolean contains(@NonNull LocalDate date) {
        return (date.isEqual(lower) || date.isAfter(lower)) && (date.isEqual(upper) || date.isBefore(upper));
    }

}
