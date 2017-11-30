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

import static org.adhuc.cena.menu.domain.model.menu.MenuMother.allMenus;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.dinner20170103;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.dinner20170104;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.dinner20170105;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.generateMenus1DayAt20170102WeekWorkingDays;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.lunch20170103;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.lunch20170104;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.lunch20170105;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.adhuc.cena.menu.domain.model.menu.GenerateMenus;
import org.adhuc.cena.menu.domain.model.menu.MenuGenerationService;
import org.adhuc.cena.menu.domain.model.menu.MenuRepository;
import org.adhuc.cena.menu.domain.model.menu.MenusQuery;
import org.adhuc.cena.menu.port.adapter.persistence.memory.InMemoryMenuRepository;

/**
 * The {@link MenuAppServiceImpl} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("appService")
@DisplayName("Menu service")
public class MenuAppServiceImplTest {

    private MenuRepository        menuRepository;
    private MenuGenerationService menuGenerationServiceMock;

    private MenuAppServiceImpl    service;

    @BeforeEach
    public void setUp() {
        menuRepository = new InMemoryMenuRepository();
        menuGenerationServiceMock = Mockito.mock(MenuGenerationService.class);
        service = new MenuAppServiceImpl(menuRepository, menuGenerationServiceMock);
    }

    @Test
    @DisplayName("gets menus starting from 2017-01-03 for 3 days")
    public void getMenusFrom20170103For3Days() {
        allMenus().stream().forEach(m -> menuRepository.save(m));
        assertThat(service.getMenus(new MenusQuery(3, LocalDate.parse("2017-01-03")))).containsExactlyInAnyOrder(
                lunch20170103(), dinner20170103(), lunch20170104(), dinner20170104(), lunch20170105(),
                dinner20170105());
    }

    @Test
    @DisplayName("throws a IllegalArgumentException when generating menus from a null command")
    public void generateMenusThrowsIllegalArgumentExceptionNullCommand() {
        assertThrows(IllegalArgumentException.class, () -> service.generateMenus(null));
    }

    @Test
    @DisplayName("generates menus by calling menu generation service")
    public void generateMenusCallsMenuGenerationServiceWithCommand() {
        GenerateMenus command = generateMenus1DayAt20170102WeekWorkingDays();
        service.generateMenus(command);

        verify(menuGenerationServiceMock).generateMenus(same(command));
    }

}
