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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_02_DATE;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_02_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_02_MEAL_TYPE;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_01_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_02_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_03_ID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.adhuc.cena.menu.domain.model.EntityNotFoundException;

/**
 * The {@link MenuId} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("domain")
@DisplayName("Menu identity")
public class MenuIdTest {

    @Test
    @DisplayName("cannot be created from null date")
    public void createMenuIdFromNullDate() {
        assertThrows(EntityNotFoundException.class, () -> new MenuId(null, DINNER_2017_01_02_MEAL_TYPE));
    }

    @Test
    @DisplayName("cannot be created from null meal type")
    public void createMenuIdFromNullMealType() {
        assertThrows(EntityNotFoundException.class, () -> new MenuId(DINNER_2017_01_02_DATE, null));
    }

    @Test
    @DisplayName("contains date and meal type values used during construction")
    public void createMenuIdWithDateAndMealType() {
        final MenuId createdId = new MenuId(DINNER_2017_01_02_DATE, DINNER_2017_01_02_MEAL_TYPE);
        assertThat(createdId.date()).isEqualTo(DINNER_2017_01_02_DATE);
        assertThat(createdId.type()).isEqualTo(DINNER_2017_01_02_MEAL_TYPE);
    }

    @Test
    @DisplayName("compares to itself")
    public void compareSameMenuId() {
        assertThat(LUNCH_2017_01_02_ID.compareTo(LUNCH_2017_01_02_ID)).isEqualTo(0);
    }

    @Test
    @DisplayName("compares to menu from the previous day")
    public void compareMenuFromPreviousDay() {
        assertThat(LUNCH_2017_01_02_ID.compareTo(LUNCH_2017_01_01_ID)).isGreaterThan(0);
    }

    @Test
    @DisplayName("compares to menu from the next day")
    public void compareMenuFromNextDay() {
        assertThat(LUNCH_2017_01_02_ID.compareTo(LUNCH_2017_01_03_ID)).isLessThan(0);
    }

    @Test
    @DisplayName("compares to menu from the same day, previous meal")
    public void compareMenuFromSameDayFreviousMeal() {
        assertThat(DINNER_2017_01_02_ID.compareTo(LUNCH_2017_01_02_ID)).isGreaterThan(0);
    }

    @Test
    @DisplayName("compares to menu from the same day, next meal")
    public void compareMenuFromSameDayNextMeal() {
        assertThat(LUNCH_2017_01_02_ID.compareTo(DINNER_2017_01_02_ID)).isLessThan(0);
    }

}
