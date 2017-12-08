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
package org.adhuc.cena.menu.acceptance.steps.serenity.menus;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.hateoas.Link;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.adhuc.cena.menu.acceptance.support.resource.HateoasHalClientResourceSupport;
import org.adhuc.cena.menu.domain.model.menu.MealType;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * A menu value on the client side.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(includeFieldNames = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
@Accessors(fluent = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuValue extends HateoasHalClientResourceSupport {

    private final LocalDate date;
    private final MealType  type;

    public String getRecipeUrl() {
        Optional<Link> link = links().stream().filter(l -> l.getRel().equals("recipe")).findFirst();
        assertThat(link).as("Cannot get recipe link for menu " + this).isPresent();
        return link.get().getHref();
    }

    public boolean isConsecutiveDay(MenuValue other) {
        return Duration.between(date.atStartOfDay(), other.date.atStartOfDay()).abs().toDays() <= 1;
    }

}
