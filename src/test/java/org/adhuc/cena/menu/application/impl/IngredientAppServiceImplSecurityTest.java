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
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.createTomato;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.cucumber;
import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.tomato;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.adhuc.cena.menu.application.IngredientAppService;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientRepository;

/**
 * The {@link IngredientAppServiceImpl} security tests.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("integration")
@Tag("appService")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Ingredient service with security")
public class IngredientAppServiceImplSecurityTest {

    @Autowired
    private IngredientAppService service;
    @Autowired
    private IngredientRepository repository;

    @BeforeEach
    public void setUp() {
        repository.save(cucumber());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("grants access to ingredients list to anonymous user")
    public void getIngredientsAsAnonymous() {
        assertThat(service.getIngredients()).usingFieldByFieldElementComparator().contains(cucumber());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("grants access to ingredients list to authenticated user")
    public void getIngredientsAsAuthenticatedUser() {
        assertThat(service.getIngredients()).usingFieldByFieldElementComparator().contains(cucumber());
    }

    @Test
    @WithMockUser(roles = "INGREDIENT_MANAGER")
    @DisplayName("grants access to ingredients list to ingredient manager")
    public void getIngredientsAsIngredientManager() {
        assertThat(service.getIngredients()).usingFieldByFieldElementComparator().contains(cucumber());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("refuses access to ingredient creation to anonymous user")
    public void createIngredientAsAnonymous() {
        assertThrows(AccessDeniedException.class, () -> service.createIngredient(createTomato()));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("refuses access to ingredient creation to authenticated user")
    public void createIngredientAsAuthenticatedUser() {
        assertThrows(AccessDeniedException.class, () -> service.createIngredient(createTomato()));
    }

    @Test
    @WithMockUser(roles = "INGREDIENT_MANAGER")
    @DisplayName("grants access to ingredient creation to ingredient manager")
    public void createIngredientAsIngredientManager() {
        service.createIngredient(createTomato());
        assertThat(service.getIngredients()).usingFieldByFieldElementComparator().contains(tomato());
    }

}
