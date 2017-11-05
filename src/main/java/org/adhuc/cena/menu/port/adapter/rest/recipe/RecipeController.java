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
package org.adhuc.cena.menu.port.adapter.rest.recipe;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;

import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.adhuc.cena.menu.domain.model.recipe.Recipe;
import org.adhuc.cena.menu.domain.model.recipe.RecipeId;

/**
 * A REST controller exposing /api/recipes/{recipeId} resource.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@RestController
@ExposesResourceFor(Recipe.class)
@RequestMapping(path = "/api/recipes/{recipeId}", produces = HAL_JSON_VALUE)
public class RecipeController {

    /**
     * Gets the recipe information for the recipe corresponding to the specified identity.
     *
     * @param recipeId
     *            the recipe identity.
     *
     * @return the recipe information.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public RecipeResource getRecipe(@PathVariable RecipeId recipeId) {
        throw new UnsupportedOperationException("Get recipe is not implemented yet");
    }

}
