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

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.adhuc.cena.menu.domain.model.menu.GenerateMenus;
import org.adhuc.cena.menu.domain.model.menu.MealFrequence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private MealFrequence frequence;

    /**
     * Converts this request to a {@code GenerateMenus} command.
     *
     * @return the menus generation command.
     */
    public GenerateMenus toCommand() {
        return new GenerateMenus(days, startDate, frequence);
    }

}