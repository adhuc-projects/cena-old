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
package org.adhuc.cena.menu.port.adapter.rest.support;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

/**
 * A resource representing a list of embedded resources.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public class ListResource<R extends ResourceSupport> extends HalResource {

    private static final String EMBEDDED_RESOURCES_RELATIONSHIP = "data";

    /**
     * Creates a list resource embedding the specified list of resources.
     *
     * @param embeddedResources
     */
    public ListResource(final List<R> embeddedResources) {
        embedResource(EMBEDDED_RESOURCES_RELATIONSHIP, embeddedResources);
    }

    /**
     * Adds the self reference to the resource, based on the specified method.
     *
     * @param method
     *            the method corresponding to self reference.
     *
     * @param parameters
     *            the method parameters to use for self reference.
     *
     * @return the resource.
     */
    public ListResource<R> withSelfRef(final Method method, final Object... parameters) {
        this.add(linkTo(method, parameters).withSelfRel());
        return this;
    }

}
