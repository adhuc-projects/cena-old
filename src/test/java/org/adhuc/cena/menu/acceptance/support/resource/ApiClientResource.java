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
package org.adhuc.cena.menu.acceptance.support.resource;

import org.springframework.hateoas.Link;

/**
 * A REST resource encapsulating API information on the client side.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public class ApiClientResource extends HateoasHalClientResourceSupport {

    public Link getManagement() {
        return getLink("management");
    }

    public Link getDocumentation() {
        return getLink("documentation");
    }

    public Link getIngredients() {
        return getLink("ingredients");
    }

    public Link getRecipes() {
        return getLink("recipes");
    }

    public Link getMenus() {
        return getLink("menus");
    }

}
