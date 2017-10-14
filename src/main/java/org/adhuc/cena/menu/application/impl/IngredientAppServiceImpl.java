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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import org.adhuc.cena.menu.application.IngredientAppService;
import org.adhuc.cena.menu.domain.model.ingredient.CreateIngredient;
import org.adhuc.cena.menu.domain.model.ingredient.Ingredient;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientNameAlreadyUsedException;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * An {@link IngredientAppService} implementation.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Slf4j
@Service
public class IngredientAppServiceImpl implements IngredientAppService {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientAppServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> getIngredients() {
        return ingredientRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('INGREDIENT_MANAGER')")
    public void createIngredient(CreateIngredient command) {
        log.info("Create ingredient from command {}", command);
        ensureIngredientNameNotUsed(command.ingredientName());
        ingredientRepository.save(new Ingredient(command.ingredientId(), command.ingredientName()));
    }

    private void ensureIngredientNameNotUsed(final String ingredientName) {
        if (ingredientRepository.findOneByName(ingredientName).isPresent()) {
            log.debug("Cannot create ingredient with already used name {}", ingredientName);
            throw new IngredientNameAlreadyUsedException(ingredientName);
        }
    }

}
