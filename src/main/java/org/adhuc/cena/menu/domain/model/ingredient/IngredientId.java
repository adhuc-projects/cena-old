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
package org.adhuc.cena.menu.domain.model.ingredient;

import static java.util.Objects.nonNull;

import java.util.UUID;

import org.adhuc.cena.menu.domain.model.Identity;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * An ingredient identity.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class IngredientId extends Identity {

    /**
     * Creates an ingredient identity with the specified value.
     *
     * @param id
     *            the identity value.
     */
    public IngredientId(final String id) {
        this(UUID.fromString(id));
    }

    private IngredientId(final UUID id) {
        super(id);
    }

    /**
     * Generates a new ingredient identity.
     *
     * @return a new ingredient identity.
     */
    public static IngredientId generate() {
        return new IngredientId(UUID.randomUUID());
    }

    /**
     * Indicates whether the specified value is a well formed ingredient identity.
     *
     * @param value
     *            the identity value.
     *
     * @return {@code true} if the value is well formed ingredient identity, {@code false otherwise}.
     */
    public static boolean isWellFormed(final String value) {
        try {
            return nonNull(value) && nonNull(new IngredientId(value));
        } catch (final IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
