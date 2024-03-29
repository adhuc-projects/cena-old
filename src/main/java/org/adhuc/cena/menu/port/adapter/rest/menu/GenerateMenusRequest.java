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
package org.adhuc.cena.menu.port.adapter.rest.menu;

import java.time.Clock;
import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.adhuc.cena.menu.domain.model.menu.GenerateMenus;
import org.adhuc.cena.menu.domain.model.menu.MealFrequency;
import org.adhuc.cena.menu.domain.model.menu.MenuOwner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * A request to generate menus.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerateMenusRequest {

    @NotNull
    @Min(1)
    @Max(GenerateMenus.MAX_MENUS_GENERATION_DAYS)
    private Integer       days;
    @NotNull
    private LocalDate     startDate;
    @NotNull
    private MealFrequency frequency;

    /**
     * Converts this request to a {@code GenerateMenus} command.
     *
     * @param clock
     *            the current clock.
     * 
     * @param menuOwner
     *            the menu owner.
     *
     * @return the menus generation command.
     */
    public GenerateMenus toCommand(@NonNull Clock clock, @NonNull MenuOwner menuOwner) {
        return new GenerateMenus(clock, days, startDate, frequency, menuOwner);
    }

}
