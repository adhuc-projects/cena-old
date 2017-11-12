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

import java.util.Arrays;
import java.util.List;

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

    public static final IngredientId TOMATO_ID       = IngredientId.generate();
    public static final String       TOMATO_NAME     = "Tomato";

    public static final IngredientId CUCUMBER_ID     = IngredientId.generate();
    public static final String       CUCUMBER_NAME   = "Cucumber";

    public static final IngredientId POTATO_ID       = IngredientId.generate();
    public static final String       POTATO_NAME     = "Potato";

    public static final IngredientId MOZZARELLA_ID   = IngredientId.generate();
    public static final String       MOZZARELLA_NAME = "Mozzarella";

    public static final IngredientId OLIVE_ID        = IngredientId.generate();
    public static final String       OLIVE_NAME      = "Olive";

    public static final IngredientId FETA_ID         = IngredientId.generate();
    public static final String       FETA_NAME       = "Feta";

    public static final IngredientId MUSTARD_ID      = IngredientId.generate();
    public static final String       MUSTARD_NAME    = "Mustard";

    public static final IngredientId CANTAL_ID       = IngredientId.generate();
    public static final String       CANTAL_NAME     = "Cantal";

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

    public static Ingredient potato() {
        return new Ingredient(POTATO_ID, POTATO_NAME);
    }

    public static Ingredient mozzarella() {
        return new Ingredient(MOZZARELLA_ID, MOZZARELLA_NAME);
    }

    public static Ingredient olive() {
        return new Ingredient(OLIVE_ID, OLIVE_NAME);
    }

    public static Ingredient feta() {
        return new Ingredient(FETA_ID, FETA_NAME);
    }

    public static Ingredient mustard() {
        return new Ingredient(MUSTARD_ID, MUSTARD_NAME);
    }

    public static Ingredient cantal() {
        return new Ingredient(CANTAL_ID, CANTAL_NAME);
    }

    public static List<Ingredient> allIngredients() {
        return Arrays.asList(tomato(), cucumber(), potato(), mozzarella(), olive(), feta(), mustard(), cantal());
    }

}
