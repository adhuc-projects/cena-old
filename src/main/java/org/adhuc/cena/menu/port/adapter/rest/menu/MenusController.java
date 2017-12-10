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

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.lang.reflect.Method;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.adhuc.cena.menu.application.MenuAppService;
import org.adhuc.cena.menu.domain.model.menu.MenusQuery;
import org.adhuc.cena.menu.port.adapter.rest.AbstractRequestValidationController;
import org.adhuc.cena.menu.port.adapter.rest.support.ListResource;

/**
 * A REST controller exposing /api/menus resource.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@RestController
@RequestMapping(path = "/api/menus", produces = HAL_JSON_VALUE)
public class MenusController extends AbstractRequestValidationController {

    private Clock                 clock;
    private MenuAppService        menuAppService;
    private MenuResourceAssembler resourceAssembler;

    private Method                listMethod;

    public MenusController(Clock clock, MenuAppService menuAppService, MenuResourceAssembler resourceAssembler) {
        this.clock = clock;
        this.menuAppService = menuAppService;
        this.resourceAssembler = resourceAssembler;
    }

    /**
     * Initializes the methods to get links for resources.
     */
    @PostConstruct
    public void initMethodsForLinks() throws Exception {
        listMethod = MenusController.class.getMethod("getMenus", Integer.class, LocalDate.class);
    }

    /**
     * Gets the menus for the specified parameters.
     *
     * @return the menus.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ListResource<MenuResource> getMenus(@RequestParam(name = "days", defaultValue = "1") Integer days,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate startDate) {
        return new ListResource<>(resourceAssembler
                .toResources(menuAppService.getMenus(new MenusQuery(days, startDateOrDefault(startDate)))))
                        .withSelfRef(listMethod, days, startDate);
    }

    /**
     * Generates menus based on the specified request.
     *
     * @param request
     *            the request to generate menus.
     *
     * @return the response headers containing the menus resource location.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HttpHeaders generateMenus(@RequestBody @Valid GenerateMenusRequest request, Errors errors) {
        validateRequest(errors);
        menuAppService.generateMenus(request.toCommand(clock));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(
                linkTo(methodOn(MenusController.class).getMenus(request.getDays(), request.getStartDate())).toUri());
        return httpHeaders;
    }

    private LocalDate startDateOrDefault(LocalDate startDate) {
        return Optional.ofNullable(startDate).orElse(LocalDate.now(clock));
    }

}
