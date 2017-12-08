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
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import org.adhuc.cena.menu.domain.model.ingredient.IngredientId;
import org.adhuc.cena.menu.domain.model.menu.Menu;
import org.adhuc.cena.menu.domain.model.menu.MenuGenerationState;
import org.adhuc.cena.menu.domain.model.menu.MenuId;
import org.adhuc.cena.menu.domain.model.menu.MenuRecipeDefinerStrategy;
import org.adhuc.cena.menu.domain.model.recipe.Recipe;
import org.adhuc.cena.menu.domain.model.recipe.RecipeId;
import org.adhuc.cena.menu.domain.model.recipe.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * A {@link MenuRecipeDefinerStrategy} implementation, getting the next recipe randomly, based on a subset of recipes
 * that do not collide with other menus occurring within the consecutive days.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Slf4j
@Component
public class RandomMenuRecipeDefinerStrategy implements MenuRecipeDefinerStrategy {

    private RecipeRepository recipeRepository;

    @Autowired
    public RandomMenuRecipeDefinerStrategy(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public MenuGenerationState defineRecipeForMenu(MenuId menuId, MenuGenerationState state) {
        List<IngredientId> notUsableIngredients =
                mainIngredientsWithinConsecutiveDaysMenus(consecutiveDaysMenus(menuId, state.menus()));
        log.trace("Ingredients {} are not usable to generate menu {}", notUsableIngredients, menuId);
        List<Recipe> usableRecipes =
                state.filterAlreadyUsedRecipes(recipeRepository.findByMainIngredientsNotIn(notUsableIngredients));
        log.trace("Define recipe for menu {} from recipes {}", menuId, usableRecipes);
        if (CollectionUtils.isEmpty(usableRecipes)) {
            log.debug("No usable recipe for menu {}", menuId);
            throw new IllegalStateException("Cannot generate menu " + menuId + " : there is no recipe");
        }
        return state.addMenu(new Menu(menuId, selectRandomRecipe(usableRecipes)));
    }

    private List<Menu> consecutiveDaysMenus(MenuId menuId, List<Menu> menus) {
        return menus.stream().filter(m -> menuId.isConsecutiveMenu(m.id())).collect(Collectors.toList());
    }

    private List<IngredientId> mainIngredientsWithinConsecutiveDaysMenus(List<Menu> menus) {
        return menus.stream().map(m -> m.recipe()).map(r -> recipeRepository.findOneNotNull(r).ingredients())
                .flatMap(l -> l.stream()).filter(i -> i.isMainIngredient()).map(i -> i.ingredientId())
                .collect(Collectors.toList());
    }

    private RecipeId selectRandomRecipe(List<Recipe> recipes) {
        return recipes.get(new Random().nextInt(recipes.size())).id();
    }

}
