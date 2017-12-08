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
package org.adhuc.cena.menu.domain.model.recipe;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.NonNull;

/**
 * A service providing convenient methods to validate authorization to edit recipes.
 * <p>
 * Only a recipe author can edit its own recipe.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Service("recipeEditionAuthorizationService")
public class RecipeEditionAuthorizationService {

    private RecipeRepository recipeRepository;

    public RecipeEditionAuthorizationService(@NonNull RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    /**
     * Indicates whether the specified user corresponds to the author of the recipe.
     *
     * @param recipeId
     *            the recipe identity.
     *
     * @param user
     *            the user.
     *
     * @return {@code true} if the user corresponds to the author of the recipe, {@code false} otherwise.
     */
    public boolean isAuthor(@NonNull RecipeId recipeId, @NonNull UserDetails user) {
        Recipe recipe = recipeRepository.findOneNotNull(recipeId);
        return isAuthor(recipe.author(), user);
    }

    private boolean isAuthor(@NonNull RecipeAuthor author, @NonNull UserDetails user) {
        return author.name().equals(user.getUsername());
    }

}
