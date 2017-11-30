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

import java.time.LocalDate;

/**
 * A dateable element.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public interface Dateable {

    /**
     * Gets the element's date.
     *
     * @return the date.
     */
    LocalDate date();

    /**
     * Indicates whether the element's date is between the specified dates. Between corresponds to equal or after lower
     * date, and equal or before upper date.
     *
     * @param lowerDate
     *            the lower limit date.
     *
     * @param upperDate
     *            the upper limit date.
     *
     * @return {@code true} if element's date is between dates.
     */
    default boolean isBetween(LocalDate lowerDate, LocalDate upperDate) {
        return (date().isEqual(lowerDate) || date().isAfter(lowerDate))
                && (date().isEqual(upperDate) || date().isBefore(upperDate));
    }

}
