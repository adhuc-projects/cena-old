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
package org.adhuc.cena.menu.domain.model.recipe.ingredient;

import static org.springframework.util.Assert.notNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.adhuc.cena.menu.domain.model.ingredient.Ingredient;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientRepository;
import org.adhuc.cena.menu.domain.model.recipe.Recipe;
import org.adhuc.cena.menu.domain.model.recipe.RecipeRepository;

/**
 * A domain service responsible for adding ingredients to recipes.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Service
public class RecipeIngredientAdditionService {

    private RecipeRepository     recipeRepository;
    private IngredientRepository ingredientRepository;

    @Autowired
    public RecipeIngredientAdditionService(RecipeRepository recipeRepository,
            IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    /**
     * Adds the ingredient to the recipe.
     *
     * @param command
     *            the ingredient addition to recipe command.
     */
    public void addIngredientToRecipe(AddIngredientToRecipe command) {
        notNull(command, "Cannot add ingredient to recipe from invalid command");
        Recipe recipe = recipeRepository.findOneNotNull(command.recipeId());
        Ingredient ingredient = ingredientRepository.findOneNotNull(command.ingredientId());
        recipe.addIngredient(new RecipeIngredientId(ingredient.id(), command.isMainIngredient()));
        recipeRepository.save(recipe);
    }

}
