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

import org.hibernate.validator.constraints.NotBlank;

import org.adhuc.cena.menu.domain.model.ingredient.CreateIngredient;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * A request to create an ingredient.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Data
@AllArgsConstructor
@Builder
public class CreateIngredientRequest {

    @NotBlank
    private String name;

    /**
     * Converts this request to a {@code CreateIngredient} command, with specified identity.
     *
     * @param identity
     *            the ingredient identity.
     *
     * @return the ingredient creation command.
     */
    public CreateIngredient toCommand(@NonNull IngredientId identity) {
        return new CreateIngredient(identity, name);
    }

}
