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
package org.adhuc.cena.menu.domain.model.menu.generation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import org.adhuc.cena.menu.domain.model.menu.GenerateMenus;
import org.adhuc.cena.menu.domain.model.menu.MealFrequencyIterationGenerator;
import org.adhuc.cena.menu.domain.model.menu.MenuId;
import org.adhuc.cena.menu.domain.model.menu.MenuRecipeDefinerStrategy;
import org.adhuc.cena.menu.domain.model.recipe.Recipe;
import org.adhuc.cena.menu.domain.model.recipe.RecipeId;
import org.adhuc.cena.menu.domain.model.recipe.RecipeRepository;

/**
 * A {@link MenuRecipeDefinerStrategy} implementation, dumbly getting the next recipe from the recipes list depending on
 * the current menu position.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Component
public class DumbMenuRecipeDefinerStrategy implements MenuRecipeDefinerStrategy {

    private MealFrequencyIterationGenerator mealFrequencyIterationGenerator;
    private RecipeRepository                recipeRepository;

    @Autowired
    public DumbMenuRecipeDefinerStrategy(MealFrequencyIterationGenerator mealFrequencyIterationGenerator,
            RecipeRepository recipeRepository) {
        this.mealFrequencyIterationGenerator = mealFrequencyIterationGenerator;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public RecipeId defineRecipeForMenu(MenuId menuId, GenerateMenus command) {
        int iteration = mealFrequencyIterationGenerator.determineIteration(menuId, command);
        List<Recipe> recipes = recipeRepository.findAll();
        if (CollectionUtils.isEmpty(recipes) || recipes.size() < iteration) {
            throw new IllegalStateException("Cannot generate menu " + menuId
                    + " : there is no recipe left to be used (requiring at least " + iteration + " recipes)");
        }
        return recipes.get(iteration - 1).id();
    }

}
