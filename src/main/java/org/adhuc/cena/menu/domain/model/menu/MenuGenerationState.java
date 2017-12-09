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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.adhuc.cena.menu.domain.model.recipe.Recipe;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * Defines the current state of the menus generation process.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Slf4j
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Accessors(fluent = true)
public class MenuGenerationState {

    private final GenerateMenus command;
    private final List<Menu>    menus;

    /**
     * Creates an initial menus generation state, with no menu generated yet.
     *
     * @param command
     *            the menu generation command.
     */
    public MenuGenerationState(@NonNull GenerateMenus command) {
        this.command = command;
        menus = Collections.emptyList();
    }

    /**
     * Returns the menu corresponding to the specified identity, if any.
     *
     * @param id
     *            the menu identity.
     *
     * @return the menu, or empty.
     */
    public Optional<Menu> menu(@NonNull MenuId id) {
        return menus.stream().filter(m -> m.id().equals(id)).findFirst();
    }

    /**
     * Adds a menu to the menu generation state, and returns a new version of it.
     *
     * @param menu
     *            the menu to add to the menu generation state.
     *
     * @return the new state.
     */
    public MenuGenerationState addMenu(@NonNull Menu menu) {
        log.debug("Add menu {} to menus generated for command {}", menu, command);
        if (containsMenuWithId(menu.id())) {
            throw new IllegalArgumentException("Cannot add menu " + menu + " with already known identity");
        }
        List<Menu> menus = new ArrayList<>(this.menus);
        menus.add(menu);
        return new MenuGenerationState(command, Collections.unmodifiableList(menus));
    }

    /**
     * Filters the specified list from recipes that are already used in generated menus.
     *
     * @param recipes
     *            the recipes list to filter.
     *
     * @return the filtered recipes list.
     */
    public List<Recipe> filterAlreadyUsedRecipes(@NonNull Collection<Recipe> recipes) {
        return recipes.stream().filter(r -> menus.stream().filter(m -> m.recipe().equals(r.id())).count() == 0)
                .collect(Collectors.toList());
    }

    private boolean containsMenuWithId(MenuId menuId) {
        return menus.stream().anyMatch(m -> m.id().equals(menuId));
    }

}
