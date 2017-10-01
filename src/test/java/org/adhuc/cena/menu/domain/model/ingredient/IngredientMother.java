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

/**
 * An object mother to create testing domain elements related to {@link Ingredient}s.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 *
 * @see https://www.martinfowler.com/bliki/ObjectMother.html
 */
public class IngredientMother {

    public static final IngredientId TOMATO_ID     = IngredientId.generate();
    public static final String       TOMATO_NAME   = "Tomato";

    public static final IngredientId CUCUMBER_ID   = IngredientId.generate();
    public static final String       CUCUMBER_NAME = "Cucumber";

    public static CreateIngredient createTomato() {
        return new CreateIngredient(TOMATO_ID, TOMATO_NAME);
    }

    public static Ingredient tomato() {
        return new Ingredient(TOMATO_ID, TOMATO_NAME);
    }

    public static CreateIngredient createCucumber() {
        return new CreateIngredient(CUCUMBER_ID, CUCUMBER_NAME);
    }

    public static Ingredient cucumber() {
        return new Ingredient(CUCUMBER_ID, CUCUMBER_NAME);
    }

}
