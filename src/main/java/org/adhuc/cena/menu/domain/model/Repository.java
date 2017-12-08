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
package org.adhuc.cena.menu.domain.model;

import java.util.List;
import java.util.Optional;

import lombok.NonNull;

/**
 * A repository of entities.
 *
 * @param <E>
 *            the entity type.
 *
 * @param <I>
 *            the identity type.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public interface Repository<E extends Entity<I>, I extends Identity> {

    /**
     * Gets the entity type this repository manages.
     *
     * @return the entity type.
     */
    Class<E> entityType();

    /**
     * Finds all the entities stored in the repository.
     *
     * @return all the entities.
     */
    List<E> findAll();

    /**
     * Finds the entity corresponding to the specified identity.
     *
     * @param entityId
     *            the entity identity.
     *
     * @return the entity if existing, empty otherwise.
     */
    Optional<E> findOne(I entityId);

    /**
     * Finds the entity corresponding to the specified identity.
     *
     * @param entityId
     *            the entity identity.
     *
     * @return the entity if existing.
     *
     * @throws EntityNotFoundException
     *             if no entity could be found for identity.
     */
    default E findOneNotNull(@NonNull I entityId) {
        Optional<E> entity = findOne(entityId);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new EntityNotFoundException(entityType(), entityId);
    }

}
