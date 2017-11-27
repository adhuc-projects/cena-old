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

import java.time.LocalDate;

/**
 * An object mother to create testing domain elements related to {@link Menu}s.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 *
 * @see https://www.martinfowler.com/bliki/ObjectMother.html
 */
public class MenuMother {

    public static final int           MENU_2017_01_02_DAYS        = 1;
    public static final LocalDate     MENU_2017_01_02_START_DATE  = LocalDate.parse("2017-01-02");
    public static final MealFrequence MENU_2017_01_02_FREQUENCE   = MealFrequence.WEEK_WORKING_DAYS;

    public static final int           MENU_2017_01_01_DAYS        = 10;
    public static final LocalDate     MENU_2017_01_01_START_DATE  = LocalDate.parse("2017-01-01");
    public static final MealFrequence MENU_2017_01_01_FREQUENCE   = MealFrequence.TWICE_A_DAY;

    public static final LocalDate     DINNER_2017_01_02_DATE      = LocalDate.parse("2017-01-02");
    public static final MealType      DINNER_2017_01_02_MEAL_TYPE = MealType.DINNER;
    public static final MenuId        DINNER_2017_01_02           =
            new MenuId(DINNER_2017_01_02_DATE, DINNER_2017_01_02_MEAL_TYPE);

    public static GenerateMenus generateMenus1DayAt20170102WeekWorkingDays() {
        return new GenerateMenus(MENU_2017_01_02_DAYS, MENU_2017_01_02_START_DATE, MENU_2017_01_02_FREQUENCE);
    }

    public static GenerateMenus generateMenus10DaysAt20170101TwiceADay() {
        return new GenerateMenus(MENU_2017_01_01_DAYS, MENU_2017_01_01_START_DATE, MENU_2017_01_01_FREQUENCE);
    }

}
