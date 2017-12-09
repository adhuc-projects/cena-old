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
package org.adhuc.cena.menu.domain.model.menu.generation;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.web.bind.annotation.ResponseStatus;

import org.adhuc.cena.menu.domain.model.menu.MenuId;
import org.adhuc.cena.menu.exception.CenaException;
import org.adhuc.cena.menu.exception.ExceptionCode;

/**
 * An exception occurring while trying to generate menus and no recipe can be selected.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@SuppressWarnings("serial")
@ResponseStatus(INTERNAL_SERVER_ERROR)
public class NoRecipeForMenuGenerationException extends CenaException {

    private static final ExceptionCode EXCEPTION_CODE = ExceptionCode.NO_RECIPE_FOR_MENU_GENERATION;

    /**
     * Creates a {@code NoRecipeForMenuGenerationException} with message initialized from specified parameters.
     *
     * @param menuId
     *            the menu identity.
     */
    public NoRecipeForMenuGenerationException(MenuId menuId) {
        super("No recipe can be selected for menu " + menuId, EXCEPTION_CODE);
    }

}
