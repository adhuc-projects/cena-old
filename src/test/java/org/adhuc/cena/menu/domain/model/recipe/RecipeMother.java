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
package org.adhuc.cena.menu.domain.model.recipe;

import java.util.Arrays;
import java.util.List;

/**
 * An object mother to create testing domain elements related to {@link Recipe}s.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 *
 * @see https://www.martinfowler.com/bliki/ObjectMother.html
 */
public class RecipeMother {

    public static final RecipeId     TOMATO_CUCUMBER_MOZZA_SALAD_ID           = RecipeId.generate();
    public static final String       TOMATO_CUCUMBER_MOZZA_SALAD_NAME         = "Tomato, cucumber and mozzarella salad";
    public static final String       TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT      =
            "Cut everything into dices, mix it, dress it";
    public static final RecipeAuthor TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR       = new RecipeAuthor("authenticated-user");

    public static final RecipeId     TOMATO_CUCUMBER_OLIVE_FETA_SALAD_ID      = RecipeId.generate();
    public static final String       TOMATO_CUCUMBER_OLIVE_FETA_SALAD_NAME    =
            "Tomato, cucumber, olive and feta salad";
    public static final String       TOMATO_CUCUMBER_OLIVE_FETA_SALAD_CONTENT =
            "Cut everything into dices, mix it, dress it";
    public static final RecipeAuthor TOMATO_CUCUMBER_OLIVE_FETA_SALAD_AUTHOR  = new RecipeAuthor("authenticated-user");

    public static CreateRecipe createTomatoCucumberMozzaSalad() {
        return new CreateRecipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID, TOMATO_CUCUMBER_MOZZA_SALAD_NAME,
                TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT, TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR);
    }

    public static Recipe tomatoCucumberMozzaSalad() {
        return new Recipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID, TOMATO_CUCUMBER_MOZZA_SALAD_NAME,
                TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT, TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR);
    }

    public static CreateRecipe createTomatoCucumberOliveFetaSalad() {
        return new CreateRecipe(TOMATO_CUCUMBER_OLIVE_FETA_SALAD_ID, TOMATO_CUCUMBER_OLIVE_FETA_SALAD_NAME,
                TOMATO_CUCUMBER_OLIVE_FETA_SALAD_CONTENT, TOMATO_CUCUMBER_OLIVE_FETA_SALAD_AUTHOR);
    }

    public static Recipe tomatoCucumberOliveFetaSalad() {
        return new Recipe(TOMATO_CUCUMBER_OLIVE_FETA_SALAD_ID, TOMATO_CUCUMBER_OLIVE_FETA_SALAD_NAME,
                TOMATO_CUCUMBER_OLIVE_FETA_SALAD_CONTENT, TOMATO_CUCUMBER_OLIVE_FETA_SALAD_AUTHOR);
    }

    public static List<Recipe> allRecipes() {
        return Arrays.asList(tomatoCucumberMozzaSalad(), tomatoCucumberOliveFetaSalad());
    }

}
