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
package org.adhuc.cena.menu.acceptance.support;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.hal.Jackson2HalModule.HalLinkListDeserializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * An abstract REST resource on the client side.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public abstract class AbstractClientResource {

    @JsonProperty("_links")
    @JsonDeserialize(using = HalLinkListDeserializer.class)
    private List<Link> links;

    @JsonIgnore
    public Link getId() {
        return getLink(Link.REL_SELF);
    }

    public Link getLink(final String rel) {
        for (final Link link : links) {
            if (link.getRel().equals(rel)) {
                return link;
            }
        }
        return null;
    }

}
