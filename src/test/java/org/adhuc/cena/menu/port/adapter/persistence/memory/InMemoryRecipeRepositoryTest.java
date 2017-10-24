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
package org.adhuc.cena.menu.port.adapter.persistence.memory;

import static org.assertj.core.api.Assertions.assertThat;

import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_OLIVE_FETA_SALAD_NAME;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberOliveFetaSalad;

import org.junit.Before;
import org.junit.Test;

import org.adhuc.cena.menu.domain.model.recipe.Recipe;

/**
 * The {@link InMemoryRecipeRepository} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public class InMemoryRecipeRepositoryTest {

    private InMemoryRecipeRepository repository;

    @Before
    public void setUp() {
        repository = new InMemoryRecipeRepository();
    }

    @Test
    public void findAllEmpty() {
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    public void findAllAfterSaveContainsSavedRecipe() {
        repository.save(tomatoCucumberMozzaSalad());
        assertThat(repository.findAll()).containsExactly(tomatoCucumberMozzaSalad());
    }

    @Test
    public void findAllAfterMultipleSaveContainsSavedRecipes() {
        repository.save(tomatoCucumberMozzaSalad());
        repository.save(tomatoCucumberOliveFetaSalad());
        assertThat(repository.findAll()).containsExactlyInAnyOrder(tomatoCucumberMozzaSalad(),
                tomatoCucumberOliveFetaSalad());
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNullRecipe() {
        repository.save(null);
    }

    @Test
    public void saveExistingRecipeOverwritePreviousValue() {
        repository.save(tomatoCucumberOliveFetaSalad());

        Recipe recipe = tomatoCucumberMozzaSalad();
        repository.save(recipe);
        recipe.name(TOMATO_CUCUMBER_OLIVE_FETA_SALAD_NAME);
        repository.save(recipe);
        assertThat(repository.findAll()).containsExactlyInAnyOrder(tomatoCucumberOliveFetaSalad(),
                new Recipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID, TOMATO_CUCUMBER_OLIVE_FETA_SALAD_NAME,
                        TOMATO_CUCUMBER_MOZZA_SALAD_CONTENT));
    }

}
