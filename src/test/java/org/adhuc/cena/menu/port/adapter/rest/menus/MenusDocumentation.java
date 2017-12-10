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

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.adhuc.cena.menu.domain.model.menu.MenuMother.MENU_2017_01_02_DAYS;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.MENU_2017_01_02_FREQUENCY;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.MENU_2017_01_02_START_DATE;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.dinner20170102;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.lunch20170102;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.queryMenus1DayAt20170102;
import static org.adhuc.cena.menu.support.ClockProvider.CLOCK;

import java.time.Clock;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.adhuc.cena.menu.application.MenuAppService;
import org.adhuc.cena.menu.configuration.MenuGenerationProperties;
import org.adhuc.cena.menu.configuration.WebSecurityConfiguration;
import org.adhuc.cena.menu.port.adapter.rest.ControllerTestSupport;
import org.adhuc.cena.menu.port.adapter.rest.ResultHandlerConfiguration;
import org.adhuc.cena.menu.port.adapter.rest.documentation.support.ConstrainedFields;
import org.adhuc.cena.menu.port.adapter.rest.menu.GenerateMenusRequest;
import org.adhuc.cena.menu.port.adapter.rest.menu.MenuResourceAssembler;
import org.adhuc.cena.menu.port.adapter.rest.menu.MenusController;
import org.adhuc.cena.menu.support.security.WithAuthenticatedUser;

/**
 * The menus related rest-services documentation.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("integration")
@Tag("documentation")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MenusController.class,
        includeFilters = { @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = MenuResourceAssembler.class) })
@ContextConfiguration(classes = ResultHandlerConfiguration.class)
@EnableConfigurationProperties(MenuGenerationProperties.class)
@Import(WebSecurityConfiguration.class)
@AutoConfigureRestDocs("target/generated-snippets")
@DisplayName("Menus resource documentation")
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class MenusDocumentation extends ControllerTestSupport {

    private static final String            MENUS_API_URL = "/api/menus";

    @Autowired
    private MockMvc                        mvc;
    @Autowired
    private RestDocumentationResultHandler documentationHandler;

    @MockBean
    private MenuAppService                 menuAppServiceMock;

    @BeforeEach
    public void setUp() {
        reset(menuAppServiceMock);
    }

    @Test
    @DisplayName("generates menus list example")
    @WithAuthenticatedUser
    public void menusListExample() throws Exception {
        when(menuAppServiceMock.getMenus(queryMenus1DayAt20170102()))
                .thenReturn(Arrays.asList(lunch20170102(), dinner20170102()));
        mvc.perform(get(MENUS_API_URL).param("days", Integer.toString(MENU_2017_01_02_DAYS)).param("startDate",
                MENU_2017_01_02_START_DATE.toString())).andExpect(status().isOk())
                .andDo(documentationHandler.document(requestParameters(
                        parameterWithName("days").description("The number of days to get menus for (default = 1)"),
                        parameterWithName("startDate")
                                .description("The start date to get menus for (default = current date)")),
                        links(linkWithRel("self").description("This <<resources-menus,menus list>>")),
                        responseFields(
                                subsectionWithPath("_embedded.data")
                                        .description("An array of <<resources-menu, Menu resources>>"),
                                subsectionWithPath("_links")
                                        .description("<<resources-menus-links,Links>> to other resources"))));
    }

    @Test
    @DisplayName("generates menus generation example")
    @WithAuthenticatedUser
    public void menusGenerateExample() throws Exception {
        ConstrainedFields fields = new ConstrainedFields(GenerateMenusRequest.class);
        mvc.perform(post(MENUS_API_URL).contentType(APPLICATION_JSON).content(asJson(generateMenusRequest())))
                .andExpect(status().isCreated())
                .andDo(documentationHandler.document(
                        requestFields(fields.withPath("days").description("The number of days to generate menus for"),
                                fields.withPath("startDate").description("The menus generation start date"),
                                fields.withPath("frequency").description("The menus frequency"))));
    }

    private GenerateMenusRequest generateMenusRequest() {
        return GenerateMenusRequest.builder().days(MENU_2017_01_02_DAYS).frequency(MENU_2017_01_02_FREQUENCY)
                .startDate(MENU_2017_01_02_START_DATE).build();
    }

    @TestConfiguration
    public static class FixedClockTestConfiguration {

        @Bean
        public Clock clock() {
            return CLOCK;
        }

    }

}
