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

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.CUCUMBER_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.MOZZARELLA_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.MUSTARD_ID;

import org.adhuc.cena.menu.domain.model.recipe.ingredient.AddIngredientToRecipe;
import org.adhuc.cena.menu.domain.model.recipe.ingredient.RecipeIngredientId;

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

    public static final RecipeId     TOMATO_CUCUMBER_MOZZA_SALAD_ID            = RecipeId.generate();
    public static final String       TOMATO_CUCUMBER_MOZZA_SALAD_NAME          =
            "Tomato, cucumber and mozzarella salad";
    public static final String       TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT       =
            "Cut everything into dices, mix it, dress it";
    public static final RecipeAuthor TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR        = new RecipeAuthor("authenticated-user");

    public static final RecipeId     TOMATO_CUCUMBER_OLIVE_FETA_SALAD_ID       = RecipeId.generate();
    public static final String       TOMATO_CUCUMBER_OLIVE_FETA_SALAD_NAME     =
            "Tomato, cucumber, olive and feta salad";
    public static final String       TOMATO_CUCUMBER_OLIVE_FETA_SALAD_CONTENT  =
            "Cut everything into dices, mix it, dress it";
    public static final RecipeAuthor TOMATO_CUCUMBER_OLIVE_FETA_SALAD_AUTHOR   = new RecipeAuthor("authenticated-user");

    public static final RecipeId     TOMATO_CANTAL_PIE_ID                      = RecipeId.generate();
    public static final String       TOMATO_CANTAL_PIE_NAME                    = "Tomato and cantal pie";
    public static final String       TOMATO_CANTAL_PIE_CONTENT                 =
            "Spread the shortcrust in a pie plate, wrap it with mustard, tomato slices and cantal slices in this order, and bake it during 20 minutes";
    public static final RecipeAuthor TOMATO_CANTAL_PIE_AUTHOR                  = new RecipeAuthor("authenticated-user");

    public static final RecipeId     QUICHE_LORRAINE_ID                        = RecipeId.generate();
    public static final String       QUICHE_LORRAINE_NAME                      = "Quiche lorraine";
    public static final String       QUICHE_LORRAINE_CONTENT                   =
            "Spread the shortcrust in a pie plate, beat the eggs with milk, add lardons, pour on the pastry and bake it during 30 minutes";
    public static final RecipeAuthor QUICHE_LORRAINE_AUTHOR                    = new RecipeAuthor("authenticated-user");

    public static final RecipeId     WATERCRESS_SOUP_ID                        = RecipeId.generate();
    public static final String       WATERCRESS_SOUP_NAME                      = "Watercress soup";
    public static final String       WATERCRESS_SOUP_CONTENT                   = "Watercress soup recipe content";
    public static final RecipeAuthor WATERCRESS_SOUP_AUTHOR                    = new RecipeAuthor("authenticated-user");

    public static final RecipeId     GAZPACHO_ID                               = RecipeId.generate();
    public static final String       GAZPACHO_NAME                             = "Gazpacho";
    public static final String       GAZPACHO_CONTENT                          = "Gazpacho recipe content";
    public static final RecipeAuthor GAZPACHO_AUTHOR                           = new RecipeAuthor("authenticated-user");

    public static final RecipeId     POACHED_EGGS_SALAD_ID                     = RecipeId.generate();
    public static final String       POACHED_EGGS_SALAD_NAME                   = "Poached eggs salad";
    public static final String       POACHED_EGGS_SALAD_CONTENT                = "Poached eggs salad recipe content";
    public static final RecipeAuthor POACHED_EGGS_SALAD_AUTHOR                 = new RecipeAuthor("authenticated-user");

    public static final RecipeId     NORVEGIAN_SALAD_ID                        = RecipeId.generate();
    public static final String       NORVEGIAN_SALAD_NAME                      = "Norvegian salad";
    public static final String       NORVEGIAN_SALAD_CONTENT                   = "Norvegian salad recipe content";
    public static final RecipeAuthor NORVEGIAN_SALAD_AUTHOR                    = new RecipeAuthor("authenticated-user");

    public static final RecipeId     OMELETTE_ID                               = RecipeId.generate();
    public static final String       OMELETTE_NAME                             = "Omelette";
    public static final String       OMELETTE_CONTENT                          = "Omelette recipe content";
    public static final RecipeAuthor OMELETTE_AUTHOR                           = new RecipeAuthor("authenticated-user");

    public static final RecipeId     CHILI_CON_CARNE_ID                        = RecipeId.generate();
    public static final String       CHILI_CON_CARNE_NAME                      = "Chili con carne";
    public static final String       CHILI_CON_CARNE_CONTENT                   = "Chili con carne recipe content";
    public static final RecipeAuthor CHILI_CON_CARNE_AUTHOR                    = new RecipeAuthor("authenticated-user");

    public static final RecipeId     SAUERKRAUT_ID                             = RecipeId.generate();
    public static final String       SAUERKRAUT_NAME                           = "Sauerkraut";
    public static final String       SAUERKRAUT_CONTENT                        = "Sauerkraut recipe content";
    public static final RecipeAuthor SAUERKRAUT_AUTHOR                         = new RecipeAuthor("authenticated-user");

    public static final RecipeId     LEEKS_WITH_HAM_AND_BECHAMEL_SAUCE_ID      = RecipeId.generate();
    public static final String       LEEKS_WITH_HAM_AND_BECHAMEL_SAUCE_NAME    = "Leeks with ham and béchamel sauce";
    public static final String       LEEKS_WITH_HAM_AND_BECHAMEL_SAUCE_CONTENT =
            "Leeks with ham and béchamel sauce recipe content";
    public static final RecipeAuthor LEEKS_WITH_HAM_AND_BECHAMEL_SAUCE_AUTHOR  = new RecipeAuthor("authenticated-user");

    public static final RecipeId     MOUSSAKA_ID                               = RecipeId.generate();
    public static final String       MOUSSAKA_NAME                             = "Moussaka";
    public static final String       MOUSSAKA_CONTENT                          = "Moussaka recipe content";
    public static final RecipeAuthor MOUSSAKA_AUTHOR                           = new RecipeAuthor("authenticated-user");

    public static final RecipeId     LASAGNE_ID                                = RecipeId.generate();
    public static final String       LASAGNE_NAME                              = "Lasagne";
    public static final String       LASAGNE_CONTENT                           = "Lasagne recipe content";
    public static final RecipeAuthor LASAGNE_AUTHOR                            = new RecipeAuthor("authenticated-user");

    public static final RecipeId     DUCK_BREAST_FILLET_WITH_TURNIPS_ID        = RecipeId.generate();
    public static final String       DUCK_BREAST_FILLET_WITH_TURNIPS_NAME      = "Duck breast fillet with turnips";
    public static final String       DUCK_BREAST_FILLET_WITH_TURNIPS_CONTENT   =
            "Duck breast fillet with turnips recipe content";
    public static final RecipeAuthor DUCK_BREAST_FILLET_WITH_TURNIPS_AUTHOR    = new RecipeAuthor("authenticated-user");

    public static final RecipeId     CROQUE_MONSIEUR_ID                        = RecipeId.generate();
    public static final String       CROQUE_MONSIEUR_NAME                      = "Croque-monsieur";
    public static final String       CROQUE_MONSIEUR_CONTENT                   = "Croque-monsieur recipe content";
    public static final RecipeAuthor CROQUE_MONSIEUR_AUTHOR                    = new RecipeAuthor("authenticated-user");

    public static final RecipeId     RACLETTE_ID                               = RecipeId.generate();
    public static final String       RACLETTE_NAME                             = "Raclette";
    public static final String       RACLETTE_CONTENT                          = "Raclette recipe content";
    public static final RecipeAuthor RACLETTE_AUTHOR                           = new RecipeAuthor("authenticated-user");

    public static final RecipeId     DUCK_PARMENTIER_ID                        = RecipeId.generate();
    public static final String       DUCK_PARMENTIER_NAME                      = "Duck parmentier";
    public static final String       DUCK_PARMENTIER_CONTENT                   = "Duck parmentier recipe content";
    public static final RecipeAuthor DUCK_PARMENTIER_AUTHOR                    = new RecipeAuthor("authenticated-user");

    public static CreateRecipe createTomatoCucumberMozzaSalad() {
        return new CreateRecipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID, TOMATO_CUCUMBER_MOZZA_SALAD_NAME,
                TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT, TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR);
    }

    public static Recipe tomatoCucumberMozzaSalad() {
        return new Recipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID, TOMATO_CUCUMBER_MOZZA_SALAD_NAME,
                TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT, TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR);
    }

    public static AddIngredientToRecipe addCucumberToTomatoCucumberMozzaSalad() {
        return new AddIngredientToRecipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID, CUCUMBER_ID, true);
    }

    public static RecipeIngredientId cucumberInTomatoCucumberMozzaSalad() {
        return new RecipeIngredientId(CUCUMBER_ID, true);
    }

    public static AddIngredientToRecipe addMozzaToTomatoCucumberMozzaSalad() {
        return new AddIngredientToRecipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID, MOZZARELLA_ID, false);
    }

    public static RecipeIngredientId mozzaInTomatoCucumberMozzaSalad() {
        return new RecipeIngredientId(MOZZARELLA_ID, false);
    }

    public static CreateRecipe createTomatoCucumberOliveFetaSalad() {
        return new CreateRecipe(TOMATO_CUCUMBER_OLIVE_FETA_SALAD_ID, TOMATO_CUCUMBER_OLIVE_FETA_SALAD_NAME,
                TOMATO_CUCUMBER_OLIVE_FETA_SALAD_CONTENT, TOMATO_CUCUMBER_OLIVE_FETA_SALAD_AUTHOR);
    }

    public static Recipe tomatoCucumberOliveFetaSalad() {
        return new Recipe(TOMATO_CUCUMBER_OLIVE_FETA_SALAD_ID, TOMATO_CUCUMBER_OLIVE_FETA_SALAD_NAME,
                TOMATO_CUCUMBER_OLIVE_FETA_SALAD_CONTENT, TOMATO_CUCUMBER_OLIVE_FETA_SALAD_AUTHOR);
    }

    public static Recipe tomatoCantalPie() {
        return new Recipe(TOMATO_CANTAL_PIE_ID, TOMATO_CANTAL_PIE_NAME, TOMATO_CANTAL_PIE_CONTENT,
                TOMATO_CANTAL_PIE_AUTHOR);
    }

    public static AddIngredientToRecipe addMustardToTomatoCantalPie() {
        return new AddIngredientToRecipe(TOMATO_CANTAL_PIE_ID, MUSTARD_ID, false);
    }

    public static RecipeIngredientId mustardInTomatoCantalPie() {
        return new RecipeIngredientId(MUSTARD_ID, false);
    }

    public static Recipe quicheLorraine() {
        return new Recipe(QUICHE_LORRAINE_ID, QUICHE_LORRAINE_NAME, QUICHE_LORRAINE_CONTENT, QUICHE_LORRAINE_AUTHOR);
    }

    public static Recipe watercressSoup() {
        return new Recipe(WATERCRESS_SOUP_ID, WATERCRESS_SOUP_NAME, WATERCRESS_SOUP_CONTENT, WATERCRESS_SOUP_AUTHOR);
    }

    public static Recipe gazpacho() {
        return new Recipe(GAZPACHO_ID, GAZPACHO_NAME, GAZPACHO_CONTENT, GAZPACHO_AUTHOR);
    }

    public static Recipe poachedEggsSalad() {
        return new Recipe(POACHED_EGGS_SALAD_ID, POACHED_EGGS_SALAD_NAME, POACHED_EGGS_SALAD_CONTENT,
                POACHED_EGGS_SALAD_AUTHOR);
    }

    public static Recipe norvegianSalad() {
        return new Recipe(NORVEGIAN_SALAD_ID, NORVEGIAN_SALAD_NAME, NORVEGIAN_SALAD_CONTENT, NORVEGIAN_SALAD_AUTHOR);
    }

    public static Recipe omelette() {
        return new Recipe(OMELETTE_ID, OMELETTE_NAME, OMELETTE_CONTENT, OMELETTE_AUTHOR);
    }

    public static Recipe chiliConCarne() {
        return new Recipe(CHILI_CON_CARNE_ID, CHILI_CON_CARNE_NAME, CHILI_CON_CARNE_CONTENT, CHILI_CON_CARNE_AUTHOR);
    }

    public static Recipe sauerkraut() {
        return new Recipe(SAUERKRAUT_ID, SAUERKRAUT_NAME, SAUERKRAUT_CONTENT, SAUERKRAUT_AUTHOR);
    }

    public static Recipe leeksWithHamAndBechamelSauce() {
        return new Recipe(LEEKS_WITH_HAM_AND_BECHAMEL_SAUCE_ID, LEEKS_WITH_HAM_AND_BECHAMEL_SAUCE_NAME,
                LEEKS_WITH_HAM_AND_BECHAMEL_SAUCE_CONTENT, LEEKS_WITH_HAM_AND_BECHAMEL_SAUCE_AUTHOR);
    }

    public static Recipe moussaka() {
        return new Recipe(MOUSSAKA_ID, MOUSSAKA_NAME, MOUSSAKA_CONTENT, MOUSSAKA_AUTHOR);
    }

    public static Recipe lasagne() {
        return new Recipe(LASAGNE_ID, LASAGNE_NAME, LASAGNE_CONTENT, LASAGNE_AUTHOR);
    }

    public static Recipe duckBreastFilletWithTurnips() {
        return new Recipe(DUCK_BREAST_FILLET_WITH_TURNIPS_ID, DUCK_BREAST_FILLET_WITH_TURNIPS_NAME,
                DUCK_BREAST_FILLET_WITH_TURNIPS_CONTENT, DUCK_BREAST_FILLET_WITH_TURNIPS_AUTHOR);
    }

    public static Recipe croqueMonsieur() {
        return new Recipe(CROQUE_MONSIEUR_ID, CROQUE_MONSIEUR_NAME, CROQUE_MONSIEUR_CONTENT, CROQUE_MONSIEUR_AUTHOR);
    }

    public static Recipe raclette() {
        return new Recipe(RACLETTE_ID, RACLETTE_NAME, RACLETTE_CONTENT, RACLETTE_AUTHOR);
    }

    public static Recipe duckParmentier() {
        return new Recipe(DUCK_PARMENTIER_ID, DUCK_PARMENTIER_NAME, DUCK_PARMENTIER_CONTENT, DUCK_PARMENTIER_AUTHOR);
    }

}
