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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import org.adhuc.cena.menu.domain.model.BasicEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * A recipe definition, consisting of a name and a content.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Data
@EqualsAndHashCode(callSuper = true, exclude = "name")
@ToString(callSuper = true)
@Accessors(fluent = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Recipe extends BasicEntity<RecipeId> {

    @NonNull
    private String name;
    @NonNull
    private String content;

    /**
     * Creates a recipe.
     *
     * @param id
     *            the recipe identity.
     *
     * @param name
     *            the recipe name.
     *
     * @param content
     *            the recipe content.
     */
    public Recipe(RecipeId id, String name, String content) {
        super(id);
        hasText(name, "Cannot create recipe with invalid name");
        hasText(content, "Cannot create recipe with invalid content");
        this.name = name;
        this.content = content;
    }

    /**
     * Sets the recipe name.
     *
     * @param name
     *            the new recipe name.
     */
    public Recipe name(String name) {
        hasText(name, "Cannot change name with invalid value");
        this.name = name;
        return this;
    }

    /**
     * Sets the recipe content.
     *
     * @param content
     *            the new recipe content.
     */
    public Recipe content(String content) {
        hasText(content, "Cannot change content with invalid value");
        this.content = content;
        return this;
    }

}
