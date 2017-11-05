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
package org.adhuc.cena.menu.acceptance.steps.serenity.recipes;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeThat;

import net.thucydides.core.annotations.Step;

/**
 * An abstract recipe rest-service client steps definition.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@SuppressWarnings("serial")
public abstract class AbstractRecipeServiceClientSteps extends AbstractRecipeStorageSteps {

    @Step("Assume recipe {0} has been authored by authenticated user {1}")
    public void assumeRecipeAuthoredByAuthenticatedUser(RecipeValue recipe, String authenticatedUser) {
        assumeNotNull(recipe.author());
        assumeThat(recipe.author(), equalTo(authenticatedUser));
    }

    @Step("Assume recipe {0} has been authored by another authenticated user than {1}")
    public void assumeRecipeAuthoredByAnotherUser(RecipeValue recipe, String authenticatedUser) {
        assumeNotNull(recipe.author());
        assumeThat(recipe.author(), not(equalTo(authenticatedUser)));
    }

    protected final String getRecipesResourceUrl() {
        return getApiClientResource().getRecipes().getHref();
    }

}
