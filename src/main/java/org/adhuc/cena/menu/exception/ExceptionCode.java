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
package org.adhuc.cena.menu.exception;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * The exception codes.
 * <p>
 * Exception code ranges are :
 * <ul>
 * <li>[100000-109999] - General errors</li>
 * <li>[200000-209999] - Integration errors</li>
 * <li>...</li>
 * <li>[900000-909999] - Business errors</li>
 * <li>[910000-919999] - Business warnings</li>
 * </ul>
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Getter
@Accessors(fluent = true)
public enum ExceptionCode {

    // General errors
    INTERNAL_ERROR(100000, "Internal error occurred"),
    INVALID_REQUEST(101000, "Invalid request"),

    ENTITY_NOT_FOUND(900000, "Entity not found"),
    INGREDIENT_NAME_ALREADY_USED(900100, "Ingredient name already used"),
    INGREDIENT_NOT_LINKED_TO_RECIPE(900200, "Ingredient is not linked to the recipe"),
    NO_RECIPE_FOR_MENU_GENERATION(900300, "No recipe can be selected for menu generation"),
    NO_MENU_GENERATION_IN_THE_PAST(900301, "Menus generation cannot start in the past");

    private final int    code;
    private final String description;

    /**
     * Constructs an exception code based on the code and description.
     *
     * @param code
     *            the code.
     *
     * @param description
     *            the description.
     */
    private ExceptionCode(final int code, final String description) {
        this.code = code;
        this.description = description;
    }

}
