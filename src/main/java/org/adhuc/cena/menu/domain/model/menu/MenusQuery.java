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

import org.adhuc.cena.menu.domain.model.DateInterval;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * A query to get menus.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Value
@Accessors(fluent = true)
public class MenusQuery {

    private final int       days;
    private final LocalDate startDate;
    private final MenuOwner owner;

    public MenusQuery(int days, @NonNull LocalDate startDate, @NonNull MenuOwner owner) {
        isTrue(days > 0, "Cannot get menus with negative days value");
        this.days = days;
        this.startDate = startDate;
        this.owner = owner;
    }

    /**
     * Gets the interval starting from start date and finishing to (start date + days - 1).
     *
     * @return the interval corresponding to query.
     */
    public DateInterval interval() {
        return new DateInterval(startDate, startDate.plusDays(days - 1));
    }

}
