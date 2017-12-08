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

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import org.adhuc.cena.menu.domain.model.menu.Menu;
import org.adhuc.cena.menu.domain.model.menu.MenuId;
import org.adhuc.cena.menu.domain.model.menu.MenuRepository;

import lombok.NonNull;

/**
 * An in-memory {@link MenuRepository} implementation.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Repository
@Profile("in-memory")
public class InMemoryMenuRepository extends AbstractInMemoryRepository<Menu, MenuId> implements MenuRepository {

    @Override
    public Class<Menu> entityType() {
        return Menu.class;
    }

    @Override
    public List<Menu> findByDateBetween(@NonNull LocalDate startDate, @NonNull LocalDate endDate) {
        return entities().values().stream().filter(m -> m.id().isBetween(startDate, endDate))
                .collect(Collectors.toList());
    }

}
