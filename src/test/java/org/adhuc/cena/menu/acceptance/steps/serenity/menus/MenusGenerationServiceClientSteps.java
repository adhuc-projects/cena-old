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

import static net.serenitybdd.rest.SerenityRest.then;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.adhuc.cena.menu.acceptance.steps.serenity.AbstractServiceClientSteps;
import org.adhuc.cena.menu.domain.model.menu.MealFrequency;
import org.adhuc.cena.menu.domain.model.menu.MealType;
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

    private static final List<DayOfWeek> WEEK_END_DAYS  = Arrays.asList(SATURDAY, SUNDAY);

    private GenerateMenusRequestBuilder  requestBuilder = GenerateMenusRequest.builder();
    private List<MenuValue>              menus;

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

    @Step("Assert there is exactly {0} meals in menus list")
    public void assertExactNumberOfMealsInMenusList(int mealsCount) {
        String menuListUrl = then().extract().header(LOCATION);
        menus = getMenuListFromUrl(menuListUrl);
        assertThat(menus).hasSize(mealsCount);
    }

    @Step("Assert meals distribution corresponds to specifications")
    public void assertMealsDistributionCorrespondToSpecifications() {
        GenerateMenusRequest request = requestBuilder.build();
        for (int i = 0; i < request.getDays(); i++) {
            LocalDate date = request.getStartDate().plusDays(i);
            determineExpectedMealTypes(date, request).stream()
                    .forEach(mealType -> assertMenusContainMealCorrespondingTo(date, mealType));
        }
    }

    @Step("Assert menus contain meal corresponding to date {0} and meal type {1}")
    public void assertMenusContainMealCorrespondingTo(LocalDate date, MealType mealType) {
        assertThat(menus).anySatisfy(menu -> {
            assertThat(menu.date()).isEqualTo(date);
            assertThat(menu.type()).isEqualTo(mealType);
        });
    }

    @Step("Get menu list from {0}")
    public List<MenuValue> getMenuListFromUrl(String menuListUrl) {
        return rest().get(menuListUrl).then().extract().jsonPath().getList("_embedded.data", MenuValue.class);
    }

    private String getMenusResourceUrl(RequestSpecification rest) {
        return getApiClientResource(rest).getMenus().getHref();
    }

    private Collection<MealType> determineExpectedMealTypes(LocalDate date, GenerateMenusRequest request) {
        List<MealType> mealTypes = new ArrayList<>();
        if (expectLunch(date, request)) {
            mealTypes.add(MealType.LUNCH);
        }
        if (expectDinner(date, request)) {
            mealTypes.add(MealType.DINNER);
        }
        return mealTypes;
    }

    private boolean expectLunch(LocalDate date, GenerateMenusRequest request) {
        return MealFrequency.TWICE_A_DAY.equals(request.getFrequency()) || WEEK_END_DAYS.contains(date.getDayOfWeek());
    }

    private boolean expectDinner(LocalDate date, GenerateMenusRequest request) {
        return true;
    }

}
