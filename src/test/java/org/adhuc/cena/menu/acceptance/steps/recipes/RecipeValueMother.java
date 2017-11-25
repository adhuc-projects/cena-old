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
package org.adhuc.cena.menu.acceptance.steps.recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.adhuc.cena.menu.acceptance.steps.serenity.recipes.RecipeValue;

/**
 * An object mother to create testing elements related to {@link RecipeValue}s.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 *
 * @see https://www.martinfowler.com/bliki/ObjectMother.html
 */
public class RecipeValueMother {

    private static final Map<String, RecipeValue> EXISTING_RECIPES = new HashMap<>();
    private static final Map<String, RecipeValue> ALL_RECIPES      = new HashMap<>();

    static {
        List<RecipeValue> existingRecipes = Arrays.asList(tomatoCantalPie(), quicheLorraine(), watercressSoup(),
                gazpacho(), poachedEggsSalad(), norvegianSalad(), omelette(), chiliConCarne(), sauerkraut(),
                leeksWithHamAndBechamelSauce(), moussaka(), lasagne(), duckBreastFilletWithTurnips(), croqueMonsieur(),
                raclette(), duckParmentier());
        addAllRecipes(existingRecipes, EXISTING_RECIPES);
        addAllRecipes(existingRecipes, ALL_RECIPES);

        List<RecipeValue> recipesUsedInTests =
                Arrays.asList(tomatoCucumberMozzaSalad(), tomatoCucumberOliveFetaSalad());
        addAllRecipes(recipesUsedInTests, ALL_RECIPES);
    }

    public static List<RecipeValue> allRecipes() {
        return new ArrayList<>(ALL_RECIPES.values());
    }

    public static List<RecipeValue> existingRecipes() {
        return new ArrayList<>(EXISTING_RECIPES.values());
    }

    public static Optional<RecipeValue> getRecipe(String recipeName) {
        return Optional.ofNullable(ALL_RECIPES.get(recipeName));
    }

    public static RecipeValue tomatoCucumberMozzaSalad() {
        return new RecipeValue("Tomato, cucumber and mozzarella salad", "Cut everything into dices, mix it, dress it");
    }

    public static RecipeValue tomatoCucumberOliveFetaSalad() {
        return new RecipeValue("Tomato, cucumber, olive and feta salad", "Cut everything into dices, mix it, dress it");
    }

    public static RecipeValue tomatoCantalPie() {
        return new RecipeValue("Tomato and cantal pie",
                "Spread the shortcrust in a pie plate, wrap it with mustard, tomato slices and cantal slices in this order, and bake it during 20 minutes");
    }

    public static RecipeValue quicheLorraine() {
        return new RecipeValue("Quiche lorraine",
                "Spread the shortcrust in a pie plate, beat the eggs with milk, add lardons, pour on the pastry and bake it during 30 minutes");
    }

    public static RecipeValue watercressSoup() {
        return new RecipeValue("Watercress soup", "Watercress soup recipe content");
    }

    public static RecipeValue gazpacho() {
        return new RecipeValue("Gazpacho", "Gazpacho recipe content");
    }

    public static RecipeValue poachedEggsSalad() {
        return new RecipeValue("Poached eggs salad", "Poached eggs salad recipe content");
    }

    public static RecipeValue norvegianSalad() {
        return new RecipeValue("Norvegian salad", "Norvegian salad recipe content");
    }

    public static RecipeValue omelette() {
        return new RecipeValue("Omelette", "Omelette recipe content");
    }

    public static RecipeValue chiliConCarne() {
        return new RecipeValue("Chili con carne", "Chili con carne recipe content");
    }

    public static RecipeValue sauerkraut() {
        return new RecipeValue("Sauerkraut", "Sauerkraut recipe content");
    }

    public static RecipeValue leeksWithHamAndBechamelSauce() {
        return new RecipeValue("Leeks with ham and béchamel sauce", "Leeks with ham and béchamel sauce recipe content");
    }

    public static RecipeValue moussaka() {
        return new RecipeValue("Moussaka", "Moussaka recipe content");
    }

    public static RecipeValue lasagne() {
        return new RecipeValue("Lasagne", "Lasagne recipe content");
    }

    public static RecipeValue duckBreastFilletWithTurnips() {
        return new RecipeValue("Duck breast fillet with turnips", "Duck breast fillet with turnips recipe content");
    }

    public static RecipeValue croqueMonsieur() {
        return new RecipeValue("Croque-monsieur", "Croque-monsieur recipe content");
    }

    public static RecipeValue raclette() {
        return new RecipeValue("Raclette", "Raclette recipe content");
    }

    public static RecipeValue duckParmentier() {
        return new RecipeValue("Duck parmentier", "Duck parmentier recipe content");
    }

    private static void addAllRecipes(List<RecipeValue> recipes, Map<String, RecipeValue> map) {
        recipes.stream().forEach(r -> map.put(r.name(), r));
    }

}
