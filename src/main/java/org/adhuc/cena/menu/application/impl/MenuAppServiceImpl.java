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

import static org.springframework.util.Assert.notNull;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.adhuc.cena.menu.application.MenuAppService;
import org.adhuc.cena.menu.domain.model.menu.GenerateMenus;
import org.adhuc.cena.menu.domain.model.menu.Menu;
import org.adhuc.cena.menu.domain.model.menu.MenuGenerationService;
import org.adhuc.cena.menu.domain.model.menu.MenuRepository;
import org.adhuc.cena.menu.domain.model.menu.MenusQuery;

/**
 * A {@link MenuAppService} implementation.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Service
public class MenuAppServiceImpl implements MenuAppService {

    private MenuRepository        menuRepository;
    private MenuGenerationService menuGenerationService;

    @Autowired
    public MenuAppServiceImpl(MenuRepository menuRepository, MenuGenerationService menuGenerationService) {
        this.menuRepository = menuRepository;
        this.menuGenerationService = menuGenerationService;
    }

    @Override
    public List<Menu> getMenus(MenusQuery query) {
        return menuRepository.findByDateBetween(query.startDate(), query.startDate().plusDays(query.days() - 1));
    }

    @Override
    public void generateMenus(GenerateMenus command) {
        notNull(command, "Cannot generate menus from invalid command");
        menuGenerationService.generateMenus(command);
    }

}
