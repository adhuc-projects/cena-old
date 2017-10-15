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

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_ID;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.TOMATO_NAME;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.createCucumber;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.createTomato;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.cucumber;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.tomato;

import org.junit.Before;
import org.junit.Test;

import org.adhuc.cena.menu.domain.model.ingredient.CreateIngredient;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientId;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientNameAlreadyUsedException;
import org.adhuc.cena.menu.port.adapter.persistence.memory.InMemoryIngredientRepository;

/**
 * The {@link IngredientAppServiceImpl} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public class IngredientAppServiceImplTest {

    private IngredientAppServiceImpl service;

    @Before
    public void setUp() {
        service = new IngredientAppServiceImpl(new InMemoryIngredientRepository());
    }

    @Test
    public void getIngredientsEmpty() {
        assertThat(service.getIngredients()).isEmpty();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getIngredientsIsNotModifiable() {
        service.createIngredient(createTomato());
        service.getIngredients().add(tomato());
    }

    @Test
    public void getIngredientsContainsCreatedIngredient() {
        service.createIngredient(createTomato());
        assertThat(service.getIngredients()).isNotEmpty().containsExactly(tomato());
    }

    @Test
    public void getIngredientsContainsAllCreatedIngredients() {
        service.createIngredient(createTomato());
        service.createIngredient(createCucumber());
        assertThat(service.getIngredients()).isNotEmpty().containsExactlyInAnyOrder(tomato(), cucumber());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getIngredientNullId() {
        service.getIngredient(null);
    }

    @Test
    public void getUnknownIngredient() {
        assertThat(service.getIngredient(TOMATO_ID)).isEmpty();
    }

    @Test
    public void getCreatedIngredient() {
        service.createIngredient(createTomato());
        assertThat(service.getIngredient(TOMATO_ID)).isNotEmpty().contains(tomato());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createIngredientNullCommand() {
        service.createIngredient(null);
    }

    @Test(expected = IngredientNameAlreadyUsedException.class)
    public void createIngredientWithAlreadyUsedName() {
        service.createIngredient(new CreateIngredient(IngredientId.generate(), TOMATO_NAME));
        service.createIngredient(new CreateIngredient(IngredientId.generate(), TOMATO_NAME));
    }

}
