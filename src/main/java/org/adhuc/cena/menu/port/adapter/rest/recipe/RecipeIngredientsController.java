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

import java.lang.reflect.Method;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.adhuc.cena.menu.application.RecipeIngredientAppService;
import org.adhuc.cena.menu.domain.model.recipe.RecipeId;
import org.adhuc.cena.menu.port.adapter.rest.support.ListResource;

/**
 * A REST controller exposing /api/recipes/{recipeId}/ingredients resource.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@RestController
@RequestMapping(path = "/api/recipes/{recipeId}/ingredients", produces = HAL_JSON_VALUE)
public class RecipeIngredientsController {

    private RecipeIngredientAppService        recipeIngredientAppService;
    private RecipeIngredientResourceAssembler recipeIngredientResourceAssembler;

    private Method                            listMethod;

    public RecipeIngredientsController(RecipeIngredientAppService recipeIngredientAppService,
            RecipeIngredientResourceAssembler recipeIngredientResourceAssembler) {
        this.recipeIngredientAppService = recipeIngredientAppService;
        this.recipeIngredientResourceAssembler = recipeIngredientResourceAssembler;
    }

    /**
     * Initializes the methods to get links for resources.
     */
    @PostConstruct
    public void initMethodsForLinks() throws Exception {
        listMethod = RecipeIngredientsController.class.getMethod("getRecipeIngredients", RecipeId.class);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ListResource<RecipeIngredientResource> getRecipeIngredients(@PathVariable RecipeId recipeId) {
        ListResource<RecipeIngredientResource> resource = new ListResource<>(recipeIngredientResourceAssembler
                .toResources(recipeIngredientAppService.getRecipeIngredients(recipeId))).withSelfRef(listMethod,
                        recipeId);
        resource.add(linkTo(RecipeController.class, recipeId.toString()).withRel("recipe"));
        return resource;
    }

}
