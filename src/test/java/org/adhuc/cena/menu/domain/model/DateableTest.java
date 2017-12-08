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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The {@link Dateable} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@DisplayName("Dateable whose date is 2017-01-02")
public class DateableTest {

    private Dateable dateable = new Dateable() {

        @Override
        public LocalDate date() {
            return LocalDate.parse("2017-01-02");
        }

    };

    @Test
    @DisplayName("throws IllegalArgumentException when calling isBetween with null before day")
    public void isBetweenNullDayBefore() {
        assertThrows(IllegalArgumentException.class, () -> dateable.isBetween(null, LocalDate.parse("2017-01-03")));
    }

    @Test
    @DisplayName("throws IllegalArgumentException when calling isBetween with null after day")
    public void isBetweenNullDayAfter() {
        assertThrows(IllegalArgumentException.class, () -> dateable.isBetween(LocalDate.parse("2017-01-01"), null));
    }

    @Test
    @DisplayName("is between 2017-01-01 and 2017-01-03")
    public void isBetweenDayBeforeAndDayAfter() {
        assertThat(dateable.isBetween(LocalDate.parse("2017-01-01"), LocalDate.parse("2017-01-03"))).isTrue();
    }

    @Test
    @DisplayName("is between 2017-01-01 and 2017-01-02")
    public void isBetweenDayBeforeAndSameDay() {
        assertThat(dateable.isBetween(LocalDate.parse("2017-01-01"), LocalDate.parse("2017-01-02"))).isTrue();
    }

    @Test
    @DisplayName("is between 2017-01-02 and 2017-01-03")
    public void isBetweenSameDayAndDayAfter() {
        assertThat(dateable.isBetween(LocalDate.parse("2017-01-02"), LocalDate.parse("2017-01-03"))).isTrue();
    }

    @Test
    @DisplayName("is between 2017-01-02 and 2017-01-02")
    public void isBetweenSameDays() {
        assertThat(dateable.isBetween(LocalDate.parse("2017-01-02"), LocalDate.parse("2017-01-02"))).isTrue();
    }

    @Test
    @DisplayName("is not between 2016-12-31 and 2017-01-01")
    public void isNotBetweenDaysBefore() {
        assertThat(dateable.isBetween(LocalDate.parse("2016-12-31"), LocalDate.parse("2017-01-01"))).isFalse();
    }

    @Test
    @DisplayName("is between 2017-01-03 and 2017-01-04")
    public void isNotBetweenDaysAfter() {
        assertThat(dateable.isBetween(LocalDate.parse("2017-01-03"), LocalDate.parse("2017-01-04"))).isFalse();
    }

    @Test
    @DisplayName("is between 2017-01-03 and 2017-01-01")
    public void isNotBetweenInversedDayBeforeAndDayAfter() {
        assertThat(dateable.isBetween(LocalDate.parse("2017-01-03"), LocalDate.parse("2017-01-01"))).isFalse();
    }

}
