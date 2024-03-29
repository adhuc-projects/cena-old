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

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_01_DATE;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_01_OWNER;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The {@link MenusQuery} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("domain")
@DisplayName("Menus query")
public class MenusQueryTest {

    @Test
    @DisplayName("cannot be created with negative days")
    public void createWithNegativeDays() {
        assertThrows(IllegalArgumentException.class,
                () -> new MenusQuery(-1, DINNER_2017_01_01_DATE, DINNER_2017_01_01_OWNER));
    }

    @Test
    @DisplayName("cannot be created with 0 days")
    public void createWithZeroDays() {
        assertThrows(IllegalArgumentException.class,
                () -> new MenusQuery(0, DINNER_2017_01_01_DATE, DINNER_2017_01_01_OWNER));
    }

    @Test
    @DisplayName("cannot be created with null start date")
    public void createWithNullStartDate() {
        assertThrows(IllegalArgumentException.class, () -> new MenusQuery(1, null, DINNER_2017_01_01_OWNER));
    }

    @Test
    @DisplayName("cannot be created with null menu owner")
    public void createWithNullMenuOwner() {
        assertThrows(IllegalArgumentException.class, () -> new MenusQuery(1, DINNER_2017_01_01_DATE, null));
    }

    @Test
    @DisplayName("contains days and start date")
    public void createOK() {
        MenusQuery query = new MenusQuery(3, DINNER_2017_01_01_DATE, DINNER_2017_01_01_OWNER);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(query.days()).isEqualTo(3);
            softly.assertThat(query.startDate()).isEqualTo(DINNER_2017_01_01_DATE);
            softly.assertThat(query.owner()).isEqualTo(DINNER_2017_01_01_OWNER);
        });
    }

}
