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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.adhuc.cena.menu.domain.model.EntityNotFoundException;

import lombok.NonNull;

/**
 * A {@link Menu} repository.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public interface MenuRepository {

    /**
     * Finds all the menus.
     *
     * @return all the menus.
     */
    List<Menu> findAll();

    /**
     * Finds the menus whose date is between the specified dates.
     *
     * @param startDate
     *            the query start date.
     *
     * @param endDate
     *            the query end date.
     *
     * @return the menus whose date is between dates.
     */
    List<Menu> findByDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Finds the menu corresponding to the specified identity.
     *
     * @param menuId
     *            the menu identity.
     *
     * @return the menu if existing, empty otherwise.
     */
    Optional<Menu> findOne(MenuId menuId);

    /**
     * Finds the menu corresponding to the specified identity.
     *
     * @param menuId
     *            the menu identity.
     *
     * @return the menu if existing.
     *
     * @throws EntityNotFoundException
     *             if no menu could be found for identity.
     */
    default Menu findOneNotNull(@NonNull MenuId menuId) {
        Optional<Menu> menu = findOne(menuId);
        if (menu.isPresent()) {
            return menu.get();
        }
        throw new EntityNotFoundException(Menu.class, menuId);
    }

    /**
     * Saves the specified menu.
     *
     * @param menu
     *            the menu to save.
     *
     * @return the saved menu.
     */
    <I extends Menu> I save(I menu);

}
