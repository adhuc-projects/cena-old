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

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.ingredient;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.caesarSaladWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.chiliConCarneWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.croqueMonsieurWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.duckBreastFilletWithTurnipsWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.duckParmentierWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.gazpachoWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.japaneseTunaWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.lasagneWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.leeksWithHamAndBechamelSauceWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.moussakaWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.norvegianSaladWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.omeletteWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.poachedEggsSaladWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.quicheLorraineWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.racletteWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.sauerkrautWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCantalPieWithIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberOliveFetaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.watercressSoupWithIngredients;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.adhuc.cena.menu.acceptance.steps.serenity.recipes.RecipeIngredientValue;
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

    private static final Map<String, RecipeValue> EXISTING_RECIPES = new LinkedHashMap<>();
    private static final Map<String, RecipeValue> ALL_RECIPES      = new LinkedHashMap<>();

    static {
        List<RecipeValue> existingRecipes =
                convertAll(tomatoCantalPieWithIngredients(), watercressSoupWithIngredients(),
                        poachedEggsSaladWithIngredients(), sauerkrautWithIngredients(), quicheLorraineWithIngredients(),
                        chiliConCarneWithIngredients(), leeksWithHamAndBechamelSauceWithIngredients(),
                        duckBreastFilletWithTurnipsWithIngredients(), racletteWithIngredients(),
                        gazpachoWithIngredients(), norvegianSaladWithIngredients(), omeletteWithIngredients(),
                        moussakaWithIngredients(), lasagneWithIngredients(), croqueMonsieurWithIngredients(),
                        duckParmentierWithIngredients(), caesarSaladWithIngredients(), japaneseTunaWithIngredients());
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
        RecipeValue recipeValue = new RecipeValue(recipe.name(), recipe.content());
        recipe.ingredients().stream().forEach(i -> recipeValue.ingredients()
                .add(new RecipeIngredientValue(ingredient(i.ingredientId()).get().name(), i.isMainIngredient())));
        return recipeValue;
    }

    private static List<RecipeValue> convertAll(Recipe... recipes) {
        return Arrays.asList(recipes).stream().map(r -> convert(r)).collect(Collectors.toList());
    }

    private static void addAllRecipes(List<RecipeValue> recipes, Map<String, RecipeValue> map) {
        recipes.stream().forEach(r -> map.put(r.name(), r));
    }

}
