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
package org.adhuc.cena.menu.application.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.CUCUMBER_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.cucumber;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.mozzarella;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.addCucumberToTomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.addMozzaToTomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.cucumberInTomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.mozzaInTomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSalad;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.adhuc.cena.menu.domain.model.EntityNotFoundException;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientId;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientRepository;
import org.adhuc.cena.menu.domain.model.recipe.RecipeId;
import org.adhuc.cena.menu.domain.model.recipe.RecipeRepository;
import org.adhuc.cena.menu.domain.model.recipe.ingredient.RecipeIngredient;
import org.adhuc.cena.menu.domain.model.recipe.ingredient.RecipeIngredientAdditionService;
import org.adhuc.cena.menu.port.adapter.persistence.memory.InMemoryIngredientRepository;
import org.adhuc.cena.menu.port.adapter.persistence.memory.InMemoryRecipeRepository;

/**
 * The {@link RecipeIngredientAppServiceImpl} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("appService")
@DisplayName("Recipe ingredient service")
public class RecipeIngredientAppServiceImplTest {

    private RecipeRepository                recipeRepository;
    private IngredientRepository            ingredientRepository;
    private RecipeIngredientAdditionService recipeIngredientAdditionService;
    private RecipeIngredientAppServiceImpl  service;

    @BeforeEach
    public void setUp() {
        recipeRepository = new InMemoryRecipeRepository();
        ingredientRepository = new InMemoryIngredientRepository();
        recipeIngredientAdditionService = new RecipeIngredientAdditionService(recipeRepository, ingredientRepository);
        service = new RecipeIngredientAppServiceImpl(recipeIngredientAdditionService, recipeRepository,
                ingredientRepository);

        recipeRepository.save(tomatoCucumberMozzaSalad());
        ingredientRepository.save(cucumber());
        ingredientRepository.save(mozzarella());
    }

    @Test
    @DisplayName("get ingredient from unknown recipe")
    public void getIngredientFromUnknownRecipe() {
        assertThrows(EntityNotFoundException.class,
                () -> service.getRecipeIngredient(RecipeId.generate(), CUCUMBER_ID));
    }

    @Test
    @DisplayName("get unknown ingredient from recipe")
    public void getUnknownIngredientFromRecipe() {
        assertThrows(EntityNotFoundException.class,
                () -> service.getRecipeIngredient(TOMATO_CUCUMBER_MOZZA_SALAD_ID, IngredientId.generate()));
    }

    @Test
    @DisplayName("throws IllegalArgumentException when adding ingredient to recipe from null command")
    public void addIngredientToRecipeNullCommand() {
        assertThrows(IllegalArgumentException.class, () -> service.addIngredientToRecipe(null));
    }

    @Test
    @DisplayName("adds ingredient to recipe and retrieve the recipe ingredients containing added ingredient")
    public void addIngredientToRecipeRetrieveRecipeIngredientsContainingIngredient() {
        RecipeId recipeId = TOMATO_CUCUMBER_MOZZA_SALAD_ID;
        RecipeIngredient cucumber = new RecipeIngredient(recipeId, cucumberInTomatoCucumberMozzaSalad(), cucumber());
        assumeFalse(service.getRecipeIngredients(recipeId).contains(cucumber));
        service.addIngredientToRecipe(addCucumberToTomatoCucumberMozzaSalad());
        assertThat(service.getRecipeIngredients(recipeId)).contains(cucumber);
        assertThat(service.getRecipeIngredient(recipeId, CUCUMBER_ID)).isEqualTo(cucumber);
    }

    @Test
    @DisplayName("retrieves all the ingredients after addition")
    public void retrieveAllIngredientsAfterAddition() {
        RecipeId recipeId = TOMATO_CUCUMBER_MOZZA_SALAD_ID;
        RecipeIngredient cucumber = new RecipeIngredient(recipeId, cucumberInTomatoCucumberMozzaSalad(), cucumber());
        RecipeIngredient mozza = new RecipeIngredient(recipeId, mozzaInTomatoCucumberMozzaSalad(), mozzarella());
        service.addIngredientToRecipe(addCucumberToTomatoCucumberMozzaSalad());
        service.addIngredientToRecipe(addMozzaToTomatoCucumberMozzaSalad());
        assertThat(service.getRecipeIngredients(recipeId)).containsExactlyInAnyOrder(cucumber, mozza);
    }

}
