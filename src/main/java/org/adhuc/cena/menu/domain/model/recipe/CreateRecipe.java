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

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A recipe creation command.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Data
@Accessors(fluent = true)
public class CreateRecipe {

    private final RecipeId recipeId;
    private final String   recipeName;
    private final String   recipeContent;

    /**
     * Creates a recipe creation command.
     *
     * @param recipeId
     *            the recipe identity.
     *
     * @param recipeName
     *            the recipe name.
     * 
     * @param recipeContent
     *            the recipe content.
     */
    public CreateRecipe(final RecipeId recipeId, final String recipeName, final String recipeContent) {
        notNull(recipeId, "Cannot create recipe creation command with invalid recipe identity");
        hasText(recipeName, "Cannot create recipe creation command with invalid recipe name");
        hasText(recipeContent, "Cannot create recipe creation command with invalid recipe content");
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipeContent = recipeContent;
    }

}