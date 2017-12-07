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
package org.adhuc.cena.menu.domain.model.recipe;

import static java.util.Collections.unmodifiableCollection;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.adhuc.cena.menu.domain.model.BasicEntity;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientId;
import org.adhuc.cena.menu.domain.model.recipe.ingredient.IngredientNotLinkedToRecipeException;
import org.adhuc.cena.menu.domain.model.recipe.ingredient.RecipeIngredientId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * A recipe definition, consisting of a name and a content.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Data
@EqualsAndHashCode(callSuper = true, exclude = { "name", "content", "author", "ingredients" })
@ToString(callSuper = true)
@Accessors(fluent = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Recipe extends BasicEntity<RecipeId> {

    @NonNull
    private String                        name;
    @NonNull
    private String                        content;
    @NonNull
    private final RecipeAuthor            author;
    @JsonIgnore
    private final Set<RecipeIngredientId> ingredients;

    /**
     * Creates a recipe.
     *
     * @param id
     *            the recipe identity.
     *
     * @param name
     *            the recipe name.
     *
     * @param content
     *            the recipe content.
     */
    public Recipe(@NonNull RecipeId id, @NonNull String name, @NonNull String content, @NonNull RecipeAuthor author) {
        this(id, name, content, author, Collections.emptyList());
    }

    protected Recipe(@NonNull RecipeId id, @NonNull String name, @NonNull String content, @NonNull RecipeAuthor author,
            @NonNull Collection<RecipeIngredientId> ingredients) {
        super(id);
        hasText(name, "Cannot create recipe with invalid name");
        hasText(content, "Cannot create recipe with invalid content");
        this.name = name;
        this.content = content;
        this.author = author;
        this.ingredients = new HashSet<>(ingredients);
    }

    /**
     * Sets the recipe name.
     *
     * @param name
     *            the new recipe name.
     */
    public Recipe name(String name) {
        hasText(name, "Cannot change name with invalid value");
        this.name = name;
        return this;
    }

    /**
     * Sets the recipe content.
     *
     * @param content
     *            the new recipe content.
     */
    public Recipe content(String content) {
        hasText(content, "Cannot change content with invalid value");
        this.content = content;
        return this;
    }

    /**
     * Gets the ingredients (unmodifiable).
     *
     * @return the ingredients.
     */
    public Collection<RecipeIngredientId> ingredients() {
        return unmodifiableCollection(ingredients);
    }

    /**
     * Gets the ingredient corresponding to the specified identity.
     *
     * @param ingredientId
     *            the ingredient identity.
     *
     * @return the ingredient.
     *
     * @throws IngredientNotLinkedToRecipeException
     *             if the ingredient is not linked to the recipe.
     */
    public RecipeIngredientId ingredient(IngredientId ingredientId) {
        Optional<RecipeIngredientId> ingredient =
                ingredients.stream().filter(i -> i.ingredientId().equals(ingredientId)).findFirst();
        if (ingredient.isPresent()) {
            return ingredient.get();
        }
        throw new IngredientNotLinkedToRecipeException(id(), ingredientId);
    }

    /**
     * Adds the ingredient corresponding to the specified identity to the ingredients list.
     *
     * @param ingredient
     *            the ingredient.
     *
     * @return {@code true} if the ingredients list changed, {@code false} otherwise.
     */
    public boolean addIngredient(RecipeIngredientId ingredient) {
        notNull(ingredient, "Cannot add invalid ingredient to recipe");
        Optional<RecipeIngredientId> existingIngredient =
                ingredients.stream().filter(i -> i.ingredientId().equals(ingredient.ingredientId())).findFirst();
        boolean changedOrUnknown =
                existingIngredient.isPresent() && !Objects.equals(existingIngredient.get(), ingredient)
                        ? ingredients.remove(existingIngredient.get()) : true;
        return changedOrUnknown ? ingredients.add(ingredient) : false;
    }

}
