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

import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_OLIVE_FETA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.createTomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberOliveFetaSalad;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.adhuc.cena.menu.application.RecipeAppService;
import org.adhuc.cena.menu.domain.model.recipe.RecipeRepository;
import org.adhuc.cena.menu.support.security.WithAuthenticatedUser;
import org.adhuc.cena.menu.support.security.WithCommunityUser;

/**
 * The {@link RecipeAppServiceImpl} security tests.
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
@DisplayName("Recipe service with security")
public class RecipeAppServiceImplSecurityTest {

    @Autowired
    private RecipeAppService service;
    @Autowired
    private RecipeRepository repository;

    @BeforeEach
    public void setUp() {
        repository.save(tomatoCucumberOliveFetaSalad());
    }

    @Test
    @WithCommunityUser
    @DisplayName("grants access to recipes list to community user")
    public void getRecipesAsCommunityUser() {
        assertThat(service.getRecipes()).usingFieldByFieldElementComparator().contains(tomatoCucumberOliveFetaSalad());
    }

    @Test
    @WithAuthenticatedUser
    @DisplayName("grants access to recipes list to authenticated user")
    public void getRecipesAsAuthenticatedUser() {
        assertThat(service.getRecipes()).usingFieldByFieldElementComparator().contains(tomatoCucumberOliveFetaSalad());
    }

    @Test
    @WithCommunityUser
    @DisplayName("grants access to recipe detail to community user")
    public void getRecipeAsCommunityUser() {
        assertThat(service.getRecipe(TOMATO_CUCUMBER_OLIVE_FETA_SALAD_ID))
                .isEqualToComparingFieldByField(tomatoCucumberOliveFetaSalad());
    }

    @Test
    @WithAuthenticatedUser
    @DisplayName("grants access to recipe detail to authenticated user")
    public void getRecipeAsAuthenticatedUser() {
        assertThat(service.getRecipe(TOMATO_CUCUMBER_OLIVE_FETA_SALAD_ID))
                .isEqualToComparingFieldByField(tomatoCucumberOliveFetaSalad());
    }

    @Test
    @WithCommunityUser
    @DisplayName("refuses access to recipe creation to community user")
    public void createRecipeAsCommunityUser() {
        assertThrows(AccessDeniedException.class, () -> service.createRecipe(createTomatoCucumberMozzaSalad()));
    }

    @Test
    @WithAuthenticatedUser
    @DisplayName("grants access to recipe creation to authenticated user")
    public void createRecipeAsAuthenticatedUser() {
        service.createRecipe(createTomatoCucumberMozzaSalad());
        assertThat(service.getRecipes()).usingFieldByFieldElementComparator().contains(tomatoCucumberMozzaSalad());
    }

}
