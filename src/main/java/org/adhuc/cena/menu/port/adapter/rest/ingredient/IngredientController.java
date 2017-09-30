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
package org.adhuc.cena.menu.port.adapter.rest.ingredient;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.adhuc.cena.menu.model.ingredient.Ingredient;

/**
 * A REST controller exposing /api/ingredients/{ingredientId} resource.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@RestController
@RequestMapping(path = "/api/ingredients/{ingredientId}", produces = APPLICATION_JSON_VALUE)
public class IngredientController {

    /**
     * Gets the ingredient information for the ingredient corresponding to the specified identity.
     *
     * @param ingredientId
     *            the ingredient identity.
     *
     * @return the ingredient information.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Ingredient getIngredient(@PathVariable String ingredientId) {
        throw new UnsupportedOperationException("Cannot get ingredient " + ingredientId + " information");
    }

}
