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
package org.adhuc.cena.menu.application.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;

import static org.adhuc.cena.menu.domain.model.menu.MenuMother.dinner20170102;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.generateMenus1DayAt20170102WeekWorkingDays;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.queryMenus1DayAt20170102;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.adhuc.cena.menu.application.MenuAppService;
import org.adhuc.cena.menu.domain.model.menu.GenerateMenus;
import org.adhuc.cena.menu.domain.model.menu.MenuGenerationService;
import org.adhuc.cena.menu.domain.model.menu.MenuRepository;
import org.adhuc.cena.menu.support.security.WithCommunityUser;

/**
 * The {@link RecipeAppServiceImpl} security tests.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("integration")
@Tag("appService")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Menu service with security")
public class MenuAppServiceImplSecurityTest {

    @Autowired
    private MenuAppService        service;
    @Autowired
    private MenuRepository        repository;
    @MockBean
    private MenuGenerationService menuGenerationServiceMock;

    @BeforeEach
    public void setUp() {
        repository.save(dinner20170102());
    }

    @Test
    @WithCommunityUser
    @DisplayName("refuses access to menus list to community user")
    public void getMenusAsCommunityUser() {
        assertThrows(AccessDeniedException.class, () -> service.getMenus(queryMenus1DayAt20170102()));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("grants access to menus list to authenticated user")
    public void getRecipesAsAuthenticatedUser() {
        assertThat(service.getMenus(queryMenus1DayAt20170102())).usingFieldByFieldElementComparator()
                .contains(dinner20170102());
    }

    @Test
    @WithCommunityUser
    @DisplayName("refuses access to menus generation to community user")
    public void createRecipeAsCommunityUser() {
        assertThrows(AccessDeniedException.class,
                () -> service.generateMenus(generateMenus1DayAt20170102WeekWorkingDays()));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("grants access to recipe creation to authenticated user")
    public void createRecipeAsAuthenticatedUser() {
        GenerateMenus command = generateMenus1DayAt20170102WeekWorkingDays();
        service.generateMenus(command);

        verify(menuGenerationServiceMock).generateMenus(same(command));
    }

}
