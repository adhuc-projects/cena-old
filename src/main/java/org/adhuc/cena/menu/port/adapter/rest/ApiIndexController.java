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
package org.adhuc.cena.menu.port.adapter.rest;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.util.Assert.notNull;

import static org.adhuc.cena.menu.security.RoleDefinition.INGREDIENT_MANAGER_ROLE;
import static org.adhuc.cena.menu.security.RoleDefinition.ROLE_PREFIX;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.adhuc.cena.menu.configuration.MenuGenerationProperties;
import org.adhuc.cena.menu.configuration.MenuGenerationProperties.Documentation;
import org.adhuc.cena.menu.port.adapter.rest.ingredient.IngredientsController;
import org.adhuc.cena.menu.port.adapter.rest.menu.MenusController;
import org.adhuc.cena.menu.port.adapter.rest.recipe.RecipesController;

/**
 * A REST controller providing links to all general resources.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@RestController
@RequestMapping(value = "/api", produces = HAL_JSON_VALUE)
public class ApiIndexController {

    private final Documentation documentation;

    public ApiIndexController(MenuGenerationProperties menuGenerationProperties) {
        notNull(menuGenerationProperties, "Cannot initialize index controller with null properties");
        documentation = menuGenerationProperties.getDocumentation();
    }

    @GetMapping
    public ResourceSupport index(@AuthenticationPrincipal UserDetails user) {
        final ResourceSupport index = new ResourceSupport();
        if (documentation.isEnabled()) {
            index.add(linkTo(ApiIndexController.class).slash("docs").slash("api-guide.html").withRel("documentation"));
        }
        index.add(linkTo(ApiIndexController.class).slash("management").withRel("management"));
        index.add(linkTo(IngredientsController.class).withRel("ingredients"));
        index.add(linkTo(RecipesController.class).withRel("recipes"));
        addLinksForAuthenticatedUsers(user, index);
        return index;
    }

    private void addLinksForAuthenticatedUsers(UserDetails user, ResourceSupport resource) {
        if (isAuthenticated(user)) {
            resource.add(linkTo(MenusController.class).withRel("menus"));
            addLinksForIngredientManagers(user, resource);
        }
    }

    private void addLinksForIngredientManagers(UserDetails user, ResourceSupport resource) {
        if (isIngredientManager(user)) {
            resource.add(linkTo(IngredientsController.class).withRel("ingredientsManagement"));
        }
    }

    private boolean isAuthenticated(UserDetails user) {
        return Objects.nonNull(user);
    }

    private boolean isIngredientManager(UserDetails user) {
        return getRoles(user).contains(INGREDIENT_MANAGER_ROLE);
    }

    private Set<String> getRoles(UserDetails user) {
        return user.getAuthorities().stream().filter(a -> a.toString() != null && a.toString().startsWith(ROLE_PREFIX))
                .map(a -> a.toString().substring(ROLE_PREFIX.length())).collect(Collectors.toSet());
    }

}
