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

import java.lang.reflect.Method;
import java.util.Collections;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.adhuc.cena.menu.application.RecipeIngredientAppService;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientId;
import org.adhuc.cena.menu.domain.model.recipe.AddIngredientToRecipe;
import org.adhuc.cena.menu.domain.model.recipe.RecipeId;
import org.adhuc.cena.menu.port.adapter.rest.ingredient.IngredientResource;
import org.adhuc.cena.menu.port.adapter.rest.ingredient.IngredientResourceAssembler;
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

    public static final String          RECIPE_INGREDIENTS_RELATIONSHIP      = "ingredients";
    public static final String          RECIPE_MAIN_INGREDIENTS_RELATIONSHIP = "mainIngredients";

    private RecipeIngredientAppService  recipeIngredientAppService;
    private IngredientResourceAssembler ingredientResourceAssembler;

    private Method                      listMethod;

    @Autowired
    public RecipeIngredientsController(RecipeIngredientAppService recipeIngredientAppService,
            IngredientResourceAssembler ingredientResourceAssembler) {
        this.recipeIngredientAppService = recipeIngredientAppService;
        this.ingredientResourceAssembler = ingredientResourceAssembler;
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
    public ListResource<IngredientResource> getRecipeIngredients(@PathVariable RecipeId recipeId) {
        return new ListResource<>(
                ingredientResourceAssembler.toResources(recipeIngredientAppService.getRecipeIngredients(recipeId)),
                RECIPE_INGREDIENTS_RELATIONSHIP)
                        .embedResource(RECIPE_MAIN_INGREDIENTS_RELATIONSHIP, Collections.emptyList())
                        .withSelfRef(listMethod, recipeId);
    }

    @PutMapping("/{ingredientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addIngredientToRecipe(@PathVariable RecipeId recipeId, @PathVariable IngredientId ingredientId) {
        recipeIngredientAppService.addIngredientToRecipe(new AddIngredientToRecipe(recipeId, ingredientId));
    }

}
