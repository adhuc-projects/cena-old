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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.time.LocalDate;

import org.adhuc.cena.menu.acceptance.steps.serenity.AbstractServiceClientSteps;
import org.adhuc.cena.menu.domain.model.menu.MealFrequency;
import org.adhuc.cena.menu.port.adapter.rest.menu.GenerateMenusRequest;
import org.adhuc.cena.menu.port.adapter.rest.menu.GenerateMenusRequest.GenerateMenusRequestBuilder;

import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

/**
 * The menus generation rest-service client steps definition.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@SuppressWarnings("serial")
public class MenusGenerationServiceClientSteps extends AbstractServiceClientSteps {

    private GenerateMenusRequestBuilder requestBuilder = GenerateMenusRequest.builder();

    @Step("Specify menus generation days count as {0}")
    public void setMenusGenerationDays(int days) {
        requestBuilder.days(days);
    }

    @Step("Specify menus generation start date as {0}")
    public void setMenusGenerationStartDate(LocalDate startDate) {
        requestBuilder.startDate(startDate);
    }

    @Step("Specify menus generation meal frequency as {0}")
    public void setMenusGenerationMealFrequency(MealFrequency frequency) {
        requestBuilder.frequency(frequency);
    }

    @Step("Generate menus")
    public void generateMenus() {
        RequestSpecification rest = rest();
        String menusResourceUrl = getMenusResourceUrl(rest);
        rest.contentType(APPLICATION_JSON_VALUE).body(requestBuilder.build()).post(menusResourceUrl).andReturn();
    }

    @Step("Assert menus have been successfully generated")
    public void assertMenusSuccessfullyGenerated() {
        assertCreated();
    }

    private String getMenusResourceUrl(RequestSpecification rest) {
        return getApiClientResource(rest).getMenus().getHref();
    }

}
