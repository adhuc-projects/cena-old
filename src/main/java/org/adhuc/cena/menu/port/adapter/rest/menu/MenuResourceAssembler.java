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
package org.adhuc.cena.menu.port.adapter.rest.menu;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import org.adhuc.cena.menu.domain.model.menu.Menu;
import org.adhuc.cena.menu.domain.model.menu.MenuId;
import org.adhuc.cena.menu.port.adapter.rest.recipe.RecipeController;

import lombok.NonNull;

/**
 * A {@link org.springframework.hateoas.ResourceAssembler ResourceAssembler} implementation allowing building
 * {@link MenuResource}s.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Component
public class MenuResourceAssembler extends ResourceAssemblerSupport<Menu, MenuResource> {

    /**
     * Creates a resource assembler for menus.
     */
    public MenuResourceAssembler() {
        super(MenusController.class, MenuResource.class);
    }

    @Override
    public MenuResource toResource(@NonNull Menu menu) {
        MenuResource resource = createResourceWithId(menu.id(), menu);
        resource.add(linkTo(RecipeController.class, menu.recipe().toString()).withRel("recipe"));
        return resource;
    }

    @Override
    protected MenuResource instantiateResource(@NonNull Menu menu) {
        return new MenuResource(menu);
    }

    private MenuResource createResourceWithId(MenuId id, Menu menu) {
        MenuResource resource = instantiateResource(menu);
        resource.add(linkTo(MenuController.class, id.date(), id.type()).withSelfRel());
        return resource;
    }

}
