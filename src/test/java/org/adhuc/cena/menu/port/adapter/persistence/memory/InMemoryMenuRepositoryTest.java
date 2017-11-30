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
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.adhuc.cena.menu.domain.model.menu.MenuMother.DINNER_2017_01_02_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.LUNCH_2017_01_01_ID;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.allMenus;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.dinner20170102;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.dinner20170103;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.dinner20170104;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.dinner20170105;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.lunch20170101;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.lunch20170103;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.lunch20170104;
import static org.adhuc.cena.menu.domain.model.menu.MenuMother.lunch20170105;
import static org.adhuc.cena.menu.domain.model.recipe.RecipeMother.TOMATO_CUCUMBER_MOZZA_SALAD_ID;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.adhuc.cena.menu.domain.model.EntityNotFoundException;
import org.adhuc.cena.menu.domain.model.menu.Menu;

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

    @Test
    @DisplayName("throws IllegalArgumentException when saving null menu")
    public void saveNullMenu() {
        assertThrows(IllegalArgumentException.class, () -> repository.save(null));
    }

    @Test
    @DisplayName("throws EntityNotFoundException when finding unknown menu (not null required)")
    public void findOneNotNullUnknown() {
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> repository.findOneNotNull(DINNER_2017_01_02_ID));
        assertThat(exception.entityType()).isEqualTo(Menu.class);
        assertThat(exception.identity()).isEqualTo(DINNER_2017_01_02_ID.toString());
    }

    @Nested
    @DisplayName("with no menu")
    class WithNoMenu {

        @Test
        @DisplayName("returns empty list")
        public void findAllEmpty() {
            assertThat(repository.findAll()).isEmpty();
        }

        @Test
        @DisplayName("returns empty list of menus between min and max")
        public void findAllEmptyMinAndMax() {
            assertThat(repository.findByDateBetween(LocalDate.MIN, LocalDate.MAX)).isEmpty();
        }

    }

    @Nested
    @DisplayName("with dinner at 2017-01-02")
    class WithDinner20170102 {

        @BeforeEach
        public void setUp() {
            repository.save(dinner20170102());
        }

        @Test
        @DisplayName("returns a menus list containing saved menu")
        public void findAllAfterSaveContainsSavedMenu() {
            assertThat(repository.findAll()).usingFieldByFieldElementComparator().containsExactly(dinner20170102());
        }

        @Test
        @DisplayName("returns an empty menu when finding by unknown id")
        public void findOneNotExisting() {
            assertThat(repository.findOne(LUNCH_2017_01_01_ID)).isNotPresent();
        }

        @Test
        @DisplayName("returns dinner at 2017-01-02 when finding by id")
        public void findOneExisting() {
            assertThat(repository.findOne(DINNER_2017_01_02_ID)).isPresent().usingFieldByFieldValueComparator()
                    .contains(dinner20170102());
        }

        @Test
        @DisplayName("returns a menus list containing saved menu with new value")
        public void saveExistingRecipeOverwritePreviousValue() {
            repository.save(dinner20170102().recipe(TOMATO_CUCUMBER_MOZZA_SALAD_ID));
            assertThat(repository.findAll()).usingFieldByFieldElementComparator()
                    .containsExactly(new Menu(DINNER_2017_01_02_ID, TOMATO_CUCUMBER_MOZZA_SALAD_ID));
        }

        @Nested
        @DisplayName("and lunch at 2017-01-01")
        class AndLunch20170101 {

            @BeforeEach
            public void setUp() {
                repository.save(lunch20170101());
            }

            @Test
            @DisplayName("returns a menus list containing all saved menus")
            public void findAllAfterMultipleSaveContainsSavedMenus() {
                assertThat(repository.findAll()).usingFieldByFieldElementComparator()
                        .containsExactlyInAnyOrder(dinner20170102(), lunch20170101());
            }

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
