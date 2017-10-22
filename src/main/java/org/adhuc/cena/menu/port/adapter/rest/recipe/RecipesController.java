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
package org.adhuc.cena.menu.port.adapter.rest.recipe;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.lang.reflect.Method;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.adhuc.cena.menu.application.RecipeAppService;
import org.adhuc.cena.menu.domain.model.recipe.RecipeId;
import org.adhuc.cena.menu.port.adapter.rest.AbstractRequestValidationController;
import org.adhuc.cena.menu.port.adapter.rest.support.ListResource;

/**
 * A REST controller exposing /api/recipes resource.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@RestController
@RequestMapping(path = "/api/recipes", produces = HAL_JSON_VALUE)
public class RecipesController extends AbstractRequestValidationController {

    private RecipeAppService        recipeAppService;
    private RecipeResourceAssembler resourceAssembler;

    private Method                  listMethod;

    @Autowired
    public RecipesController(RecipeAppService recipeAppService, RecipeResourceAssembler resourceAssembler) {
        this.recipeAppService = recipeAppService;
        this.resourceAssembler = resourceAssembler;
    }

    /**
     * Initializes the methods to get links for resources.
     */
    @PostConstruct
    public void initMethodsForLinks() throws Exception {
        listMethod = RecipesController.class.getMethod("getRecipes");
    }

    /**
     * Gets the recipe information for all recipes.
     *
     * @return the recipe information for all recipes.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ListResource<RecipeResource> getRecipes() {
        return new ListResource<>(resourceAssembler.toResources(recipeAppService.getRecipes())).withSelfRef(listMethod);
    }

    /**
     * Creates a recipe based on the specified request.
     *
     * @param request
     *            the request to create a recipe.
     *
     * @return the response headers containing the recipe resource location.
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public HttpHeaders createRecipe(@RequestBody @Valid final CreateRecipeRequest request, Errors errors) {
        validateRequest(errors);
        final RecipeId identity = RecipeId.generate();
        recipeAppService.createRecipe(request.toCommand(identity));

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(RecipeController.class, identity.toString()).toUri());
        return httpHeaders;
    }

}
