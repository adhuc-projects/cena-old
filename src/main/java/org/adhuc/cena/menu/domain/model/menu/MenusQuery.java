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
import static org.springframework.util.Assert.notNull;

import java.time.LocalDate;

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

    public MenusQuery(int days, LocalDate startDate) {
        isTrue(days > 0, "Cannot get menus with negative days value");
        notNull(startDate, "Cannot get menus with invalid start date");
        this.days = days;
        this.startDate = startDate;
    }

}