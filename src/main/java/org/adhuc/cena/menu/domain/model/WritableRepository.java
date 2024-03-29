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

/**
 * A writable repository of entities.
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
public interface WritableRepository<E extends Entity<I>, I extends Identity> extends Repository<E, I> {

    /**
     * Saves the specified entity.
     *
     * @param entity
     *            the entity to save.
     *
     * @return the saved entity.
     */
    <S extends E> S save(S entity);

}
