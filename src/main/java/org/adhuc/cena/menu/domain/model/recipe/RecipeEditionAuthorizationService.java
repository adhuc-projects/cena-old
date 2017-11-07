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

import static org.springframework.util.Assert.notNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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

    @Autowired
    public RecipeEditionAuthorizationService(RecipeRepository recipeRepository) {
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
    public boolean isAuthor(RecipeId recipeId, UserDetails user) {
        notNull(recipeId, "Cannot validate that user is author of recipe with invalid identity");
        notNull(user, "Cannot validate that invalid user is author of recipe");
        Recipe recipe = recipeRepository.findOneNotNull(recipeId);
        return isAuthor(recipe.author(), user);
    }

    private boolean isAuthor(RecipeAuthor author, UserDetails user) {
        return author.name().equals(user.getUsername());
    }

}
