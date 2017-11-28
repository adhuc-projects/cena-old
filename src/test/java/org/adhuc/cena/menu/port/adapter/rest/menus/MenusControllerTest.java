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

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.adhuc.cena.menu.domain.model.menu.MenuMother.generateMenus1DayAt20170102WeekWorkingDays;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import org.adhuc.cena.menu.application.MenuAppService;
import org.adhuc.cena.menu.configuration.MenuGenerationProperties;
import org.adhuc.cena.menu.configuration.WebSecurityConfiguration;
import org.adhuc.cena.menu.domain.model.menu.MealFrequency;
import org.adhuc.cena.menu.port.adapter.rest.ControllerTestSupport;
import org.adhuc.cena.menu.port.adapter.rest.menu.GenerateMenusRequest;
import org.adhuc.cena.menu.port.adapter.rest.menu.MenusController;

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
@WebMvcTest(controllers = MenusController.class)
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
    @DisplayName("generating menus without days returns bad request")
    @WithMockUser(authorities = "USER")
    public void generateMenusWithoutDaysReturnsBadRequestStatus() throws Exception {
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON).content(asJson(GenerateMenusRequest.builder()
                .frequency(MealFrequency.WEEK_WORKING_DAYS).startDate(LocalDate.parse("2017-01-02")).build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("generating menus with negative days returns bad request")
    @WithMockUser(authorities = "USER")
    public void generateMenusWithNegativeDaysReturnsBadRequestStatus() throws Exception {
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON).content(asJson(GenerateMenusRequest.builder()
                .days(-1).frequency(MealFrequency.WEEK_WORKING_DAYS).startDate(LocalDate.parse("2017-01-02")).build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("generating menus with 0 days returns bad request")
    @WithMockUser(authorities = "USER")
    public void generateMenusWith0DaysReturnsBadRequestStatus() throws Exception {
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON).content(asJson(GenerateMenusRequest.builder()
                .days(0).frequency(MealFrequency.WEEK_WORKING_DAYS).startDate(LocalDate.parse("2017-01-02")).build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("generating menus with too much days returns bad request")
    @WithMockUser(authorities = "USER")
    public void generateMenusWithTooMuchDaysReturnsBadRequestStatus() throws Exception {
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON).content(asJson(GenerateMenusRequest.builder()
                .days(11).frequency(MealFrequency.WEEK_WORKING_DAYS).startDate(LocalDate.parse("2017-01-02")).build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("generating menus without frequency returns bad request")
    @WithMockUser(authorities = "USER")
    public void generateMenusWithoutFrequencyReturnsBadRequestStatus() throws Exception {
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON).content(
                asJson(GenerateMenusRequest.builder().days(1).startDate(LocalDate.parse("2017-01-02")).build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("generating menus without start date returns bad request")
    @WithMockUser(authorities = "USER")
    public void generateMenusWithoutStartDateReturnsBadRequestStatus() throws Exception {
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON).content(
                asJson(GenerateMenusRequest.builder().days(1).frequency(MealFrequency.WEEK_WORKING_DAYS).build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("generating menus as anonymous user returns created status")
    @WithAnonymousUser
    public void generateMenusAsAnonymousUserReturnsCreatedStatus() throws Exception {
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON)
                .content(asJson(generateMenus1DayWeekWorkingDays2Jan17Request()))).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("generating menus for 1 day for week working days starting on 02-01-2017 calls application service")
    @WithMockUser(authorities = "USER")
    public void generateMenus1DayWeekWorkingDays2Jan17CallsAppService() throws Exception {
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON)
                .content(asJson(generateMenus1DayWeekWorkingDays2Jan17Request()))).andExpect(status().isCreated());
        verify(menuAppServiceMock).generateMenus(generateMenus1DayAt20170102WeekWorkingDays());
    }

    @Test
    @DisplayName("generating menus for 1 day for week working days starting on 02-01-2017 returns created status")
    @WithMockUser(authorities = "USER")
    public void generateMenus1DayWeekWorkingDays2Jan17ReturnsCreatedStatus() throws Exception {
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON)
                .content(asJson(generateMenus1DayWeekWorkingDays2Jan17Request()))).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("generating menus for 1 day for week working days starting on 02-01-2017 returns location header with link to menus list")
    @WithMockUser(authorities = "USER")
    public void generateMenus1DayWeekWorkingDays2Jan17ReturnsLinkToMenusList() throws Exception {
        GenerateMenusRequest request = generateMenus1DayWeekWorkingDays2Jan17Request();
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON).content(asJson(request)))
                .andExpect(header().string(HttpHeaders.LOCATION, endsWith(buildMenusListLink(request))));
    }

    @Test
    @DisplayName("generating menus for 10 days for twice a day starting on 01-01-2017 returns location header with link to menus list")
    @WithMockUser(authorities = "USER")
    public void generateMenus10DaysTwiceADay1Jan17ReturnsLinkToMenusList() throws Exception {
        GenerateMenusRequest request = generateMenus10DaysTwiceADay1Jan17Request();
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON).content(asJson(request)))
                .andExpect(header().string(HttpHeaders.LOCATION, endsWith(buildMenusListLink(request))));
    }

    private GenerateMenusRequest generateMenus1DayWeekWorkingDays2Jan17Request() {
        return GenerateMenusRequest.builder().days(1).frequency(MealFrequency.WEEK_WORKING_DAYS)
                .startDate(LocalDate.parse("2017-01-02")).build();
    }

    private GenerateMenusRequest generateMenus10DaysTwiceADay1Jan17Request() {
        return GenerateMenusRequest.builder().days(10).frequency(MealFrequency.TWICE_A_DAY)
                .startDate(LocalDate.parse("2017-01-01")).build();
    }

    private String buildMenusListLink(GenerateMenusRequest request) {
        return UriComponentsBuilder.fromPath(MENUS_API_URL).queryParam("days", Integer.toString(request.getDays()))
                .queryParam("frequency", request.getFrequency().toString())
                .queryParam("startDate", request.getStartDate().toString()).build().toUriString();
    }

}
