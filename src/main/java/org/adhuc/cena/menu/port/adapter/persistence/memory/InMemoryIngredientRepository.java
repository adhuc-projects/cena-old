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

import static java.util.Objects.nonNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import org.adhuc.cena.menu.domain.model.ingredient.Ingredient;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientId;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientRepository;

import lombok.NonNull;

/**
 * An in-memory {@link IngredientRepository} implementation.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Repository
@Profile("in-memory")
public class InMemoryIngredientRepository extends AbstractInMemoryRepository<Ingredient, IngredientId>
        implements IngredientRepository {

    @Override
    public Class<Ingredient> entityType() {
        return Ingredient.class;
    }

    @Override
    public List<Ingredient> findAll(@NonNull Collection<IngredientId> ingredientIds) {
        return ingredientIds.stream().map(id -> entities().get(id)).filter(i -> nonNull(i))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Ingredient> findOneByName(@NonNull String ingredientName) {
        return entities().values().stream().filter(i -> i.name().equals(ingredientName)).findFirst();
    }

}
