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
package org.adhuc.cena.menu.port.adapter.rest.menus;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.adhuc.cena.menu.domain.model.menu.MenuMother.MENU_2017_01_01_DAYS;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.MENU_2017_01_01_FREQUENCY;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.MENU_2017_01_01_OWNER;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.MENU_2017_01_01_START_DATE;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.MENU_2017_01_02_DAYS;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.MENU_2017_01_02_FREQUENCY;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.MENU_2017_01_02_START_DATE;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.MENU_2017_01_03_DAYS;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.MENU_2017_01_03_START_DATE;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.dinner20170101;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.dinner20170103;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.dinner20170104;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.generateMenus1DayAt20170102WeekWorkingDays;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.lunch20170101;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.lunch20170103;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.lunch20170104;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.queryMenus1DayAt20170102;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.queryMenus2DaysAt20170103;
import static org.adhuc.cena.menu.support.ClockProvider.CLOCK;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.UriComponentsBuilder;

import org.adhuc.cena.menu.application.MenuAppService;
import org.adhuc.cena.menu.configuration.MenuGenerationProperties;
import org.adhuc.cena.menu.configuration.WebSecurityConfiguration;
import org.adhuc.cena.menu.domain.model.menu.MealFrequency;
import org.adhuc.cena.menu.domain.model.menu.Menu;
import org.adhuc.cena.menu.domain.model.menu.MenusQuery;
import org.adhuc.cena.menu.port.adapter.rest.ControllerTestSupport;
import org.adhuc.cena.menu.port.adapter.rest.menu.GenerateMenusRequest;
import org.adhuc.cena.menu.port.adapter.rest.menu.MenuResourceAssembler;
import org.adhuc.cena.menu.port.adapter.rest.menu.MenusController;
import org.adhuc.cena.menu.support.security.WithAuthenticatedUser;
import org.adhuc.cena.menu.support.security.WithCommunityUser;

