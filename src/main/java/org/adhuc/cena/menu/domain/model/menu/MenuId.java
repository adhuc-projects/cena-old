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

import java.time.Duration;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import org.adhuc.cena.menu.domain.model.Dateable;
import org.adhuc.cena.menu.domain.model.EntityNotFoundException;
import org.adhuc.cena.menu.domain.model.Identity;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * A menu identity definition, consisting of the menu date and the meal type.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Value
@Accessors(fluent = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class MenuId implements Identity, Dateable, Comparable<MenuId> {

    private final LocalDate date;
    private final MealType  type;

    /**
     * Creates a menu identity based on the specified date and meal type.
     *
     * @param date
     *            the menu date.
     *
     * @param type
     *            the meal type.
     */
    public MenuId(LocalDate date, MealType type) {
        notNull(date, "date");
        notNull(type, "meal type");
        this.date = date;
        this.type = type;
    }

    @Override
    public int compareTo(@NonNull MenuId o) {
        int compareDate = date.compareTo(o.date);
        return compareDate == 0 ? type.compareTo(o.type) : compareDate;
    }

    /**
     * Indicates whether the specified menu is within a consecutive day. A menu is considered as consecutive if its date
     * is the same, or a day before or after this menu date.
     *
     * @param other
     *            the other menu identity.
     *
     * @return {@code true} if the other menu is within a consecutive day, {@code false} otherwise.
     */
    public boolean isConsecutiveMenu(@NonNull MenuId other) {
        return Duration.between(date.atStartOfDay(), other.date.atStartOfDay()).abs().toDays() <= 1;
    }

    private void notNull(Object value, String name) {
        if (value == null) {
            throw new EntityNotFoundException(Menu.class, "with invalid " + name);
        }
    }

}
