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
package org.adhuc.cena.menu.port.adapter.rest.recipe;

import org.hibernate.validator.constraints.NotBlank;

import org.adhuc.cena.menu.domain.model.recipe.CreateRecipe;
import org.adhuc.cena.menu.domain.model.recipe.RecipeAuthor;
import org.adhuc.cena.menu.domain.model.recipe.RecipeId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * A request to create a recipe
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Data
@AllArgsConstructor
@Builder
public class CreateRecipeRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String content;

    /**
     * Converts this request to a {@code CreateRecipe} command, with specified identity.
     *
     * @param identity
     *            the recipe identity.
     *
     * @param author
     *            the recipe author.
     *
     * @return the recipe creation command.
     */
    public CreateRecipe toCommand(@NonNull RecipeId identity, @NonNull RecipeAuthor author) {
        return new CreateRecipe(identity, name, content, author);
    }

}
