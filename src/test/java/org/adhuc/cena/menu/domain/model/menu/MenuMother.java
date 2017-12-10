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

import static org.adhuc.cena.menu.support.ClockProvider.CLOCK;
import static org.adhuc.cena.menu.support.UserProvider.ANOTHER_USER;
import static org.adhuc.cena.menu.support.UserProvider.AUTHENTICATED_USER;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.adhuc.cena.menu.domain.model.recipe.RecipeId;
import org.adhuc.cena.menu.domain.model.recipe.RecipeMother;

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

    public static final int           MENU_2017_01_02_DAYS              = 1;
    public static final LocalDate     MENU_2017_01_02_START_DATE        = LocalDate.parse("2017-01-02");
    public static final MealFrequency MENU_2017_01_02_FREQUENCY         = MealFrequency.WEEK_WORKING_DAYS;
    public static final MenuOwner     MENU_2017_01_02_OWNER             = new MenuOwner(AUTHENTICATED_USER);

    public static final int           MENU_2017_01_03_DAYS              = 2;
    public static final LocalDate     MENU_2017_01_03_START_DATE        = LocalDate.parse("2017-01-03");
    public static final MealFrequency MENU_2017_01_03_FREQUENCY         = MealFrequency.TWICE_A_DAY;
    public static final MenuOwner     MENU_2017_01_03_OWNER             = new MenuOwner(AUTHENTICATED_USER);

    public static final int           MENU_2017_01_01_DAYS              = 7;
    public static final LocalDate     MENU_2017_01_01_START_DATE        = LocalDate.parse("2017-01-01");
    public static final MealFrequency MENU_2017_01_01_FREQUENCY         = MealFrequency.TWICE_A_DAY;
    public static final MenuOwner     MENU_2017_01_01_OWNER             = new MenuOwner(AUTHENTICATED_USER);

    public static final LocalDate     LUNCH_2017_01_01_DATE             = LocalDate.parse("2017-01-01");
    public static final MealType      LUNCH_2017_01_01_MEAL_TYPE        = MealType.LUNCH;
    public static final MenuOwner     LUNCH_2017_01_01_OWNER            = new MenuOwner(AUTHENTICATED_USER);
    public static final MenuId        LUNCH_2017_01_01_ID               =
            new MenuId(LUNCH_2017_01_01_DATE, LUNCH_2017_01_01_MEAL_TYPE, LUNCH_2017_01_01_OWNER);
    public static final RecipeId      LUNCH_2017_01_01_RECIPE           =
            RecipeMother.DUCK_BREAST_FILLET_WITH_TURNIPS_ID;

    public static final LocalDate     DINNER_2017_01_01_DATE            = LocalDate.parse("2017-01-01");
    public static final MealType      DINNER_2017_01_01_MEAL_TYPE       = MealType.DINNER;
    public static final MenuOwner     DINNER_2017_01_01_OWNER           = new MenuOwner(AUTHENTICATED_USER);
    public static final MenuId        DINNER_2017_01_01_ID              =
            new MenuId(DINNER_2017_01_01_DATE, DINNER_2017_01_01_MEAL_TYPE, DINNER_2017_01_01_OWNER);
    public static final RecipeId      DINNER_2017_01_01_RECIPE          = RecipeMother.OMELETTE_ID;

    public static final LocalDate     LUNCH_2017_01_02_DATE             = LocalDate.parse("2017-01-02");
    public static final MealType      LUNCH_2017_01_02_MEAL_TYPE        = MealType.LUNCH;
    public static final MenuOwner     LUNCH_2017_01_02_OWNER            = new MenuOwner(AUTHENTICATED_USER);
    public static final MenuId        LUNCH_2017_01_02_ID               =
            new MenuId(LUNCH_2017_01_02_DATE, LUNCH_2017_01_02_MEAL_TYPE, LUNCH_2017_01_02_OWNER);
    public static final RecipeId      LUNCH_2017_01_02_RECIPE           = RecipeMother.LASAGNE_ID;

    public static final LocalDate     DINNER_2017_01_02_DATE            = LocalDate.parse("2017-01-02");
    public static final MealType      DINNER_2017_01_02_MEAL_TYPE       = MealType.DINNER;
    public static final MenuOwner     DINNER_2017_01_02_OWNER           = new MenuOwner(AUTHENTICATED_USER);
    public static final MenuId        DINNER_2017_01_02_ID              =
            new MenuId(DINNER_2017_01_02_DATE, DINNER_2017_01_02_MEAL_TYPE, DINNER_2017_01_02_OWNER);
    public static final RecipeId      DINNER_2017_01_02_RECIPE          = RecipeMother.TOMATO_CANTAL_PIE_ID;

    public static final LocalDate     LUNCH_2017_01_03_DATE             = LocalDate.parse("2017-01-03");
    public static final MealType      LUNCH_2017_01_03_MEAL_TYPE        = MealType.LUNCH;
    public static final MenuOwner     LUNCH_2017_01_03_OWNER            = new MenuOwner(AUTHENTICATED_USER);
    public static final MenuId        LUNCH_2017_01_03_ID               =
            new MenuId(LUNCH_2017_01_03_DATE, LUNCH_2017_01_03_MEAL_TYPE, LUNCH_2017_01_03_OWNER);
    public static final RecipeId      LUNCH_2017_01_03_RECIPE           = RecipeMother.RACLETTE_ID;

    public static final LocalDate     DINNER_2017_01_03_DATE            = LocalDate.parse("2017-01-03");
    public static final MealType      DINNER_2017_01_03_MEAL_TYPE       = MealType.DINNER;
    public static final MenuOwner     DINNER_2017_01_03_OWNER           = new MenuOwner(AUTHENTICATED_USER);
    public static final MenuId        DINNER_2017_01_03_ID              =
            new MenuId(DINNER_2017_01_03_DATE, DINNER_2017_01_03_MEAL_TYPE, DINNER_2017_01_03_OWNER);
    public static final RecipeId      DINNER_2017_01_03_RECIPE          = RecipeMother.CROQUE_MONSIEUR_ID;

    public static final LocalDate     LUNCH_2017_01_04_DATE             = LocalDate.parse("2017-01-04");
    public static final MealType      LUNCH_2017_01_04_MEAL_TYPE        = MealType.LUNCH;
    public static final MenuOwner     LUNCH_2017_01_04_OWNER            = new MenuOwner(AUTHENTICATED_USER);
    public static final MenuId        LUNCH_2017_01_04_ID               =
            new MenuId(LUNCH_2017_01_04_DATE, LUNCH_2017_01_04_MEAL_TYPE, LUNCH_2017_01_04_OWNER);
    public static final RecipeId      LUNCH_2017_01_04_RECIPE           = RecipeMother.CHILI_CON_CARNE_ID;

    public static final LocalDate     DINNER_2017_01_04_DATE            = LocalDate.parse("2017-01-04");
    public static final MealType      DINNER_2017_01_04_MEAL_TYPE       = MealType.DINNER;
    public static final MenuOwner     DINNER_2017_01_04_OWNER           = new MenuOwner(AUTHENTICATED_USER);
    public static final MenuId        DINNER_2017_01_04_ID              =
            new MenuId(DINNER_2017_01_04_DATE, DINNER_2017_01_04_MEAL_TYPE, DINNER_2017_01_04_OWNER);
    public static final RecipeId      DINNER_2017_01_04_RECIPE          = RecipeMother.POACHED_EGGS_SALAD_ID;

    public static final LocalDate     LUNCH_2017_01_05_DATE             = LocalDate.parse("2017-01-05");
    public static final MealType      LUNCH_2017_01_05_MEAL_TYPE        = MealType.LUNCH;
    public static final MenuOwner     LUNCH_2017_01_05_OWNER            = new MenuOwner(AUTHENTICATED_USER);
    public static final MenuId        LUNCH_2017_01_05_ID               =
            new MenuId(LUNCH_2017_01_05_DATE, LUNCH_2017_01_05_MEAL_TYPE, LUNCH_2017_01_05_OWNER);
    public static final RecipeId      LUNCH_2017_01_05_RECIPE           = RecipeMother.DUCK_PARMENTIER_ID;

    public static final LocalDate     DINNER_2017_01_05_DATE            = LocalDate.parse("2017-01-05");
    public static final MealType      DINNER_2017_01_05_MEAL_TYPE       = MealType.DINNER;
    public static final MenuOwner     DINNER_2017_01_05_OWNER           = new MenuOwner(AUTHENTICATED_USER);
    public static final MenuId        DINNER_2017_01_05_ID              =
            new MenuId(DINNER_2017_01_05_DATE, DINNER_2017_01_05_MEAL_TYPE, DINNER_2017_01_05_OWNER);
    public static final RecipeId      DINNER_2017_01_05_RECIPE          = RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_ID;

    public static final LocalDate     LUNCH_2017_01_06_DATE             = LocalDate.parse("2017-01-06");
    public static final MealType      LUNCH_2017_01_06_MEAL_TYPE        = MealType.LUNCH;
    public static final MenuOwner     LUNCH_2017_01_06_OWNER            = new MenuOwner(AUTHENTICATED_USER);
    public static final MenuId        LUNCH_2017_01_06_ID               =
            new MenuId(LUNCH_2017_01_06_DATE, LUNCH_2017_01_06_MEAL_TYPE, LUNCH_2017_01_06_OWNER);
    public static final RecipeId      LUNCH_2017_01_06_RECIPE           = RecipeMother.MOUSSAKA_ID;

    public static final LocalDate     DINNER_2017_01_06_DATE            = LocalDate.parse("2017-01-06");
    public static final MealType      DINNER_2017_01_06_MEAL_TYPE       = MealType.DINNER;
    public static final MenuOwner     DINNER_2017_01_06_OWNER           = new MenuOwner(AUTHENTICATED_USER);
    public static final MenuId        DINNER_2017_01_06_ID              =
            new MenuId(DINNER_2017_01_06_DATE, DINNER_2017_01_06_MEAL_TYPE, DINNER_2017_01_06_OWNER);
    public static final RecipeId      DINNER_2017_01_06_RECIPE          = RecipeMother.WATERCRESS_SOUP_ID;

    public static final LocalDate     LUNCH_2017_01_07_DATE             = LocalDate.parse("2017-01-07");
    public static final MealType      LUNCH_2017_01_07_MEAL_TYPE        = MealType.LUNCH;
    public static final MenuOwner     LUNCH_2017_01_07_OWNER            = new MenuOwner(AUTHENTICATED_USER);
    public static final MenuId        LUNCH_2017_01_07_ID               =
            new MenuId(LUNCH_2017_01_07_DATE, LUNCH_2017_01_07_MEAL_TYPE, LUNCH_2017_01_07_OWNER);
    public static final RecipeId      LUNCH_2017_01_07_RECIPE           =
            RecipeMother.LEEKS_WITH_HAM_AND_BECHAMEL_SAUCE_ID;

    public static final LocalDate     DINNER_2017_01_07_DATE            = LocalDate.parse("2017-01-07");
    public static final MealType      DINNER_2017_01_07_MEAL_TYPE       = MealType.DINNER;
    public static final MenuOwner     DINNER_2017_01_07_OWNER           = new MenuOwner(AUTHENTICATED_USER);
    public static final MenuId        DINNER_2017_01_07_ID              =
            new MenuId(DINNER_2017_01_07_DATE, DINNER_2017_01_07_MEAL_TYPE, DINNER_2017_01_07_OWNER);
    public static final RecipeId      DINNER_2017_01_07_RECIPE          = RecipeMother.GAZPACHO_ID;

    public static final LocalDate     OTHER_DINNER_2017_01_02_DATE      = LocalDate.parse("2017-01-02");
    public static final MealType      OTHER_DINNER_2017_01_02_MEAL_TYPE = MealType.DINNER;
    public static final MenuOwner     OTHER_DINNER_2017_01_02_OWNER     = new MenuOwner(ANOTHER_USER);
    public static final MenuId        OTHER_DINNER_2017_01_02_ID        =
            new MenuId(OTHER_DINNER_2017_01_02_DATE, OTHER_DINNER_2017_01_02_MEAL_TYPE, OTHER_DINNER_2017_01_02_OWNER);
    public static final RecipeId      OTHER_DINNER_2017_01_02_RECIPE    = RecipeMother.POACHED_EGGS_SALAD_ID;

    public static final LocalDate     OTHER_DINNER_2017_01_03_DATE      = LocalDate.parse("2017-01-03");
    public static final MealType      OTHER_DINNER_2017_01_03_MEAL_TYPE = MealType.DINNER;
    public static final MenuOwner     OTHER_DINNER_2017_01_03_OWNER     = new MenuOwner(ANOTHER_USER);
    public static final MenuId        OTHER_DINNER_2017_01_03_ID        =
            new MenuId(OTHER_DINNER_2017_01_03_DATE, OTHER_DINNER_2017_01_03_MEAL_TYPE, OTHER_DINNER_2017_01_03_OWNER);
    public static final RecipeId      OTHER_DINNER_2017_01_03_RECIPE    =
            RecipeMother.LEEKS_WITH_HAM_AND_BECHAMEL_SAUCE_ID;

    public static final LocalDate     OTHER_DINNER_2017_01_04_DATE      = LocalDate.parse("2017-01-04");
    public static final MealType      OTHER_DINNER_2017_01_04_MEAL_TYPE = MealType.DINNER;
    public static final MenuOwner     OTHER_DINNER_2017_01_04_OWNER     = new MenuOwner(ANOTHER_USER);
    public static final MenuId        OTHER_DINNER_2017_01_04_ID        =
            new MenuId(OTHER_DINNER_2017_01_04_DATE, OTHER_DINNER_2017_01_04_MEAL_TYPE, OTHER_DINNER_2017_01_04_OWNER);
    public static final RecipeId      OTHER_DINNER_2017_01_04_RECIPE    = RecipeMother.RACLETTE_ID;

    public static final LocalDate     OTHER_DINNER_2017_01_05_DATE      = LocalDate.parse("2017-01-05");
    public static final MealType      OTHER_DINNER_2017_01_05_MEAL_TYPE = MealType.DINNER;
    public static final MenuOwner     OTHER_DINNER_2017_01_05_OWNER     = new MenuOwner(ANOTHER_USER);
    public static final MenuId        OTHER_DINNER_2017_01_05_ID        =
            new MenuId(OTHER_DINNER_2017_01_05_DATE, OTHER_DINNER_2017_01_05_MEAL_TYPE, OTHER_DINNER_2017_01_05_OWNER);
    public static final RecipeId      OTHER_DINNER_2017_01_05_RECIPE    = RecipeMother.CAESAR_SALAD_ID;

    public static final LocalDate     OTHER_DINNER_2017_01_06_DATE      = LocalDate.parse("2017-01-06");
    public static final MealType      OTHER_DINNER_2017_01_06_MEAL_TYPE = MealType.DINNER;
    public static final MenuOwner     OTHER_DINNER_2017_01_06_OWNER     = new MenuOwner(ANOTHER_USER);
    public static final MenuId        OTHER_DINNER_2017_01_06_ID        =
            new MenuId(OTHER_DINNER_2017_01_06_DATE, OTHER_DINNER_2017_01_06_MEAL_TYPE, OTHER_DINNER_2017_01_06_OWNER);
    public static final RecipeId      OTHER_DINNER_2017_01_06_RECIPE    = RecipeMother.CHILI_CON_CARNE_ID;

    public static Menu lunch20170101() {
        return new Menu(LUNCH_2017_01_01_ID, LUNCH_2017_01_01_RECIPE);
    }

    public static Menu dinner20170101() {
        return new Menu(DINNER_2017_01_01_ID, DINNER_2017_01_01_RECIPE);
    }

    public static Menu lunch20170102() {
        return new Menu(LUNCH_2017_01_02_ID, LUNCH_2017_01_02_RECIPE);
    }

    public static Menu dinner20170102() {
        return new Menu(DINNER_2017_01_02_ID, DINNER_2017_01_02_RECIPE);
    }

    public static Menu lunch20170103() {
        return new Menu(LUNCH_2017_01_03_ID, LUNCH_2017_01_03_RECIPE);
    }

    public static Menu dinner20170103() {
        return new Menu(DINNER_2017_01_03_ID, DINNER_2017_01_03_RECIPE);
    }

    public static Menu lunch20170104() {
        return new Menu(LUNCH_2017_01_04_ID, LUNCH_2017_01_04_RECIPE);
    }

    public static Menu dinner20170104() {
        return new Menu(DINNER_2017_01_04_ID, DINNER_2017_01_04_RECIPE);
    }

    public static Menu lunch20170105() {
        return new Menu(LUNCH_2017_01_05_ID, LUNCH_2017_01_05_RECIPE);
    }

    public static Menu dinner20170105() {
        return new Menu(DINNER_2017_01_05_ID, DINNER_2017_01_05_RECIPE);
    }

    public static Menu lunch20170106() {
        return new Menu(LUNCH_2017_01_06_ID, LUNCH_2017_01_06_RECIPE);
    }

    public static Menu dinner20170106() {
        return new Menu(DINNER_2017_01_06_ID, DINNER_2017_01_06_RECIPE);
    }

    public static Menu lunch20170107() {
        return new Menu(LUNCH_2017_01_07_ID, LUNCH_2017_01_07_RECIPE);
    }

    public static Menu dinner20170107() {
        return new Menu(DINNER_2017_01_07_ID, DINNER_2017_01_07_RECIPE);
    }

    public static List<Menu> allMenus() {
        return Arrays.asList(lunch20170101(), dinner20170101(), lunch20170102(), dinner20170102(), lunch20170103(),
                dinner20170103(), lunch20170104(), dinner20170104(), lunch20170105(), dinner20170105(), lunch20170106(),
                dinner20170106(), lunch20170107(), dinner20170107());
    }

    public static GenerateMenus generateMenus1DayAt20170102WeekWorkingDays() {
        return new GenerateMenus(CLOCK, MENU_2017_01_02_DAYS, MENU_2017_01_02_START_DATE, MENU_2017_01_02_FREQUENCY,
                MENU_2017_01_02_OWNER);
    }

    public static MenusQuery queryMenus1DayAt20170102() {
        return new MenusQuery(MENU_2017_01_02_DAYS, MENU_2017_01_02_START_DATE, MENU_2017_01_02_OWNER);
    }

    public static GenerateMenus generateMenus2DaysAt20170103TwiceADay() {
        return new GenerateMenus(CLOCK, MENU_2017_01_03_DAYS, MENU_2017_01_03_START_DATE, MENU_2017_01_03_FREQUENCY,
                MENU_2017_01_03_OWNER);
    }

    public static MenusQuery queryMenus2DaysAt20170103() {
        return new MenusQuery(MENU_2017_01_03_DAYS, MENU_2017_01_03_START_DATE, MENU_2017_01_03_OWNER);
    }

    public static GenerateMenus generateMenus7DaysAt20170101TwiceADay() {
        return new GenerateMenus(CLOCK, MENU_2017_01_01_DAYS, MENU_2017_01_01_START_DATE, MENU_2017_01_01_FREQUENCY,
                MENU_2017_01_01_OWNER);
    }

    public static MenusQuery queryMenus7DaysAt20170101() {
        return new MenusQuery(MENU_2017_01_01_DAYS, MENU_2017_01_01_START_DATE, MENU_2017_01_01_OWNER);
    }

    public static MenuGenerationState menuGeneration2DaysAt20170103TwiceADayCurrentState() {
        return new MenuGenerationState(generateMenus2DaysAt20170103TwiceADay()).addMenu(lunch20170103())
                .addMenu(dinner20170103());
    }

    public static MenuGenerationState menuGeneration7DaysAt20170101TwiceADayCurrentState() {
        return new MenuGenerationState(generateMenus7DaysAt20170101TwiceADay()).addMenu(lunch20170101())
                .addMenu(dinner20170101()).addMenu(lunch20170102());
    }

    public static Menu otherDinner20170102() {
        return new Menu(OTHER_DINNER_2017_01_02_ID, OTHER_DINNER_2017_01_02_RECIPE);
    }

    public static Menu otherDinner20170103() {
        return new Menu(OTHER_DINNER_2017_01_03_ID, OTHER_DINNER_2017_01_03_RECIPE);
    }

    public static Menu otherDinner20170104() {
        return new Menu(OTHER_DINNER_2017_01_04_ID, OTHER_DINNER_2017_01_04_RECIPE);
    }

    public static Menu otherDinner20170105() {
        return new Menu(OTHER_DINNER_2017_01_05_ID, OTHER_DINNER_2017_01_05_RECIPE);
    }

    public static Menu otherDinner20170106() {
        return new Menu(OTHER_DINNER_2017_01_06_ID, OTHER_DINNER_2017_01_06_RECIPE);
    }

    public static List<Menu> allOtherMenus() {
        return Arrays.asList(otherDinner20170102(), otherDinner20170103(), otherDinner20170104(), otherDinner20170105(),
                otherDinner20170106());
    }

}
