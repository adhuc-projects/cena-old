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
package org.adhuc.cena.menu.domain.model.ingredient;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * An ingredient definition, consisting of a name.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Data
@Accessors(fluent = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Ingredient {

    @JsonUnwrapped
    private final IngredientId id;
    private String             name;

    /**
     * Creates an ingredient.
     *
     * @param id
     *            the ingredient identity.
     *
     * @param name
     *            the ingredient name.
     */
    public Ingredient(final IngredientId id, final String name) {
        notNull(id, "Cannot create ingredient with invalid identity");
        hasText(name, "Cannot create ingredient with invalid name");
        this.id = id;
        this.name = name;
    }

    /**
     * Sets the ingredient name.
     *
     * @param name
     *            the new ingredient name.
     */
    public void name(String name) {
        hasText(name, "Cannot change name with invalid value");
        this.name = name;
    }

}
