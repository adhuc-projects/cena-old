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
package org.adhuc.cena.menu.acceptance.support.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.Link;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * An abstract REST resource providing HATEOAS support on the client side.
 * <p>
 * Do <b>NOT</b> extend with class directly, but prefer using {@link HateoasHalClientResourceSupport} or
 * {@link HateoasJsonClientResourceSupport}, respectively for application/hal+json or application/json content-types.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public abstract class HateoasClientResourceSupport {

    @JsonIgnore
    public String getId() {
        return getLink(Link.REL_SELF).get();
    }

    public Optional<String> getLink(String rel) {
        for (Link link : links()) {
            if (link.getRel().equals(rel)) {
                return Optional.of(link.getHref());
            }
        }
        return Optional.empty();
    }

    public String getLinkOrDefault(String rel, String defaultValue) {
        return getLink(rel).orElse(defaultValue);
    }

    protected abstract List<Link> links();

}
