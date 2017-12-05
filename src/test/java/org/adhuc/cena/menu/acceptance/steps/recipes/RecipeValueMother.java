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
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.chiliConCarne;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.chiliConCarneIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.croqueMonsieur;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.croqueMonsieurIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.duckBreastFilletWithTurnips;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.duckBreastFilletWithTurnipsIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.duckParmentier;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.duckParmentierIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.gazpacho;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.gazpachoIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.lasagne;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.lasagneIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.leeksWithHamAndBechamelSauce;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.leeksWithHamAndBechamelSauceIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.moussaka;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.moussakaIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.norvegianSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.norvegianSaladIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.omelette;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.omeletteIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.poachedEggsSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.poachedEggsSaladIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.quicheLorraine;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.quicheLorraineIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.raclette;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.racletteIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.sauerkraut;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.sauerkrautIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCantalPie;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCantalPieIngredients;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberOliveFetaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.watercressSoup;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.watercressSoupIngredients;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.adhuc.cena.menu.acceptance.steps.serenity.recipes.RecipeIngredientValue;
import org.adhuc.cena.menu.acceptance.steps.serenity.recipes.RecipeValue;
import org.adhuc.cena.menu.domain.model.recipe.Recipe;
import org.adhuc.cena.menu.domain.model.recipe.ingredient.RecipeIngredientId;

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
        List<RecipeValue> existingRecipes = new ArrayList<>();
        existingRecipes.add(convert(tomatoCantalPie(), tomatoCantalPieIngredients()));
        existingRecipes.add(convert(quicheLorraine(), quicheLorraineIngredients()));
        existingRecipes.add(convert(watercressSoup(), watercressSoupIngredients()));
        existingRecipes.add(convert(gazpacho(), gazpachoIngredients()));
        existingRecipes.add(convert(poachedEggsSalad(), poachedEggsSaladIngredients()));
        existingRecipes.add(convert(norvegianSalad(), norvegianSaladIngredients()));
        existingRecipes.add(convert(omelette(), omeletteIngredients()));
        existingRecipes.add(convert(chiliConCarne(), chiliConCarneIngredients()));
        existingRecipes.add(convert(sauerkraut(), sauerkrautIngredients()));
        existingRecipes.add(convert(leeksWithHamAndBechamelSauce(), leeksWithHamAndBechamelSauceIngredients()));
        existingRecipes.add(convert(moussaka(), moussakaIngredients()));
        existingRecipes.add(convert(lasagne(), lasagneIngredients()));
        existingRecipes.add(convert(duckBreastFilletWithTurnips(), duckBreastFilletWithTurnipsIngredients()));
        existingRecipes.add(convert(croqueMonsieur(), croqueMonsieurIngredients()));
        existingRecipes.add(convert(raclette(), racletteIngredients()));
        existingRecipes.add(convert(duckParmentier(), duckParmentierIngredients()));
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

    private static RecipeValue convert(Recipe recipe, List<RecipeIngredientId> ingredientIds) {
        RecipeValue recipeValue = convert(recipe);
        ingredientIds.forEach(i -> recipeValue.ingredients()
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
