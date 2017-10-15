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

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.CUCUMBER_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_NAME;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.cucumber;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.tomato;

import org.junit.Before;
import org.junit.Test;

import org.adhuc.cena.menu.domain.model.ingredient.Ingredient;

/**
 * The {@link InMemoryIngredientRepository} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public class InMemoryIngredientRepositoryTest {

    private InMemoryIngredientRepository repository;

    @Before
    public void setUp() {
        repository = new InMemoryIngredientRepository();
    }

    @Test
    public void findAllEmpty() {
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    public void findAllAfterSaveContainsSavedIngredient() {
        repository.save(tomato());
        assertThat(repository.findAll()).containsExactly(tomato());
    }

    @Test
    public void findAllAfterMultipleSaveContainsSavedIngredients() {
        repository.save(tomato());
        repository.save(cucumber());
        assertThat(repository.findAll()).containsExactlyInAnyOrder(tomato(), cucumber());
    }

    @Test
    public void findOneNotExisting() {
        repository.save(cucumber());
        assertThat(repository.findOne(TOMATO_ID)).isNotPresent();
    }

    @Test
    public void findOneExisting() {
        Ingredient tomato = tomato();
        repository.save(tomato);
        assertThat(repository.findOne(TOMATO_ID)).isPresent().contains(tomato);
    }

    @Test
    public void findOneByNameNotExisting() {
        repository.save(cucumber());
        assertThat(repository.findOneByName(TOMATO_NAME)).isNotPresent();
    }

    @Test
    public void findOneByNameExisting() {
        Ingredient tomato = tomato();
        repository.save(tomato);
        assertThat(repository.findOneByName(TOMATO_NAME)).isPresent().contains(tomato);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNullIngredient() {
        repository.save(null);
    }

    @Test
    public void saveExistingIngredientOverwritePreviousValue() {
        repository.save(tomato());

        Ingredient ingredient = cucumber();
        repository.save(ingredient);
        ingredient.name(TOMATO_NAME);
        repository.save(ingredient);
        assertThat(repository.findAll()).containsExactlyInAnyOrder(tomato(), new Ingredient(CUCUMBER_ID, TOMATO_NAME));
    }

}
