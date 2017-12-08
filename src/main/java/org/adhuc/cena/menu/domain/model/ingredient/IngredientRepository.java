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
package org.adhuc.cena.menu.domain.model.ingredient;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.adhuc.cena.menu.domain.model.WritableRepository;

/**
 * An {@link Ingredient} repository.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public interface IngredientRepository extends WritableRepository<Ingredient, IngredientId> {

    /**
     * Finds all the ingredients corresponding to the specified identities.
     *
     * @param ingredientIds
     *            the ingredients identities.
     *
     * @return the ingredients corresponding to the identities.
     */
    List<Ingredient> findAll(Collection<IngredientId> ingredientIds);

    /**
     * Finds the ingredient corresponding to the specified name.
     *
     * @param ingredientName
     *            the ingredient name.
     *
     * @return the ingredient if existing, empty otherwise.
     */
    Optional<Ingredient> findOneByName(String ingredientName);

}
