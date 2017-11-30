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

import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.chiliConCarne;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.croqueMonsieur;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.duckBreastFilletWithTurnips;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.duckParmentier;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.gazpacho;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.lasagne;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.leeksWithHamAndBechamelSauce;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.moussaka;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.norvegianSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.omelette;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.poachedEggsSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.quicheLorraine;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.raclette;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.sauerkraut;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCantalPie;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberOliveFetaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.watercressSoup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.adhuc.cena.menu.acceptance.steps.serenity.recipes.RecipeValue;
import org.adhuc.cena.menu.domain.model.recipe.Recipe;

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
        List<RecipeValue> existingRecipes = convertAll(tomatoCantalPie(), quicheLorraine(), watercressSoup(),
                gazpacho(), poachedEggsSalad(), norvegianSalad(), omelette(), chiliConCarne(), sauerkraut(),
                leeksWithHamAndBechamelSauce(), moussaka(), lasagne(), duckBreastFilletWithTurnips(), croqueMonsieur(),
                raclette(), duckParmentier());
        addAllRecipes(existingRecipes, EXISTING_RECIPES);
        addAllRecipes(existingRecipes, ALL_RECIPES);

        List<RecipeValue> recipesUsedInTests = convertAll(tomatoCucumberMozzaSalad(), tomatoCucumberOliveFetaSalad());
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

    private static RecipeValue convert(Recipe recipe) {
        return new RecipeValue(recipe.name(), recipe.content());
    }

    private static List<RecipeValue> convertAll(Recipe... recipes) {
        return Arrays.asList(recipes).stream().map(r -> convert(r)).collect(Collectors.toList());
    }

    private static void addAllRecipes(List<RecipeValue> recipes, Map<String, RecipeValue> map) {
        recipes.stream().forEach(r -> map.put(r.name(), r));
    }

}
