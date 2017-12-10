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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The {@link DateInterval} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("domain")
@DisplayName("Date interval between 2017-01-01 and 2017-01-03")
public class DateIntervalTest {

    private DateInterval interval = new DateInterval(LocalDate.parse("2017-01-01"), LocalDate.parse("2017-01-03"));

    @Test
    @DisplayName("throws IllegalArgumentException when creating with null lower date")
    public void createNullLowerDate() {
        assertThrows(IllegalArgumentException.class, () -> new DateInterval(null, LocalDate.parse("2017-01-03")));
    }

    @Test
    @DisplayName("throws IllegalArgumentException when creating with null upper date")
    public void createNullUpperDate() {
        assertThrows(IllegalArgumentException.class, () -> new DateInterval(LocalDate.parse("2017-01-01"), null));
    }

    @Test
    @DisplayName("throws IllegalArgumentException when creating with lower date after upper date")
    public void createLowerDateAfterUpperDate() {
        assertThrows(IllegalArgumentException.class,
                () -> new DateInterval(LocalDate.parse("2017-01-03"), LocalDate.parse("2017-01-01")));
    }

    @Test
    @DisplayName("throws IllegalArgumentException when calling contains with null date")
    public void containsNullDate() {
        assertThrows(IllegalArgumentException.class, () -> interval.contains(null));
    }

    @Test
    @DisplayName("contains 2017-01-02")
    public void containsDateBetween() {
        assertThat(interval.contains(LocalDate.parse("2017-01-02"))).isTrue();
    }

    @Test
    @DisplayName("contains 2017-01-01")
    public void containsSameDateAsLower() {
        assertThat(interval.contains(LocalDate.parse("2017-01-01"))).isTrue();
    }

    @Test
    @DisplayName("contains 2017-01-03")
    public void containsSameDateAsUpper() {
        assertThat(interval.contains(LocalDate.parse("2017-01-03"))).isTrue();
    }

    @Test
    @DisplayName("does not contain 2016-12-31")
    public void doesNotContainDateBefore() {
        assertThat(interval.contains(LocalDate.parse("2016-12-31"))).isFalse();
    }

    @Test
    @DisplayName("does not contain 2017-01-04")
    public void doesNotContainDateAfter() {
        assertThat(interval.contains(LocalDate.parse("2017-01-04"))).isFalse();
    }

}
