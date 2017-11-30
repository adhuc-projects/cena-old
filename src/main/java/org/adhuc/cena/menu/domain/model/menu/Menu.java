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
package org.adhuc.cena.menu.domain.model.menu;

import static org.springframework.util.Assert.notNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.adhuc.cena.menu.domain.model.BasicEntity;
import org.adhuc.cena.menu.domain.model.recipe.RecipeId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * A menu definition, consisting of the different dishes served during a meal.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Data
@EqualsAndHashCode(callSuper = true, exclude = { "recipe" })
@ToString(callSuper = true)
@Accessors(fluent = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Menu extends BasicEntity<MenuId> {

    @NonNull
    @JsonIgnore
    private RecipeId recipe;

    /**
     * Creates a menu.
     *
     * @param id
     *            the menu identity.
     *
     * @param recipe
     *            the recipe served during the meal.
     */
    public Menu(MenuId id, RecipeId recipe) {
        super(id);
        notNull(recipe, "Cannot create menu without recipe");
        this.recipe = recipe;
    }

}
