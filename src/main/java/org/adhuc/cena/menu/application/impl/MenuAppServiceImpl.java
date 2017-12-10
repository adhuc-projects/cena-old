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

import java.util.Collections;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import org.adhuc.cena.menu.application.MenuAppService;
import org.adhuc.cena.menu.domain.model.menu.GenerateMenus;
import org.adhuc.cena.menu.domain.model.menu.Menu;
import org.adhuc.cena.menu.domain.model.menu.MenuGenerationService;
import org.adhuc.cena.menu.domain.model.menu.MenuRepository;
import org.adhuc.cena.menu.domain.model.menu.MenusQuery;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * A {@link MenuAppService} implementation.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Slf4j
@Service
public class MenuAppServiceImpl implements MenuAppService {

    private MenuRepository        menuRepository;
    private MenuGenerationService menuGenerationService;

    public MenuAppServiceImpl(@NonNull MenuRepository menuRepository,
            @NonNull MenuGenerationService menuGenerationService) {
        this.menuRepository = menuRepository;
        this.menuGenerationService = menuGenerationService;
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public List<Menu> getMenus(@NonNull MenusQuery query) {
        List<Menu> menus = menuRepository.findByOwnerAndDateBetween(query.owner(), query.interval());
        Collections.sort(menus);
        return menus;
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public void generateMenus(@NonNull GenerateMenus command) {
        log.info("Generate menus from command {}", command);
        menuGenerationService.generateMenus(command);
    }

}
