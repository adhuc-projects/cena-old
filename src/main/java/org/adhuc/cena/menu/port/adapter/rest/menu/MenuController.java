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

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;

import java.time.LocalDate;

import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.adhuc.cena.menu.domain.model.menu.MealType;
import org.adhuc.cena.menu.domain.model.recipe.Recipe;

/**
 * A REST controller exposing /api/menus/{date}/{mealType} resource.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@RestController
@ExposesResourceFor(Recipe.class)
@RequestMapping(path = "/api/recipes/{date}/{mealType}", produces = HAL_JSON_VALUE)
public class MenuController {

    /**
     * Gets the menu information for the menu corresponding to the specified date and meal type.
     *
     * @param date
     *            the menu date.
     *
     * @param mealType
     *            the meal type.
     *
     * @return the menu information.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MenuResource getMenu(@PathVariable LocalDate date, MealType mealType) {
        throw new UnsupportedOperationException("Menu detail not implemented yet");
    }

}
