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
package org.adhuc.cena.menu.application.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import org.adhuc.cena.menu.application.RecipeIngredientAppService;
import org.adhuc.cena.menu.domain.model.ingredient.Ingredient;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientId;
import org.adhuc.cena.menu.domain.model.recipe.AddIngredientToRecipe;
import org.adhuc.cena.menu.domain.model.recipe.RecipeId;

/**
 * A {@link RecipeIngredientAppService} implementation.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Service
public class RecipeIngredientAppServiceImpl implements RecipeIngredientAppService {

    @Override
    public List<Ingredient> getRecipeIngredients(RecipeId recipeId) {
        // TODO implement getRecipeIngredients
        return Arrays.asList(new Ingredient(IngredientId.generate(), "Tomato"),
                new Ingredient(IngredientId.generate(), "Cucumber"));
    }

    @Override
    @PreAuthorize("isAuthenticated() && @recipeEditionAuthorizationService.isAuthor(#command.recipeId(), principal)")
    public void addIngredientToRecipe(AddIngredientToRecipe command) {
        // TODO implement addIngredientToRecipe
    }

}
