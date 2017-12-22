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
package org.adhuc.cena.menu.port.adapter.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.adhuc.cena.menu.configuration.WebSecurityConfiguration;
import org.adhuc.cena.menu.port.adapter.web.WebIndexController;
import org.adhuc.cena.menu.support.security.WithAuthenticatedUser;
import org.adhuc.cena.menu.support.security.WithCommunityUser;
import org.adhuc.cena.menu.support.security.WithIngredientManager;

/**
 * The {@link ApiIndexController} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("integration")
@Tag("restController")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = { ApiIndexController.class, WebIndexController.class })
@ContextConfiguration(classes = ResultHandlerConfiguration.class)
@Import(WebSecurityConfiguration.class)
@AutoConfigureRestDocs("target/generated-snippets")
@DisplayName("API index controller")
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class ApiIndexControllerTest {

    private static final String API_URL = "/api";

    @Autowired
    private MockMvc             mvc;

    @Test
    @DisplayName("getting index as community user returns accessible links")
    @WithCommunityUser
    public void getIndexAsCommunityUser() throws Exception {
        mvc.perform(get(API_URL)).andExpect(status().isOk()).andExpect(jsonPath("_links.ingredients").exists())
                .andExpect(jsonPath("_links.recipes").exists())
                .andExpect(jsonPath("_links.ingredientsManagement").doesNotExist())
                .andExpect(jsonPath("_links.menus").doesNotExist());
    }

    @Test
    @DisplayName("getting index as authenticated user returns accessible links")
    @WithAuthenticatedUser
    public void getIndexAsAuthenticatedUser() throws Exception {
        mvc.perform(get(API_URL)).andExpect(status().isOk()).andExpect(jsonPath("_links.ingredients").exists())
                .andExpect(jsonPath("_links.recipes").exists())
                .andExpect(jsonPath("_links.ingredientsManagement").doesNotExist())
                .andExpect(jsonPath("_links.menus").exists());
    }

    @Test
    @DisplayName("getting index as ingredient manager returns accessible links")
    @WithIngredientManager
    public void getIndexAsIngredientManager() throws Exception {
        mvc.perform(get(API_URL)).andExpect(status().isOk()).andExpect(jsonPath("_links.ingredients").exists())
                .andExpect(jsonPath("_links.recipes").exists())
                .andExpect(jsonPath("_links.ingredientsManagement").exists())
                .andExpect(jsonPath("_links.menus").exists());
    }

}
