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
package org.adhuc.cena.menu.port.adapter.rest.ingredient;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.endsWith;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.test.web.servlet.ResultActions;

import org.adhuc.cena.menu.domain.model.ingredient.Ingredient;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientId;

/**
 * Provides assertions on ingredients JSON representation.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public class IngredientJsonAssertion {

    private static final String INGREDIENTS_API_URL = "/api/ingredients";

    public static void assertJsonContainsIngredient(final ResultActions resultActions, final String jsonPath,
            final Ingredient ingredient) throws Exception {
        resultActions.andExpect(jsonPath(jsonPath + ".id").exists())
                .andExpect(jsonPath(jsonPath + ".id", equalTo(ingredient.id().toString())))
                .andExpect(jsonPath(jsonPath + ".name").exists())
                .andExpect(jsonPath(jsonPath + ".name", equalTo(ingredient.name())))
                .andExpect(jsonPath(jsonPath + "._links.self.href").exists()).andExpect(
                        jsonPath(jsonPath + "._links.self.href", endsWith(buildIngredientSelfLink(ingredient.id()))));
    }

    public static String buildIngredientSelfLink(IngredientId ingredientId) {
        return INGREDIENTS_API_URL + "/" + ingredientId.id();
    }

}
