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
package org.adhuc.cena.menu.domain.model.recipe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_OLIVE_FETA_SALAD_ID;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.tomatoCucumberMozzaSalad;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.adhuc.cena.menu.port.adapter.persistence.memory.InMemoryRecipeRepository;

/**
 * The {@link RecipeEditionAuthorizationService} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("domain")
@DisplayName("Recipe edition authorization service")
public class RecipeEditionAuthorizationServiceTest {

    private static UserDetails                USER =
            new User(TOMATO_CUCUMBER_MOZZA_SALAD_AUTHOR.name(), "N/A", Collections.emptyList());

    private RecipeEditionAuthorizationService service;
    private RecipeRepository                  recipeRepository;

    @BeforeEach
    public void setUp() {
        recipeRepository = new InMemoryRecipeRepository();
        recipeRepository.save(tomatoCucumberMozzaSalad());
        service = new RecipeEditionAuthorizationService(recipeRepository);
    }

    @Test
    @DisplayName("throws IllegalArgumentException when checking author from recipe with null identity")
    public void isAuthorNullRecipeId() {
        assertThrows(IllegalArgumentException.class, () -> service.isAuthor(null, USER));
    }

    @Test
    @DisplayName("throws IllegalArgumentException when checking null author from recipe")
    public void isAuthorNullUser() {
        assertThrows(IllegalArgumentException.class, () -> service.isAuthor(TOMATO_CUCUMBER_MOZZA_SALAD_ID, null));
    }

    @Test
    @DisplayName("returns false when checking author from unknown recipe")
    public void isAuthorUnknownRecipe() {
        assertThat(service.isAuthor(TOMATO_CUCUMBER_OLIVE_FETA_SALAD_ID, USER)).isFalse();
    }

    @Test
    @DisplayName("returns true when checking author from recipe created by this author")
    public void isAuthorTrue() {
        assertThat(service.isAuthor(TOMATO_CUCUMBER_MOZZA_SALAD_ID, USER)).isTrue();
    }

    @Test
    @DisplayName("returns false when checking author from recipe created by another author")
    public void isAuthorFalse() {
        assertThat(service.isAuthor(TOMATO_CUCUMBER_MOZZA_SALAD_ID,
                new User("other-user", "N/A", Collections.emptyList()))).isFalse();
    }

}
