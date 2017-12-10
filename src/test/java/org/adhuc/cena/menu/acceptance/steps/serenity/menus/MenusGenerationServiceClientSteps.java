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

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.api.SoftAssertions;
import org.springframework.http.HttpStatus;

import org.adhuc.cena.menu.acceptance.steps.serenity.AbstractServiceClientSteps;
import org.adhuc.cena.menu.acceptance.steps.serenity.recipes.RecipeDetailServiceClientSteps;
import org.adhuc.cena.menu.acceptance.steps.serenity.recipes.RecipeIngredientValue;
import org.adhuc.cena.menu.acceptance.steps.serenity.recipes.RecipeIngredientsListServiceClientSteps;
import org.adhuc.cena.menu.acceptance.support.authentication.AuthenticationType;
import org.adhuc.cena.menu.domain.model.menu.MealFrequency;
import org.adhuc.cena.menu.domain.model.menu.MealType;
import org.adhuc.cena.menu.exception.ExceptionCode;
import org.adhuc.cena.menu.port.adapter.rest.menu.GenerateMenusRequest;
import org.adhuc.cena.menu.port.adapter.rest.menu.GenerateMenusRequest.GenerateMenusRequestBuilder;

import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

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

    private static final List<DayOfWeek>            WEEK_END_DAYS  = Arrays.asList(SATURDAY, SUNDAY);

    @Steps
    private RecipeDetailServiceClientSteps          recipeDetailServiceClient;
    @Steps
    private RecipeIngredientsListServiceClientSteps recipeIngredientsListServiceClient;

    private GenerateMenusRequestBuilder             requestBuilder = GenerateMenusRequest.builder().days(1)
            .startDate(LocalDate.now()).frequency(MealFrequency.WEEK_WORKING_DAYS);
    private List<MenuValue>                         menus;

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

    @Step("Generate menus for current user based on specifications")
    public void generateMenus() {
        generateMenus(rest(), requestBuilder.build());
    }

    @Step("Generate menus for authenticated user based on specifications")
    public void generateMenusForAuthenticatedUser() {
        generateMenus(restWithAuth(AuthenticationType.AUTHENTICATED_USER), requestBuilder.build());
        assertCreated();
    }

    @Step("Generate menus with {1} specification")
    public void generateMenus(RequestSpecification rest, GenerateMenusRequest request) {
        String menusResourceUrl = getMenusResourceUrl(rest);
        rest.contentType(APPLICATION_JSON_VALUE).body(requestBuilder.build()).post(menusResourceUrl).andReturn();
    }

    @Step("List menus for current user corresponding to specifications")
    public List<MenuValue> listMenus() {
        menus = getMenuListFromUrl(rest(), buildMenuListUrlFromSpecifications(rest()));
        return menus;
    }

    @Step("List menus for authenticated user based on specifications")
    public List<MenuValue> listMenusForAuthenticatedUser() {
        RequestSpecification rest = restWithAuth(AuthenticationType.AUTHENTICATED_USER);
        return getMenuListFromUrl(rest, buildMenuListUrlFromSpecifications(rest));
    }

    @Step("Assert menus have been successfully generated")
    public void assertMenusSuccessfullyGenerated() {
        assertCreated();
    }

    @Step("Assert there is exactly {0} meals in menus list")
    public void assertExactNumberOfMealsInMenusList(int mealsCount) {
        assertThat(listMenus()).hasSize(mealsCount);
    }

    @Step("Assert menus list is empty or different from menus list of authenticated user")
    public void assertDifferentMenusListForCurrentUserAndAuthenticatedUser() {
        assertThat(menus).isNotNull();
        if (!menus.isEmpty()) {
            List<MenuValue> authenticatedUserMenus = listMenusForAuthenticatedUser();
            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(authenticatedUserMenus).isNotEmpty().hasSameSizeAs(menus);
                softly.assertThat(authenticatedUserMenus).usingElementComparator(new MenuValue.MenuValueComparator())
                        .doesNotContainSequence(menus);
            });
        }
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

    @Step("Assert menus do not have redundant recipe")
    public void assertMenusHaveNoRedundantRecipe() {
        SoftAssertions softly = new SoftAssertions();
        menus.stream().forEach(m -> prepareAssertionMenuHaveNoRedundantRecipe(m, softly));
        softly.assertAll();
    }

    @Step("Prepare assertion : menu {0} does not have redundant recipe with other menus")
    public void prepareAssertionMenuHaveNoRedundantRecipe(MenuValue menu, SoftAssertions softly) {
        softly.assertThat(menus.stream().filter(m2 -> !menu.equals(m2))
                .filter(m2 -> menu.getRecipeUrl().equals(m2.getRecipeUrl())))
                .as("Menu %s should have unique recipe", menu).isEmpty();
    }

    @Step("Assert menus do not use same main recipe ingredients within consecutive days")
    public void assertMenusUseNotSameMainIngredientsConsecutiveDays() {
        SoftAssertions softly = new SoftAssertions();
        menus.stream().forEach(m -> prepareAssertionMenuUseNotSameMainIngredientsConsecutiveDays(m, softly));
        softly.assertAll();
    }

    @Step("Prepare assertion : menu {0} does not use same main recipe ingredients within consecutive days")
    public void prepareAssertionMenuUseNotSameMainIngredientsConsecutiveDays(MenuValue menu, SoftAssertions softly) {
        menus.stream().filter(m2 -> !menu.equals(m2) && menu.isConsecutiveDay(m2))
                .forEach(m2 -> softly.assertThat(compareMenusRecipeMainIngredients(menu, m2))
                        .as("Menu %s should not use same main ingredients as %s", menu, m2).isEmpty());
    }

    @Step("Assert menus generation results in no generation in the past error")
    public void assertNoGenerationInThePast() {
        assertException(BAD_REQUEST, ExceptionCode.NO_MENU_GENERATION_IN_THE_PAST);
    }

    @Step("Get menu list from {1}")
    public List<MenuValue> getMenuListFromUrl(RequestSpecification rest, String menuListUrl) {
        return rest().get(menuListUrl).then().statusCode(HttpStatus.OK.value()).extract().jsonPath()
                .getList("_embedded.data", MenuValue.class);
    }

    private String getMenusResourceUrl(RequestSpecification rest) {
        return getApiClientResource(rest).getMenus().getHref();
    }

    private String buildMenuListUrlFromSpecifications(RequestSpecification rest) {
        GenerateMenusRequest request = requestBuilder.build();
        return getMenusResourceUrl(rest) + "?days=" + request.getDays() + "&startDate=" + request.getStartDate();
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

    @SuppressWarnings("unchecked")
    private Collection<RecipeIngredientValue> compareMenusRecipeMainIngredients(MenuValue m1, MenuValue m2) {
        return CollectionUtils.intersection(getMenuRecipeMainIngredients(m1), getMenuRecipeMainIngredients(m2));
    }

    private List<RecipeIngredientValue> getMenuRecipeMainIngredients(MenuValue menu) {
        return Stream.of(recipeDetailServiceClient.getRecipeFromUrl(menu.getRecipeUrl()))
                .map(recipe -> recipeIngredientsListServiceClient.getIngredientsFromRecipe(recipe))
                .flatMap(l -> l.stream()).filter(i -> i.isMainIngredient()).collect(Collectors.toList());
    }

}
