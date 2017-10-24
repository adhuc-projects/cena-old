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

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

/**
 * An abstract entity definition, containing the entity identity.
 *
 * @param <ID>
 *            the identity type.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Data
@Accessors(fluent = true)
public abstract class BasicEntity<ID> implements Entity<ID> {

    @NonNull
    @JsonUnwrapped
    private final ID id;

}