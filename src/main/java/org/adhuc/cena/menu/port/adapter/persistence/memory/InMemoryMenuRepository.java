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

import static org.springframework.util.Assert.notNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import org.adhuc.cena.menu.domain.model.menu.Menu;
import org.adhuc.cena.menu.domain.model.menu.MenuId;
import org.adhuc.cena.menu.domain.model.menu.MenuRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * An in-memory {@link MenuRepository} implementation.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Slf4j
@Repository
@Profile("in-memory")
public class InMemoryMenuRepository implements MenuRepository {

    private Map<MenuId, Menu> menus = new HashMap<>();

    @Override
    public List<Menu> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(menus.values()));
    }

    @Override
    public List<Menu> findByDateBetween(LocalDate startDate, LocalDate endDate) {
        return menus.values().stream().filter(m -> m.id().isBetween(startDate, endDate)).collect(Collectors.toList());
    }

    @Override
    public Optional<Menu> findOne(MenuId menuId) {
        return Optional.ofNullable(menus.get(menuId));
    }

    @Override
    public <I extends Menu> I save(I menu) {
        notNull(menu, "Cannot save null menu");
        log.debug("Save menu {}", menu);
        menus.put(menu.id(), menu);
        return menu;
    }

}
