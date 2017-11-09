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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import org.adhuc.cena.menu.application.RecipeAppService;
import org.adhuc.cena.menu.domain.model.recipe.CreateRecipe;
import org.adhuc.cena.menu.domain.model.recipe.Recipe;
import org.adhuc.cena.menu.domain.model.recipe.RecipeId;
import org.adhuc.cena.menu.domain.model.recipe.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * A {@link RecipeAppService} implementation.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Slf4j
@Service
public class RecipeAppServiceImpl implements RecipeAppService {

    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeAppServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> getRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipe(RecipeId recipeId) {
        notNull(recipeId, "Cannot get recipe from invalid identity");
        return recipeRepository.findOneNotNull(recipeId);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public void createRecipe(CreateRecipe command) {
        notNull(command, "Cannot create recipe from invalid command");
        log.info("Create recipe from command {}", command);
        recipeRepository.save(
                new Recipe(command.recipeId(), command.recipeName(), command.recipeContent(), command.recipeAuthor()));
    }

}
