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

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.adhuc.cena.menu.port.adapter.rest.ingredient.IngredientResource;

/**
 * A REST controller exposing /api/recipes/{recipeId}/ingredients resource.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@RestController
@RequestMapping(path = "/api/recipes/{recipeId}/ingredients", produces = HAL_JSON_VALUE)
public class RecipeIngredientsController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<IngredientResource> getRecipeIngredients(@PathVariable String recipeId) {
        // TODO implement recipe's ingredients list
        return Collections.emptyList();
    }

    @PutMapping("/{ingredientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addIngredientToRecipe(@PathVariable String recipeId, @PathVariable String ingredientId) {
        // TODO implement adding ingredient to recipe
    }

}
