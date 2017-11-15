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

import static org.springframework.util.Assert.notNull;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import org.adhuc.cena.menu.application.RecipeIngredientAppService;
import org.adhuc.cena.menu.domain.model.ingredient.Ingredient;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientId;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientRepository;
import org.adhuc.cena.menu.domain.model.recipe.Recipe;
import org.adhuc.cena.menu.domain.model.recipe.RecipeId;
import org.adhuc.cena.menu.domain.model.recipe.RecipeRepository;
import org.adhuc.cena.menu.domain.model.recipe.ingredient.AddIngredientToRecipe;
import org.adhuc.cena.menu.domain.model.recipe.ingredient.RecipeIngredient;
import org.adhuc.cena.menu.domain.model.recipe.ingredient.RecipeIngredientAdditionService;

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

    private RecipeIngredientAdditionService recipeIngredientAdditionService;
    private RecipeRepository                recipeRepository;
    private IngredientRepository            ingredientRepository;

    @Autowired
    public RecipeIngredientAppServiceImpl(RecipeIngredientAdditionService recipeIngredientAdditionService,
            RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
        this.recipeIngredientAdditionService = recipeIngredientAdditionService;
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<RecipeIngredient> getRecipeIngredients(RecipeId recipeId) {
        Recipe recipe = recipeRepository.findOneNotNull(recipeId);
        List<IngredientId> ingredientIds =
                recipe.ingredients().stream().map(i -> i.ingredientId()).collect(Collectors.toList());
        Map<IngredientId, Ingredient> ingredients = ingredientRepository.findAll(ingredientIds).stream()
                .collect(Collectors.toMap(Ingredient::id, Function.identity()));
        return recipe.ingredients().stream()
                .map(i -> new RecipeIngredient(recipe.id(), i, ingredients.get(i.ingredientId())))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("isAuthenticated() && @recipeEditionAuthorizationService.isAuthor(#command.recipeId(), principal)")
    public void addIngredientToRecipe(AddIngredientToRecipe command) {
        notNull(command, "Cannot add ingredient to recipe from invalid command");
        recipeIngredientAdditionService.addIngredientToRecipe(command);
    }

}
