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

import static org.springframework.util.Assert.notNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import org.adhuc.cena.menu.domain.model.ingredient.Ingredient;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientId;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * An in-memory {@link IngredientRepository} implementation.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Slf4j
@Repository
public class InMemoryIngredientRepository implements IngredientRepository {

    private Map<IngredientId, Ingredient> ingredients = new HashMap<>();

    @Override
    public List<Ingredient> findAll() {
        return new ArrayList<>(ingredients.values());
    }

    @Override
    public <I extends Ingredient> I save(I ingredient) {
        notNull(ingredient, "Cannot save null ingredient");
        log.debug("Save ingredient {}", ingredient);
        ingredients.put(ingredient.id(), ingredient);
        return ingredient;
    }

}
