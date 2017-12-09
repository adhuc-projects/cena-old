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

import static org.adhuc.cena.menu.domain.model.ingredient.IngredientMother.cucumber;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.addCucumberToTomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.cucumberInTomatoCucumberMozzaSalad;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSalad;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.adhuc.cena.menu.application.RecipeIngredientAppService;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientRepository;
import org.adhuc.cena.menu.domain.model.recipe.RecipeId;
import org.adhuc.cena.menu.domain.model.recipe.RecipeRepository;
import org.adhuc.cena.menu.domain.model.recipe.ingredient.RecipeIngredient;
import org.adhuc.cena.menu.support.security.WithCommunityUser;

/**
 * The {@link RecipeIngredientAppServiceImpl} security tests.
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
@DisplayName("Recipe ingredient service with security")
public class RecipeIngredientAppServiceImplSecurityTest {

    @Autowired
    private RecipeIngredientAppService service;
    @Autowired
    private RecipeRepository           recipRepository;
    @Autowired
    private IngredientRepository       ingredientRepository;

    @BeforeEach
    public void setUp() {
        recipRepository.save(tomatoCucumberMozzaSalad());
        ingredientRepository.save(cucumber());
    }

    @Test
    @WithCommunityUser
    @DisplayName("refuses access to ingredient to recipe addition to community user")
    public void addIngredientToRecipeAsCommunityUser() {
        assertThrows(AccessDeniedException.class,
                () -> service.addIngredientToRecipe(addCucumberToTomatoCucumberMozzaSalad()));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("refuses access to ingredient to recipe addition to authenticated user that is not the recipe author")
    public void addIngredientToRecipeAsAuthenticatedUserNotRecipeAuthor() {
        assertThrows(AccessDeniedException.class,
                () -> service.addIngredientToRecipe(addCucumberToTomatoCucumberMozzaSalad()));
    }

    @Test
    @WithMockUser(username = "authenticated-user", roles = "USER")
    @DisplayName("grants access to ingredient to recipe addition to authenticated user that is not the recipe author")
    public void addIngredientToRecipeAsRecipeAuthor() {
        RecipeId recipeId = TOMATO_CUCUMBER_MOZZA_SALAD_ID;
        RecipeIngredient cucumber = new RecipeIngredient(recipeId, cucumberInTomatoCucumberMozzaSalad(), cucumber());

        service.addIngredientToRecipe(addCucumberToTomatoCucumberMozzaSalad());
        assertThat(service.getRecipeIngredients(recipeId)).usingFieldByFieldElementComparator().contains(cucumber);
    }

}