/**
 * The {@link MenusController} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("integration")
@Tag("restController")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MenusController.class,
        includeFilters = { @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = MenuResourceAssembler.class) })
@EnableConfigurationProperties(MenuGenerationProperties.class)
@Import(WebSecurityConfiguration.class)
@DisplayName("Menus controller")
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class MenusControllerTest extends ControllerTestSupport {

    private static final String MENUS_API_URL = "/api/menus";

    @Autowired
    private MockMvc             mvc;

    @MockBean
    private MenuAppService      menuAppServiceMock;

    @BeforeEach
    public void setUp() {
        reset(menuAppServiceMock);
    }

    @Test
    @DisplayName("getting list as community user returns unauthorized status")
    @WithCommunityUser
    public void getMenusHasSelfLink() throws Exception {
        mvc.perform(get(MENUS_API_URL).param("days", Integer.toString(MENU_2017_01_02_DAYS)).param("startDate",
                MENU_2017_01_02_START_DATE.toString())).andExpect(status().isUnauthorized());
    }

    @Nested
    @DisplayName("with empty menus list")
    class WithEmptyMenusList {

        @BeforeEach
        public void setUp() {
            when(menuAppServiceMock.getMenus(queryMenus1DayAt20170102())).thenReturn(Collections.emptyList());
        }

        @Test
        @DisplayName("getting menus starting at 2017-01-02 for 1 day returns OK")
        @WithAuthenticatedUser
        public void getMenus1DayStartDate20170102ReturnsOKStatus() throws Exception {
            mvc.perform(get(MENUS_API_URL).param("days", Integer.toString(MENU_2017_01_02_DAYS)).param("startDate",
                    MENU_2017_01_02_START_DATE.toString())).andExpect(status().isOk());
        }

        @Test
        @DisplayName("getting menus starting at 2017-01-02 for 1 day returns empty list")
        @WithAuthenticatedUser
        public void getMenus1DayStartDate20170102WithNoMenuReturnsEmptyList() throws Exception {
            mvc.perform(get(MENUS_API_URL).param("days", Integer.toString(MENU_2017_01_02_DAYS)).param("startDate",
                    MENU_2017_01_02_START_DATE.toString())).andExpect(jsonPath("$._embedded.data").isArray())
                    .andExpect(jsonPath("$._embedded.data").isEmpty());
        }

    }

    @Nested
    @DisplayName("with 4 menus in the list")
    class With4Menus {

        @BeforeEach
        public void setUp() {
            when(menuAppServiceMock.getMenus(queryMenus2DaysAt20170103()))
                    .thenReturn(Arrays.asList(lunch20170103(), dinner20170103(), lunch20170104(), dinner20170104()));
        }

        @Test
        @DisplayName("getting menus starting at 2017-01-03 for 2 days returns OK")
        @WithAuthenticatedUser
        public void getMenus2DaysStartDate20170103ReturnsOKStatus() throws Exception {
            mvc.perform(get(MENUS_API_URL).param("days", Integer.toString(MENU_2017_01_03_DAYS)).param("startDate",
                    MENU_2017_01_03_START_DATE.toString())).andExpect(status().isOk());
        }

        @Test
        @DisplayName("getting menus starting at 2017-01-03 for 2 days returns list with 4 menus")
        @WithAuthenticatedUser
        public void getMenus2DaysStartDate20170103WithNoMenuReturnsEmptyList() throws Exception {
            final ResultActions resultActions = mvc
                    .perform(get(MENUS_API_URL).param("days", Integer.toString(MENU_2017_01_03_DAYS)).param("startDate",
                            MENU_2017_01_03_START_DATE.toString()))
                    .andExpect(jsonPath("$._embedded.data").isArray())
                    .andExpect(jsonPath("$._embedded.data").isNotEmpty())
                    .andExpect(jsonPath("$._embedded.data", hasSize(4)));
            assertJsonContainsMenu(resultActions, "$._embedded.data[0]", lunch20170103());
            assertJsonContainsMenu(resultActions, "$._embedded.data[1]", dinner20170103());
            assertJsonContainsMenu(resultActions, "$._embedded.data[2]", lunch20170104());
            assertJsonContainsMenu(resultActions, "$._embedded.data[3]", dinner20170104());
        }

        @Test
        @DisplayName("getting list contains self link to resource")
        @WithAuthenticatedUser
        public void getMenusHasSelfLink() throws Exception {
            assertSelfLinkEqualToRequestUrl(
                    mvc.perform(get(MENUS_API_URL).param("days", Integer.toString(MENU_2017_01_03_DAYS))
                            .param("startDate", MENU_2017_01_02_START_DATE.toString())));
        }

    }

    @Nested
    @DisplayName("getting menus list with default parameters")
    class WithDefaultMenusListParams {

        @BeforeEach
        public void setUp() {
            when(menuAppServiceMock.getMenus(new MenusQuery(1, LocalDate.parse("2017-01-01"), MENU_2017_01_01_OWNER)))
                    .thenReturn(Arrays.asList(lunch20170101(), dinner20170101()));
        }

        @Test
        @DisplayName("returns OK")
        @WithAuthenticatedUser
        public void getMenusDefaultReturnsOKStatus() throws Exception {
            mvc.perform(get(MENUS_API_URL)).andExpect(status().isOk());
        }

        @Test
        @DisplayName("returns list with 2 menus")
        @WithAuthenticatedUser
        public void getMenusDefaultWithNoMenuReturnsEmptyList() throws Exception {
            final ResultActions resultActions =
                    mvc.perform(get(MENUS_API_URL)).andExpect(jsonPath("$._embedded.data").isArray())
                            .andExpect(jsonPath("$._embedded.data").isNotEmpty())
                            .andExpect(jsonPath("$._embedded.data", hasSize(2)));
            assertJsonContainsMenu(resultActions, "$._embedded.data[0]", lunch20170101());
            assertJsonContainsMenu(resultActions, "$._embedded.data[1]", dinner20170101());
        }

    }

    @Test
    @DisplayName("generating menus without days returns bad request")
    @WithAuthenticatedUser
    public void generateMenusWithoutDaysReturnsBadRequestStatus() throws Exception {
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON).content(asJson(GenerateMenusRequest.builder()
                .frequency(MealFrequency.WEEK_WORKING_DAYS).startDate(MENU_2017_01_02_START_DATE).build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("generating menus with negative days returns bad request")
    @WithAuthenticatedUser
    public void generateMenusWithNegativeDaysReturnsBadRequestStatus() throws Exception {
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON).content(asJson(GenerateMenusRequest.builder()
                .days(-1).frequency(MENU_2017_01_02_FREQUENCY).startDate(MENU_2017_01_02_START_DATE).build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("generating menus with 0 days returns bad request")
    @WithAuthenticatedUser
    public void generateMenusWith0DaysReturnsBadRequestStatus() throws Exception {
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON).content(asJson(GenerateMenusRequest.builder()
                .days(0).frequency(MENU_2017_01_02_FREQUENCY).startDate(MENU_2017_01_02_START_DATE).build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("generating menus with too much days returns bad request")
    @WithAuthenticatedUser
    public void generateMenusWithTooMuchDaysReturnsBadRequestStatus() throws Exception {
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON).content(asJson(GenerateMenusRequest.builder()
                .days(11).frequency(MENU_2017_01_02_FREQUENCY).startDate(MENU_2017_01_02_START_DATE).build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("generating menus without frequency returns bad request")
    @WithAuthenticatedUser
    public void generateMenusWithoutFrequencyReturnsBadRequestStatus() throws Exception {
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON).content(asJson(GenerateMenusRequest.builder()
                .days(MENU_2017_01_02_DAYS).startDate(MENU_2017_01_02_START_DATE).build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("generating menus without start date returns bad request")
    @WithAuthenticatedUser
    public void generateMenusWithoutStartDateReturnsBadRequestStatus() throws Exception {
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON).content(asJson(GenerateMenusRequest.builder()
                .days(MENU_2017_01_02_DAYS).frequency(MENU_2017_01_02_FREQUENCY).build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("generating menus as community user returns unauthorized status")
    @WithCommunityUser
    public void createRecipeAsCommunityUser() throws Exception {
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON)
                .content(asJson(generateMenus1DayWeekWorkingDays2Jan17Request()))).andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("generating menus as community user returns created status")
    @WithAuthenticatedUser
    public void generateMenusAsCommunityUserReturnsCreatedStatus() throws Exception {
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON)
                .content(asJson(generateMenus1DayWeekWorkingDays2Jan17Request()))).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("generating menus for 1 day for week working days starting on 02-01-2017 calls application service")
    @WithAuthenticatedUser
    public void generateMenus1DayWeekWorkingDays2Jan17CallsAppService() throws Exception {
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON)
                .content(asJson(generateMenus1DayWeekWorkingDays2Jan17Request()))).andExpect(status().isCreated());
        verify(menuAppServiceMock).generateMenus(generateMenus1DayAt20170102WeekWorkingDays());
    }

    @Test
    @DisplayName("generating menus for 1 day for week working days starting on 02-01-2017 returns created status")
    @WithAuthenticatedUser
    public void generateMenus1DayWeekWorkingDays2Jan17ReturnsCreatedStatus() throws Exception {
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON)
                .content(asJson(generateMenus1DayWeekWorkingDays2Jan17Request()))).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("generating menus for 1 day for week working days starting on 02-01-2017 returns location header with link to menus list")
    @WithAuthenticatedUser
    public void generateMenus1DayWeekWorkingDays2Jan17ReturnsLinkToMenusList() throws Exception {
        GenerateMenusRequest request = generateMenus1DayWeekWorkingDays2Jan17Request();
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON).content(asJson(request)))
                .andExpect(header().string(HttpHeaders.LOCATION, endsWith(buildMenusListLink(request))));
    }

    @Test
    @DisplayName("generating menus for 10 days for twice a day starting on 01-01-2017 returns location header with link to menus list")
    @WithAuthenticatedUser
    public void generateMenus10DaysTwiceADay1Jan17ReturnsLinkToMenusList() throws Exception {
        GenerateMenusRequest request = generateMenus10DaysTwiceADay1Jan17Request();
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON).content(asJson(request)))
                .andExpect(header().string(HttpHeaders.LOCATION, endsWith(buildMenusListLink(request))));
    }

    private GenerateMenusRequest generateMenus1DayWeekWorkingDays2Jan17Request() {
        return GenerateMenusRequest.builder().days(MENU_2017_01_02_DAYS).frequency(MENU_2017_01_02_FREQUENCY)
                .startDate(MENU_2017_01_02_START_DATE).build();
    }

    private GenerateMenusRequest generateMenus10DaysTwiceADay1Jan17Request() {
        return GenerateMenusRequest.builder().days(MENU_2017_01_01_DAYS).frequency(MENU_2017_01_01_FREQUENCY)
                .startDate(MENU_2017_01_01_START_DATE).build();
    }

    private String buildMenusListLink(GenerateMenusRequest request) {
        return UriComponentsBuilder.fromPath(MENUS_API_URL).queryParam("days", Integer.toString(request.getDays()))
                .queryParam("startDate", request.getStartDate().toString()).build().toUriString();
    }

    private void assertJsonContainsMenu(ResultActions resultActions, String jsonPath, Menu menu) throws Exception {
        resultActions.andExpect(jsonPath(jsonPath + ".date").exists())
                .andExpect(jsonPath(jsonPath + ".date", equalTo(menu.id().date().toString())))
                .andExpect(jsonPath(jsonPath + ".type").exists())
                .andExpect(jsonPath(jsonPath + ".type", equalTo(menu.id().type().name())))
                .andExpect(jsonPath(jsonPath + ".recipe").doesNotExist())
                // TODO remove those assertions when implementing menu detail service
                .andExpect(jsonPath(jsonPath + "._links.self").exists())
                .andExpect(jsonPath(jsonPath + "._links.recipe").exists())
                .andExpect(jsonPath(jsonPath + "._links.recipe.href", endsWith(menu.recipe().toString())));
    }

    @TestConfiguration
    public static class FixedClockTestConfiguration {

        @Bean
        public Clock clock() {
            return CLOCK;
        }

    }

}
