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
package org.adhuc.cena.menu.port.adapter.persistence.memory;

import static org.assertj.core.api.Assertions.assertThat;

import static org.adhuc.cena.menu.domain.model.menu.MenuMother.allMenus;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.dinner20170103;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.dinner20170104;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.dinner20170105;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.lunch20170103;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.lunch20170104;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.lunch20170105;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The {@link InMemoryMenuRepository} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("inMemoryRepository")
@DisplayName("In-memory menu repository")
public class InMemoryMenuRepositoryTest {

    private InMemoryMenuRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new InMemoryMenuRepository();
    }

    @Nested
    @DisplayName("with no menu")
    class WithNoMenu {

        @Test
        @DisplayName("returns empty list of menus between min and max")
        public void findAllEmptyMinAndMax() {
            assertThat(repository.findByDateBetween(LocalDate.MIN, LocalDate.MAX)).isEmpty();
        }

    }

    @Nested
    @DisplayName("with multiple menus")
    class WithMultipleMenus {

        @BeforeEach
        public void setUp() {
            allMenus().stream().forEach(m -> repository.save(m));
        }

        @Test
        @DisplayName("get menus between 2017-01-03 and 2017-01-05")
        public void findByDateBetween() {
            assertThat(repository.findByDateBetween(LocalDate.parse("2017-01-03"), LocalDate.parse("2017-01-05")))
                    .containsExactlyInAnyOrder(lunch20170103(), dinner20170103(), lunch20170104(), dinner20170104(),
                            lunch20170105(), dinner20170105());
        }

    }

}
