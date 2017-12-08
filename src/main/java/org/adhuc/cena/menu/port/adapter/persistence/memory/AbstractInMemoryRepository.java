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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.adhuc.cena.menu.domain.model.Entity;
import org.adhuc.cena.menu.domain.model.Identity;
import org.adhuc.cena.menu.domain.model.WritableRepository;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * An abstract in-memory repository implementation.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Slf4j
@Accessors(fluent = true)
public abstract class AbstractInMemoryRepository<E extends Entity<I>, I extends Identity>
        implements WritableRepository<E, I> {

    @Getter(AccessLevel.PROTECTED)
    private Map<I, E> entities = new HashMap<>();

    @Override
    public List<E> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(entities.values()));
    }

    @Override
    public Optional<E> findOne(@NonNull I entityId) {
        return Optional.ofNullable(entities.get(entityId));
    }

    @Override
    public <S extends E> S save(@NonNull S entity) {
        log.debug("Save entity {}", entity);
        entities.put(entity.id(), entity);
        return entity;
    }

}
