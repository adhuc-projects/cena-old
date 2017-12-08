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

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import org.adhuc.cena.menu.domain.model.recipe.ingredient.RecipeIngredient;

import lombok.NonNull;

/**
 * A {@link org.springframework.hateoas.ResourceAssembler ResourceAssembler} implementation allowing building
 * {@link RecipeIngredientResource}s.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Component
public class RecipeIngredientResourceAssembler
        extends ResourceAssemblerSupport<RecipeIngredient, RecipeIngredientResource> {

    /**
     * Creates a resource assembler for recipe ingredients.
     */
    public RecipeIngredientResourceAssembler() {
        super(RecipeIngredientsController.class, RecipeIngredientResource.class);
    }

    @Override
    public RecipeIngredientResource toResource(@NonNull RecipeIngredient recipeIngredient) {
        RecipeIngredientResource resource = createResourceWithId(recipeIngredient.ingredient().id().toString(),
                recipeIngredient, recipeIngredient.recipeId().toString());
        resource.add(linkTo(RecipeIngredientsController.class, recipeIngredient.recipeId().toString()).withRel("list"));
        return resource;
    }

    @Override
    protected RecipeIngredientResource instantiateResource(RecipeIngredient recipeIngredient) {
        return new RecipeIngredientResource(recipeIngredient);
    }

}
