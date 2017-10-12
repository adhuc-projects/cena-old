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
package org.adhuc.cena.menu.port.adapter.rest.ingredient;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.lang.reflect.Method;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.adhuc.cena.menu.application.IngredientAppService;
import org.adhuc.cena.menu.domain.model.ingredient.Ingredient;
import org.adhuc.cena.menu.domain.model.ingredient.IngredientId;
import org.adhuc.cena.menu.port.adapter.rest.InvalidRestRequestException;
import org.adhuc.cena.menu.port.adapter.rest.support.ListResource;

import lombok.extern.slf4j.Slf4j;

/**
 * A REST controller exposing /api/ingredients resource.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/ingredients", produces = HAL_JSON_VALUE)
public class IngredientsController {

    private IngredientAppService        ingredientAppService;
    private IngredientResourceAssembler resourceAssembler;

    private Method                      listMethod;

    @Autowired
    public IngredientsController(IngredientAppService ingredientAppService,
            IngredientResourceAssembler resourceAssembler) {
        this.ingredientAppService = ingredientAppService;
        this.resourceAssembler = resourceAssembler;
    }

    /**
     * Initializes the methods to get links for resources.
     */
    @PostConstruct
    public void initMethodsForLinks() throws Exception {
        listMethod = IngredientsController.class.getMethod("getIngredients");
    }

    /**
     * Gets the ingredient information for all ingredients.
     *
     * @return the ingredient information for all ingredients.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ListResource<IngredientResource> getIngredients() {
        final Collection<Ingredient> ingredients = ingredientAppService.getIngredients();
        return new ListResource<>(resourceAssembler.toResources(ingredients)).withSelfRef(listMethod);
    }

    /**
     * Creates an ingredient based on the specified request.
     *
     * @param request
     *            the request to create an ingredient.
     *
     * @return the response headers containing the ingredient resource location.
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public HttpHeaders createIngredient(@RequestBody @Valid final CreateIngredientRequest request,
            BindingResult bindingResult) {
        validateRequest(bindingResult);
        final IngredientId identity = IngredientId.generate();
        ingredientAppService.createIngredient(request.toCommand(identity));

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(IngredientController.class, identity.toString()).toUri());
        return httpHeaders;
    }

    private void validateRequest(final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.debug("Request validation raises errors : {}", bindingResult);
            throw new InvalidRestRequestException(bindingResult);
        }
    }

}
