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

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.AUBERGINE_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.BACON_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.BECHAMEL_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.BREAD_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.CANTAL_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.CARROT_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.CHEESE_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.CONFIT_OF_DUCK_LEG_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.CROUTON_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.CUCUMBER_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.DUCK_BREAST_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.EGG_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.FETA_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.FRANKFURTER_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.GREEN_PEPPER_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.HAM_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.HONEY_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.KIDNEY_BEAN_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.LAMB_LETTUCE_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.LARDONS_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.LEEK_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.MILK_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.MINCED_BEEF_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.MINCED_MUTTON_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.MOZZARELLA_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.MUSTARD_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.OLIVE_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.PASTA_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.POTATO_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.RED_PEPPER_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.SALMON_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.SHORTCRUST_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TURNIP_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.WATERCRESS_ID;

import java.util.Arrays;
import java.util.List;

import org.adhuc.cena.menu.domain.model.ingredient.IngredientMother;
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

    public static RecipeIngredientId tomatoInTomatoCucumberMozzaSalad() {
        return new RecipeIngredientId(TOMATO_ID, true);
    }

    public static RecipeIngredientId cucumberInTomatoCucumberMozzaSalad() {
        return new RecipeIngredientId(CUCUMBER_ID, true);
    }

    public static AddIngredientToRecipe addCucumberToTomatoCucumberMozzaSalad() {
        return new AddIngredientToRecipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID, CUCUMBER_ID, true);
    }

    public static RecipeIngredientId mozzaInTomatoCucumberMozzaSalad() {
        return new RecipeIngredientId(MOZZARELLA_ID, false);
    }

    public static AddIngredientToRecipe addMozzaToTomatoCucumberMozzaSalad() {
        return new AddIngredientToRecipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID, MOZZARELLA_ID, false);
    }

    public static List<RecipeIngredientId> tomatoCucumberMozzaSaladIngredients() {
        return Arrays.asList(tomatoInTomatoCucumberMozzaSalad(), cucumberInTomatoCucumberMozzaSalad(),
                mozzaInTomatoCucumberMozzaSalad());
    }

    public static Recipe tomatoCucumberMozzaSalad() {
        return new Recipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID, TOMATO_CUCUMBER_MOZZA_SALAD_NAME,
                TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT, TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR);
    }

    public static Recipe tomatoCucumberMozzaSaladWithIngredients() {
        return new Recipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID, TOMATO_CUCUMBER_MOZZA_SALAD_NAME,
                TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT, TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR,
                tomatoCucumberMozzaSaladIngredients());
    }

    public static CreateRecipe createTomatoCucumberOliveFetaSalad() {
        return new CreateRecipe(TOMATO_CUCUMBER_OLIVE_FETA_SALAD_ID, TOMATO_CUCUMBER_OLIVE_FETA_SALAD_NAME,
                TOMATO_CUCUMBER_OLIVE_FETA_SALAD_CONTENT, TOMATO_CUCUMBER_OLIVE_FETA_SALAD_AUTHOR);
    }

    public static RecipeIngredientId tomatoInTomatoCucumberOliveFetaSalad() {
        return new RecipeIngredientId(TOMATO_ID, true);
    }

    public static RecipeIngredientId cucumberInTomatoCucumberOliveFetaSalad() {
        return new RecipeIngredientId(CUCUMBER_ID, true);
    }

    public static RecipeIngredientId oliveInTomatoCucumberOliveFetaSalad() {
        return new RecipeIngredientId(OLIVE_ID, false);
    }

    public static RecipeIngredientId fetaInTomatoCucumberOliveFetaSalad() {
        return new RecipeIngredientId(FETA_ID, false);
    }

    public static List<RecipeIngredientId> tomatoCucumberOliveFetaSaladIngredients() {
        return Arrays.asList(tomatoInTomatoCucumberOliveFetaSalad(), cucumberInTomatoCucumberOliveFetaSalad(),
                oliveInTomatoCucumberOliveFetaSalad(), fetaInTomatoCucumberOliveFetaSalad());
    }

    public static Recipe tomatoCucumberOliveFetaSalad() {
        return new Recipe(TOMATO_CUCUMBER_OLIVE_FETA_SALAD_ID, TOMATO_CUCUMBER_OLIVE_FETA_SALAD_NAME,
                TOMATO_CUCUMBER_OLIVE_FETA_SALAD_CONTENT, TOMATO_CUCUMBER_OLIVE_FETA_SALAD_AUTHOR);
    }

    public static Recipe tomatoCucumberOliveFetaSaladWithIngredients() {
        return new Recipe(TOMATO_CUCUMBER_OLIVE_FETA_SALAD_ID, TOMATO_CUCUMBER_OLIVE_FETA_SALAD_NAME,
                TOMATO_CUCUMBER_OLIVE_FETA_SALAD_CONTENT, TOMATO_CUCUMBER_OLIVE_FETA_SALAD_AUTHOR,
                tomatoCucumberOliveFetaSaladIngredients());
    }

    public static RecipeIngredientId tomatoInTomatoCantalPie() {
        return new RecipeIngredientId(TOMATO_ID, true);
    }

    public static RecipeIngredientId cantalInTomatoCantalPie() {
        return new RecipeIngredientId(CANTAL_ID, true);
    }

    public static RecipeIngredientId shortcrustInTomatoCantalPie() {
        return new RecipeIngredientId(SHORTCRUST_ID, true);
    }

    public static RecipeIngredientId mustardInTomatoCantalPie() {
        return new RecipeIngredientId(MUSTARD_ID, false);
    }

    public static AddIngredientToRecipe addMustardToTomatoCantalPie() {
        return new AddIngredientToRecipe(TOMATO_CANTAL_PIE_ID, MUSTARD_ID, false);
    }

    public static List<RecipeIngredientId> tomatoCantalPieIngredients() {
        return Arrays.asList(tomatoInTomatoCantalPie(), cantalInTomatoCantalPie(), shortcrustInTomatoCantalPie(),
                mustardInTomatoCantalPie());
    }

    public static Recipe tomatoCantalPie() {
        return new Recipe(TOMATO_CANTAL_PIE_ID, TOMATO_CANTAL_PIE_NAME, TOMATO_CANTAL_PIE_CONTENT,
                TOMATO_CANTAL_PIE_AUTHOR);
    }

    public static Recipe tomatoCantalPieWithIngredients() {
        return new Recipe(TOMATO_CANTAL_PIE_ID, TOMATO_CANTAL_PIE_NAME, TOMATO_CANTAL_PIE_CONTENT,
                TOMATO_CANTAL_PIE_AUTHOR, tomatoCantalPieIngredients());
    }

    public static RecipeIngredientId shortcrustInQuicheLorraine() {
        return new RecipeIngredientId(SHORTCRUST_ID, true);
    }

    public static RecipeIngredientId eggInQuicheLorraine() {
        return new RecipeIngredientId(EGG_ID, true);
    }

    public static RecipeIngredientId milkInQuicheLorraine() {
        return new RecipeIngredientId(MILK_ID, false);
    }

    public static RecipeIngredientId lardonsInQuicheLorraine() {
        return new RecipeIngredientId(LARDONS_ID, true);
    }

    public static List<RecipeIngredientId> quicheLorraineIngredients() {
        return Arrays.asList(shortcrustInQuicheLorraine(), eggInQuicheLorraine(), milkInQuicheLorraine(),
                lardonsInQuicheLorraine());
    }

    public static Recipe quicheLorraine() {
        return new Recipe(QUICHE_LORRAINE_ID, QUICHE_LORRAINE_NAME, QUICHE_LORRAINE_CONTENT, QUICHE_LORRAINE_AUTHOR);
    }

    public static Recipe quicheLorraineWithIngredients() {
        return new Recipe(QUICHE_LORRAINE_ID, QUICHE_LORRAINE_NAME, QUICHE_LORRAINE_CONTENT, QUICHE_LORRAINE_AUTHOR,
                quicheLorraineIngredients());
    }

    public static RecipeIngredientId watercressInWatercressSoup() {
        return new RecipeIngredientId(WATERCRESS_ID, true);
    }

    public static RecipeIngredientId potatoInWatercressSoup() {
        return new RecipeIngredientId(POTATO_ID, false);
    }

    public static List<RecipeIngredientId> watercressSoupIngredients() {
        return Arrays.asList(watercressInWatercressSoup(), potatoInWatercressSoup());
    }

    public static Recipe watercressSoup() {
        return new Recipe(WATERCRESS_SOUP_ID, WATERCRESS_SOUP_NAME, WATERCRESS_SOUP_CONTENT, WATERCRESS_SOUP_AUTHOR);
    }

    public static Recipe watercressSoupWithIngredients() {
        return new Recipe(WATERCRESS_SOUP_ID, WATERCRESS_SOUP_NAME, WATERCRESS_SOUP_CONTENT, WATERCRESS_SOUP_AUTHOR,
                watercressSoupIngredients());
    }

    public static RecipeIngredientId tomatoInGazpacho() {
        return new RecipeIngredientId(TOMATO_ID, true);
    }

    public static RecipeIngredientId greenPepperInGazpacho() {
        return new RecipeIngredientId(GREEN_PEPPER_ID, false);
    }

    public static RecipeIngredientId cucumberInGazpacho() {
        return new RecipeIngredientId(CUCUMBER_ID, false);
    }

    public static List<RecipeIngredientId> gazpachoIngredients() {
        return Arrays.asList(tomatoInGazpacho(), greenPepperInGazpacho(), cucumberInGazpacho());
    }

    public static Recipe gazpacho() {
        return new Recipe(GAZPACHO_ID, GAZPACHO_NAME, GAZPACHO_CONTENT, GAZPACHO_AUTHOR);
    }

    public static Recipe gazpachoWithIngredients() {
        return new Recipe(GAZPACHO_ID, GAZPACHO_NAME, GAZPACHO_CONTENT, GAZPACHO_AUTHOR, gazpachoIngredients());
    }

    public static RecipeIngredientId eggInPoachedEggsSalad() {
        return new RecipeIngredientId(EGG_ID, true);
    }

    public static RecipeIngredientId lambLettuceInPoachedEggsSalad() {
        return new RecipeIngredientId(LAMB_LETTUCE_ID, true);
    }

    public static RecipeIngredientId croutonInPoachedEggsSalad() {
        return new RecipeIngredientId(CROUTON_ID, false);
    }

    public static List<RecipeIngredientId> poachedEggsSaladIngredients() {
        return Arrays.asList(eggInPoachedEggsSalad(), lambLettuceInPoachedEggsSalad(), croutonInPoachedEggsSalad());
    }

    public static Recipe poachedEggsSalad() {
        return new Recipe(POACHED_EGGS_SALAD_ID, POACHED_EGGS_SALAD_NAME, POACHED_EGGS_SALAD_CONTENT,
                POACHED_EGGS_SALAD_AUTHOR);
    }

    public static Recipe poachedEggsSaladWithIngredients() {
        return new Recipe(POACHED_EGGS_SALAD_ID, POACHED_EGGS_SALAD_NAME, POACHED_EGGS_SALAD_CONTENT,
                POACHED_EGGS_SALAD_AUTHOR, poachedEggsSaladIngredients());
    }

    public static RecipeIngredientId salmonInNorvegianSalad() {
        return new RecipeIngredientId(SALMON_ID, true);
    }

    public static RecipeIngredientId cucumberInNorvegianSalad() {
        return new RecipeIngredientId(CUCUMBER_ID, true);
    }

    public static RecipeIngredientId eggInNorvegianSalad() {
        return new RecipeIngredientId(EGG_ID, true);
    }

    public static List<RecipeIngredientId> norvegianSaladIngredients() {
        return Arrays.asList(salmonInNorvegianSalad(), cucumberInNorvegianSalad(), eggInNorvegianSalad());
    }

    public static Recipe norvegianSalad() {
        return new Recipe(NORVEGIAN_SALAD_ID, NORVEGIAN_SALAD_NAME, NORVEGIAN_SALAD_CONTENT, NORVEGIAN_SALAD_AUTHOR);
    }

    public static Recipe norvegianSaladWithIngredients() {
        return new Recipe(NORVEGIAN_SALAD_ID, NORVEGIAN_SALAD_NAME, NORVEGIAN_SALAD_CONTENT, NORVEGIAN_SALAD_AUTHOR,
                norvegianSaladIngredients());
    }

    public static RecipeIngredientId eggInOmelette() {
        return new RecipeIngredientId(EGG_ID, true);
    }

    public static List<RecipeIngredientId> omeletteIngredients() {
        return Arrays.asList(eggInOmelette());
    }

    public static Recipe omelette() {
        return new Recipe(OMELETTE_ID, OMELETTE_NAME, OMELETTE_CONTENT, OMELETTE_AUTHOR);
    }

    public static Recipe omeletteWithIngredients() {
        return new Recipe(OMELETTE_ID, OMELETTE_NAME, OMELETTE_CONTENT, OMELETTE_AUTHOR, omeletteIngredients());
    }

    public static RecipeIngredientId kidneyBeanInChiliConCarne() {
        return new RecipeIngredientId(KIDNEY_BEAN_ID, true);
    }

    public static RecipeIngredientId lardonsInChiliConCarne() {
        return new RecipeIngredientId(LARDONS_ID, true);
    }

    public static RecipeIngredientId mincedBeefInChiliConCarne() {
        return new RecipeIngredientId(MINCED_BEEF_ID, true);
    }

    public static RecipeIngredientId redPepperInChiliConCarne() {
        return new RecipeIngredientId(RED_PEPPER_ID, true);
    }

    public static List<RecipeIngredientId> chiliConCarneIngredients() {
        return Arrays.asList(kidneyBeanInChiliConCarne(), lardonsInChiliConCarne(), mincedBeefInChiliConCarne(),
                redPepperInChiliConCarne());
    }

    public static Recipe chiliConCarne() {
        return new Recipe(CHILI_CON_CARNE_ID, CHILI_CON_CARNE_NAME, CHILI_CON_CARNE_CONTENT, CHILI_CON_CARNE_AUTHOR);
    }

    public static Recipe chiliConCarneWithIngredients() {
        return new Recipe(CHILI_CON_CARNE_ID, CHILI_CON_CARNE_NAME, CHILI_CON_CARNE_CONTENT, CHILI_CON_CARNE_AUTHOR,
                chiliConCarneIngredients());
    }

    public static RecipeIngredientId sauerkrautInSauerkraut() {
        return new RecipeIngredientId(IngredientMother.SAUERKRAUT_ID, true);
    }

    public static RecipeIngredientId lardonsInSauerkraut() {
        return new RecipeIngredientId(LARDONS_ID, false);
    }

    public static RecipeIngredientId frankfurterInSauerkraut() {
        return new RecipeIngredientId(FRANKFURTER_ID, true);
    }

    public static List<RecipeIngredientId> sauerkrautIngredients() {
        return Arrays.asList(sauerkrautInSauerkraut(), lardonsInSauerkraut(), frankfurterInSauerkraut());
    }

    public static Recipe sauerkraut() {
        return new Recipe(SAUERKRAUT_ID, SAUERKRAUT_NAME, SAUERKRAUT_CONTENT, SAUERKRAUT_AUTHOR);
    }

    public static Recipe sauerkrautWithIngredients() {
        return new Recipe(SAUERKRAUT_ID, SAUERKRAUT_NAME, SAUERKRAUT_CONTENT, SAUERKRAUT_AUTHOR,
                sauerkrautIngredients());
    }

    public static RecipeIngredientId leekInLeeksWithHamAndBechamelSauce() {
        return new RecipeIngredientId(LEEK_ID, true);
    }

    public static RecipeIngredientId hamInLeeksWithHamAndBechamelSauce() {
        return new RecipeIngredientId(HAM_ID, true);
    }

    public static RecipeIngredientId bechamelInLeeksWithHamAndBechamelSauce() {
        return new RecipeIngredientId(BECHAMEL_ID, false);
    }

    public static List<RecipeIngredientId> leeksWithHamAndBechamelSauceIngredients() {
        return Arrays.asList(leekInLeeksWithHamAndBechamelSauce(), hamInLeeksWithHamAndBechamelSauce(),
                bechamelInLeeksWithHamAndBechamelSauce());
    }

    public static Recipe leeksWithHamAndBechamelSauce() {
        return new Recipe(LEEKS_WITH_HAM_AND_BECHAMEL_SAUCE_ID, LEEKS_WITH_HAM_AND_BECHAMEL_SAUCE_NAME,
                LEEKS_WITH_HAM_AND_BECHAMEL_SAUCE_CONTENT, LEEKS_WITH_HAM_AND_BECHAMEL_SAUCE_AUTHOR);
    }

    public static Recipe leeksWithHamAndBechamelSauceWithIngredients() {
        return new Recipe(LEEKS_WITH_HAM_AND_BECHAMEL_SAUCE_ID, LEEKS_WITH_HAM_AND_BECHAMEL_SAUCE_NAME,
                LEEKS_WITH_HAM_AND_BECHAMEL_SAUCE_CONTENT, LEEKS_WITH_HAM_AND_BECHAMEL_SAUCE_AUTHOR,
                leeksWithHamAndBechamelSauceIngredients());
    }

    public static RecipeIngredientId aubergineInMoussaka() {
        return new RecipeIngredientId(AUBERGINE_ID, true);
    }

    public static RecipeIngredientId mincedMuttonInMoussaka() {
        return new RecipeIngredientId(MINCED_MUTTON_ID, true);
    }

    public static RecipeIngredientId tomatoInMoussaka() {
        return new RecipeIngredientId(TOMATO_ID, false);
    }

    public static List<RecipeIngredientId> moussakaIngredients() {
        return Arrays.asList(aubergineInMoussaka(), mincedMuttonInMoussaka(), tomatoInMoussaka());
    }

    public static Recipe moussaka() {
        return new Recipe(MOUSSAKA_ID, MOUSSAKA_NAME, MOUSSAKA_CONTENT, MOUSSAKA_AUTHOR);
    }

    public static Recipe moussakaWithIngredients() {
        return new Recipe(MOUSSAKA_ID, MOUSSAKA_NAME, MOUSSAKA_CONTENT, MOUSSAKA_AUTHOR, moussakaIngredients());
    }

    public static RecipeIngredientId pastaInLasagne() {
        return new RecipeIngredientId(PASTA_ID, true);
    }

    public static RecipeIngredientId tomatoInLasagne() {
        return new RecipeIngredientId(TOMATO_ID, true);
    }

    public static RecipeIngredientId mincedBeefInLasagne() {
        return new RecipeIngredientId(MINCED_BEEF_ID, true);
    }

    public static RecipeIngredientId carrotInLasagne() {
        return new RecipeIngredientId(CARROT_ID, false);
    }

    public static List<RecipeIngredientId> lasagneIngredients() {
        return Arrays.asList(pastaInLasagne(), tomatoInLasagne(), mincedBeefInLasagne(), carrotInLasagne());
    }

    public static Recipe lasagne() {
        return new Recipe(LASAGNE_ID, LASAGNE_NAME, LASAGNE_CONTENT, LASAGNE_AUTHOR);
    }

    public static Recipe lasagneWithIngredients() {
        return new Recipe(LASAGNE_ID, LASAGNE_NAME, LASAGNE_CONTENT, LASAGNE_AUTHOR, lasagneIngredients());
    }

    public static RecipeIngredientId duckBreastInDuckBreastFilletWithTurnips() {
        return new RecipeIngredientId(DUCK_BREAST_ID, true);
    }

    public static RecipeIngredientId turnipInDuckBreastFilletWithTurnips() {
        return new RecipeIngredientId(TURNIP_ID, true);
    }

    public static RecipeIngredientId honeyInDuckBreastFilletWithTurnips() {
        return new RecipeIngredientId(HONEY_ID, false);
    }

    public static List<RecipeIngredientId> duckBreastFilletWithTurnipsIngredients() {
        return Arrays.asList(duckBreastInDuckBreastFilletWithTurnips(), turnipInDuckBreastFilletWithTurnips(),
                honeyInDuckBreastFilletWithTurnips());
    }

    public static Recipe duckBreastFilletWithTurnips() {
        return new Recipe(DUCK_BREAST_FILLET_WITH_TURNIPS_ID, DUCK_BREAST_FILLET_WITH_TURNIPS_NAME,
                DUCK_BREAST_FILLET_WITH_TURNIPS_CONTENT, DUCK_BREAST_FILLET_WITH_TURNIPS_AUTHOR);
    }

    public static Recipe duckBreastFilletWithTurnipsWithIngredients() {
        return new Recipe(DUCK_BREAST_FILLET_WITH_TURNIPS_ID, DUCK_BREAST_FILLET_WITH_TURNIPS_NAME,
                DUCK_BREAST_FILLET_WITH_TURNIPS_CONTENT, DUCK_BREAST_FILLET_WITH_TURNIPS_AUTHOR,
                duckBreastFilletWithTurnipsIngredients());
    }

    public static RecipeIngredientId breadInCroqueMonsieur() {
        return new RecipeIngredientId(BREAD_ID, true);
    }

    public static RecipeIngredientId hamInCroqueMonsieur() {
        return new RecipeIngredientId(HAM_ID, true);
    }

    public static RecipeIngredientId cheeseInCroqueMonsieur() {
        return new RecipeIngredientId(CHEESE_ID, true);
    }

    public static List<RecipeIngredientId> croqueMonsieurIngredients() {
        return Arrays.asList(breadInCroqueMonsieur(), hamInCroqueMonsieur(), cheeseInCroqueMonsieur());
    }

    public static Recipe croqueMonsieur() {
        return new Recipe(CROQUE_MONSIEUR_ID, CROQUE_MONSIEUR_NAME, CROQUE_MONSIEUR_CONTENT, CROQUE_MONSIEUR_AUTHOR);
    }

    public static Recipe croqueMonsieurWithIngredients() {
        return new Recipe(CROQUE_MONSIEUR_ID, CROQUE_MONSIEUR_NAME, CROQUE_MONSIEUR_CONTENT, CROQUE_MONSIEUR_AUTHOR,
                croqueMonsieurIngredients());
    }

    public static RecipeIngredientId cheeseInRaclette() {
        return new RecipeIngredientId(CHEESE_ID, true);
    }

    public static RecipeIngredientId potatoInRaclette() {
        return new RecipeIngredientId(POTATO_ID, true);
    }

    public static RecipeIngredientId baconInRaclette() {
        return new RecipeIngredientId(BACON_ID, true);
    }

    public static List<RecipeIngredientId> racletteIngredients() {
        return Arrays.asList(cheeseInRaclette(), potatoInRaclette(), baconInRaclette());
    }

    public static Recipe raclette() {
        return new Recipe(RACLETTE_ID, RACLETTE_NAME, RACLETTE_CONTENT, RACLETTE_AUTHOR);
    }

    public static Recipe racletteWithIngredients() {
        return new Recipe(RACLETTE_ID, RACLETTE_NAME, RACLETTE_CONTENT, RACLETTE_AUTHOR, racletteIngredients());
    }

    public static RecipeIngredientId confitOfLegDuckInDuckParmentier() {
        return new RecipeIngredientId(CONFIT_OF_DUCK_LEG_ID, true);
    }

    public static RecipeIngredientId potatoInDuckParmentier() {
        return new RecipeIngredientId(POTATO_ID, true);
    }

    public static RecipeIngredientId milkInDuckParmentier() {
        return new RecipeIngredientId(MILK_ID, false);
    }

    public static List<RecipeIngredientId> duckParmentierIngredients() {
        return Arrays.asList(confitOfLegDuckInDuckParmentier(), potatoInDuckParmentier(), milkInDuckParmentier());
    }

    public static Recipe duckParmentier() {
        return new Recipe(DUCK_PARMENTIER_ID, DUCK_PARMENTIER_NAME, DUCK_PARMENTIER_CONTENT, DUCK_PARMENTIER_AUTHOR);
    }

    public static Recipe duckParmentierWithIngredients() {
        return new Recipe(DUCK_PARMENTIER_ID, DUCK_PARMENTIER_NAME, DUCK_PARMENTIER_CONTENT, DUCK_PARMENTIER_AUTHOR,
                duckParmentierIngredients());
    }

}
