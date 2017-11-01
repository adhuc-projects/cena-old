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
package org.adhuc.cena.menu.acceptance.steps.serenity.ingredients;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.adhuc.cena.menu.acceptance.steps.serenity.AbstractServiceClientSteps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import net.serenitybdd.core.Serenity;

/**
 * An abstract ingredient storage steps definition, providing convenient methods to store ingredient information between
 * steps.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@SuppressWarnings("serial")
public abstract class AbstractIngredientStorageSteps extends AbstractServiceClientSteps {

    private static final String INGREDIENT_SESSION_KEY = "ingredient";

    public final IngredientValue storeIngredient(final IngredientValue ingredient) {
        Serenity.setSessionVariable(INGREDIENT_SESSION_KEY).to(ingredient);
        return ingredient;
    }

    protected final IngredientValue storeIngredientIfEmpty(final IngredientValue ingredient) {
        Optional<IngredientValue> optional = optionalIngredient();
        if (!optional.isPresent()) {
            return storeIngredient(ingredient);
        }
        return optional.get();
    }

    public final IngredientValue ingredient() {
        Optional<IngredientValue> recipe = optionalIngredient();
        assertThat(recipe).isPresent();
        return recipe.get();
    }

    protected final Optional<IngredientValue> optionalIngredient() {
        return Optional.ofNullable(Serenity.sessionVariableCalled(INGREDIENT_SESSION_KEY));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(fluent = true)
    @JsonAutoDetect(fieldVisibility = Visibility.ANY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class IngredientValue {
        private String name;
    }

}
