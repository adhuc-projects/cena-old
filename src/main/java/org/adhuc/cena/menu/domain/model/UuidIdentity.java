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

import static org.springframework.util.Assert.notNull;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

/**
 * An {@link Identity} implementation, based on UUID.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(fluent = true)
public abstract class UuidIdentity implements Identity {

    @NonNull
    private final UUID id;

    @Override
    @JsonValue
    public String toString() {
        return id.toString();
    }

    /**
     * Parse the identity value to an UUID value.
     *
     * @param entityType
     *            the entity type.
     *
     * @param value
     *            the identity value.
     *
     * @return the UUID identity value.
     */
    protected static UUID parseUUID(Class<? extends Entity<?>> entityType, String value) {
        try {
            notNull(value, "Cannot parse identity from null value");
            return UUID.fromString(value);
        } catch (final IllegalArgumentException e) {
            throw new EntityNotFoundException(entityType, value);
        }
    }

}
