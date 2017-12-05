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
import java.util.Optional;

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

    public static final IngredientId TOMATO_ID               = IngredientId.generate();
    public static final String       TOMATO_NAME             = "Tomato";

    public static final IngredientId CUCUMBER_ID             = IngredientId.generate();
    public static final String       CUCUMBER_NAME           = "Cucumber";

    public static final IngredientId POTATO_ID               = IngredientId.generate();
    public static final String       POTATO_NAME             = "Potato";

    public static final IngredientId MOZZARELLA_ID           = IngredientId.generate();
    public static final String       MOZZARELLA_NAME         = "Mozzarella";

    public static final IngredientId OLIVE_ID                = IngredientId.generate();
    public static final String       OLIVE_NAME              = "Olive";

    public static final IngredientId FETA_ID                 = IngredientId.generate();
    public static final String       FETA_NAME               = "Feta";

    public static final IngredientId MUSTARD_ID              = IngredientId.generate();
    public static final String       MUSTARD_NAME            = "Mustard";

    public static final IngredientId CANTAL_ID               = IngredientId.generate();
    public static final String       CANTAL_NAME             = "Cantal";

    public static final IngredientId SHORTCRUST_ID           = IngredientId.generate();
    public static final String       SHORTCRUST_NAME         = "Shortcrust";

    public static final IngredientId EGG_ID                  = IngredientId.generate();
    public static final String       EGG_NAME                = "Egg";

    public static final IngredientId MILK_ID                 = IngredientId.generate();
    public static final String       MILK_NAME               = "Milk";

    public static final IngredientId LARDONS_ID              = IngredientId.generate();
    public static final String       LARDONS_NAME            = "Lardons";

    public static final IngredientId WATERCRESS_ID           = IngredientId.generate();
    public static final String       WATERCRESS_NAME         = "Watercress";

    public static final IngredientId GREEN_PEPPER_ID         = IngredientId.generate();
    public static final String       GREEN_PEPPER_NAME       = "Green pepper";

    public static final IngredientId LAMB_LETTUCE_ID         = IngredientId.generate();
    public static final String       LAMB_LETTUCE_NAME       = "Lamb's lettuce";

    public static final IngredientId CROUTON_ID              = IngredientId.generate();
    public static final String       CROUTON_NAME            = "Crouton";

    public static final IngredientId SALMON_ID               = IngredientId.generate();
    public static final String       SALMON_NAME             = "Salmon";

    public static final IngredientId KIDNEY_BEAN_ID          = IngredientId.generate();
    public static final String       KIDNEY_BEAN_NAME        = "Kidney bean";

    public static final IngredientId MINCED_BEEF_ID          = IngredientId.generate();
    public static final String       MINCED_BEEF_NAME        = "Minced beef";

    public static final IngredientId RED_PEPPER_ID           = IngredientId.generate();
    public static final String       RED_PEPPER_NAME         = "Red pepper";

    public static final IngredientId SAUERKRAUT_ID           = IngredientId.generate();
    public static final String       SAUERKRAUT_NAME         = "Sauerkraut";

    public static final IngredientId FRANKFURTER_ID          = IngredientId.generate();
    public static final String       FRANKFURTER_NAME        = "Frankfurter";

    public static final IngredientId LEEK_ID                 = IngredientId.generate();
    public static final String       LEEK_NAME               = "Leek";

    public static final IngredientId HAM_ID                  = IngredientId.generate();
    public static final String       HAM_NAME                = "Ham";

    public static final IngredientId BECHAMEL_ID             = IngredientId.generate();
    public static final String       BECHAMEL_NAME           = "Bechamel";

    public static final IngredientId AUBERGINE_ID            = IngredientId.generate();
    public static final String       AUBERGINE_NAME          = "Aubergine";

    public static final IngredientId MINCED_MUTTON_ID        = IngredientId.generate();
    public static final String       MINCED_MUTTON_NAME      = "Minced mutton";

    public static final IngredientId PASTA_ID                = IngredientId.generate();
    public static final String       PASTA_NAME              = "Pasta";

    public static final IngredientId CARROT_ID               = IngredientId.generate();
    public static final String       CARROT_NAME             = "Carrot";

    public static final IngredientId DUCK_BREAST_ID          = IngredientId.generate();
    public static final String       DUCK_BREAST_NAME        = "Duck breast";

    public static final IngredientId TURNIP_ID               = IngredientId.generate();
    public static final String       TURNIP_NAME             = "Turnip";

    public static final IngredientId HONEY_ID                = IngredientId.generate();
    public static final String       HONEY_NAME              = "Honey";

    public static final IngredientId BREAD_ID                = IngredientId.generate();
    public static final String       BREAD_NAME              = "Bread";

    public static final IngredientId CHEESE_ID               = IngredientId.generate();
    public static final String       CHEESE_NAME             = "Cheese";

    public static final IngredientId BACON_ID                = IngredientId.generate();
    public static final String       BACON_NAME              = "Bacon";

    public static final IngredientId CONFIT_OF_DUCK_LEG_ID   = IngredientId.generate();
    public static final String       CONFIT_OF_DUCK_LEG_NAME = "Confit of duck leg";

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

    public static Ingredient shortcrust() {
        return new Ingredient(SHORTCRUST_ID, SHORTCRUST_NAME);
    }

    public static Ingredient egg() {
        return new Ingredient(EGG_ID, EGG_NAME);
    }

    public static Ingredient milk() {
        return new Ingredient(MILK_ID, MILK_NAME);
    }

    public static Ingredient lardons() {
        return new Ingredient(LARDONS_ID, LARDONS_NAME);
    }

    public static Ingredient watercress() {
        return new Ingredient(WATERCRESS_ID, WATERCRESS_NAME);
    }

    public static Ingredient greenPepper() {
        return new Ingredient(GREEN_PEPPER_ID, GREEN_PEPPER_NAME);
    }

    public static Ingredient lambLettuce() {
        return new Ingredient(LAMB_LETTUCE_ID, LAMB_LETTUCE_NAME);
    }

    public static Ingredient crouton() {
        return new Ingredient(CROUTON_ID, CROUTON_NAME);
    }

    public static Ingredient salmon() {
        return new Ingredient(SALMON_ID, SALMON_NAME);
    }

    public static Ingredient kidneyBean() {
        return new Ingredient(KIDNEY_BEAN_ID, KIDNEY_BEAN_NAME);
    }

    public static Ingredient mincedBeef() {
        return new Ingredient(MINCED_BEEF_ID, MINCED_BEEF_NAME);
    }

    public static Ingredient redPepper() {
        return new Ingredient(RED_PEPPER_ID, RED_PEPPER_NAME);
    }

    public static Ingredient sauerkraut() {
        return new Ingredient(SAUERKRAUT_ID, SAUERKRAUT_NAME);
    }

    public static Ingredient frankfurter() {
        return new Ingredient(FRANKFURTER_ID, FRANKFURTER_NAME);
    }

    public static Ingredient leek() {
        return new Ingredient(LEEK_ID, LEEK_NAME);
    }

    public static Ingredient ham() {
        return new Ingredient(HAM_ID, HAM_NAME);
    }

    public static Ingredient bechamel() {
        return new Ingredient(BECHAMEL_ID, BECHAMEL_NAME);
    }

    public static Ingredient aubergine() {
        return new Ingredient(AUBERGINE_ID, AUBERGINE_NAME);
    }

    public static Ingredient mincedMutton() {
        return new Ingredient(MINCED_MUTTON_ID, MINCED_MUTTON_NAME);
    }

    public static Ingredient pasta() {
        return new Ingredient(PASTA_ID, PASTA_NAME);
    }

    public static Ingredient carrot() {
        return new Ingredient(CARROT_ID, CARROT_NAME);
    }

    public static Ingredient duckBreast() {
        return new Ingredient(DUCK_BREAST_ID, DUCK_BREAST_NAME);
    }

    public static Ingredient turnip() {
        return new Ingredient(TURNIP_ID, TURNIP_NAME);
    }

    public static Ingredient honey() {
        return new Ingredient(HONEY_ID, HONEY_NAME);
    }

    public static Ingredient bread() {
        return new Ingredient(BREAD_ID, BREAD_NAME);
    }

    public static Ingredient cheese() {
        return new Ingredient(CHEESE_ID, CHEESE_NAME);
    }

    public static Ingredient bacon() {
        return new Ingredient(BACON_ID, BACON_NAME);
    }

    public static Ingredient confitOfDuckLeg() {
        return new Ingredient(CONFIT_OF_DUCK_LEG_ID, CONFIT_OF_DUCK_LEG_NAME);
    }

    public static List<Ingredient> allIngredients() {
        return Arrays.asList(tomato(), cucumber(), potato(), mozzarella(), olive(), feta(), mustard(), cantal(),
                shortcrust(), egg(), milk(), lardons(), watercress(), greenPepper(), lambLettuce(), crouton(), salmon(),
                kidneyBean(), mincedBeef(), redPepper(), sauerkraut(), frankfurter(), leek(), ham(), bechamel(),
                aubergine(), mincedMutton(), pasta(), carrot(), duckBreast(), turnip(), honey(), bread(), cheese(),
                bacon(), confitOfDuckLeg());
    }

    public static Optional<Ingredient> ingredient(IngredientId id) {
        return allIngredients().stream().filter(i -> i.id().equals(id)).findFirst();
    }

}
