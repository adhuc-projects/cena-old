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
package org.adhuc.cena.menu.domain.model.menu;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * A service dedicated to the menus generation.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Service
public class MenuGenerationService {

    private MenuRepository                  menuRepository;
    private MealFrequencyIterationGenerator mealFrequencyIterationGenerator;
    private MenuRecipeDefinerStrategy       menuRecipeDefinerStrategy;

    @Autowired
    public MenuGenerationService(MenuRepository menuRepository,
            MealFrequencyIterationGenerator mealFrequencyIterationGenerator,
            MenuRecipeDefinerStrategy menuRecipeDefinerStrategy) {
        this.menuRepository = menuRepository;
        this.mealFrequencyIterationGenerator = mealFrequencyIterationGenerator;
        this.menuRecipeDefinerStrategy = menuRecipeDefinerStrategy;
    }

    /**
     * Generates the menus corresponding to the specified command.
     *
     * @param command
     *            the menu generation command.
     */
    public void generateMenus(GenerateMenus command) {
        Set<MenuId> mealIterations = mealFrequencyIterationGenerator.generateIterations(command);
        mealIterations.stream().map(id -> new Menu(id, menuRecipeDefinerStrategy.defineRecipeForMenu(id, command)))
                .forEach(menu -> menuRepository.save(menu));
    }

}
