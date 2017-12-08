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
package org.adhuc.cena.menu.port.adapter.persistence.memory;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import org.adhuc.cena.menu.domain.model.ingredient.IngredientId;
import org.adhuc.cena.menu.domain.model.recipe.Recipe;
import org.adhuc.cena.menu.domain.model.recipe.RecipeId;
import org.adhuc.cena.menu.domain.model.recipe.RecipeRepository;

import lombok.NonNull;

/**
 * An in-memory {@link RecipeRepository} implementation.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Repository
@Profile("in-memory")
public class InMemoryRecipeRepository extends AbstractInMemoryRepository<Recipe, RecipeId> implements RecipeRepository {

    @Override
    public Class<Recipe> entityType() {
        return Recipe.class;
    }

    @Override
    public List<Recipe> findByMainIngredientsNotIn(@NonNull Collection<IngredientId> ingredientIds) {
        return entities().values().stream()
                .filter(r -> CollectionUtils.intersection(r.ingredients().stream().filter(i -> i.isMainIngredient())
                        .map(i -> i.ingredientId()).collect(Collectors.toList()), ingredientIds).isEmpty())
                .collect(Collectors.toList());
    }

}
