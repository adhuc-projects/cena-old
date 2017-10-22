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

import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.createTomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.createTomatoCucumberOliveFetaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberOliveFetaSalad;

import org.junit.Before;
import org.junit.Test;

/**
 * The {@link RecipeAppServiceImpl} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public class RecipeAppServiceImplTest {

    private RecipeAppServiceImpl service;

    @Before
    public void setUp() {
        service = new RecipeAppServiceImpl();
    }

    @Test
    public void getRecipesEmpty() {
        assertThat(service.getRecipes()).isEmpty();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getRecipesIsNotModifiable() {
        service.createRecipe(createTomatoCucumberMozzaSalad());
        service.getRecipes().add(tomatoCucumberOliveFetaSalad());
    }

    @Test
    public void getRecipesContainsCreatedRecipe() {
        service.createRecipe(createTomatoCucumberMozzaSalad());
        assertThat(service.getRecipes()).isNotEmpty().containsExactly(tomatoCucumberMozzaSalad());
    }

    @Test
    public void getRecipesContainsAllCreatedRecipes() {
        service.createRecipe(createTomatoCucumberMozzaSalad());
        service.createRecipe(createTomatoCucumberOliveFetaSalad());
        assertThat(service.getRecipes()).isNotEmpty().containsExactlyInAnyOrder(tomatoCucumberMozzaSalad(),
                tomatoCucumberOliveFetaSalad());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createRecipeNullCommand() {
        service.createRecipe(null);
    }

}
