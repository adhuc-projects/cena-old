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

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import lombok.NonNull;

/**
 * A resource representing a list of embedded resources.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public class ListResource<R extends ResourceSupport> extends HalResource {

    public static final String EMBEDDED_RESOURCES_RELATIONSHIP = "data";

    /**
     * Creates a list resource embedding the specified list of resources. Relationship name is
     * {@value #EMBEDDED_RESOURCES_RELATIONSHIP}.
     *
     * @param embeddedResources
     *            the embedded resources.
     */
    public ListResource(@NonNull List<R> embeddedResources) {
        this(embeddedResources, EMBEDDED_RESOURCES_RELATIONSHIP);
    }

    /**
     * Creates a list resource embedding the specified list of resources.
     *
     * @param embeddedResources
     *            the embedded resources.
     *
     * @param relationship
     *            the relationship name.
     */
    public ListResource(@NonNull List<R> embeddedResources, String relationship) {
        embedResource(relationship, embeddedResources);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ListResource<R> embedResource(@NonNull String relationship, @NonNull Object resource) {
        return (ListResource<R>) super.embedResource(relationship, resource);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ListResource<R> withSelfRef(@NonNull Method method, @NonNull Object... parameters) {
        return (ListResource<R>) super.withSelfRef(method, parameters);
    }

}
