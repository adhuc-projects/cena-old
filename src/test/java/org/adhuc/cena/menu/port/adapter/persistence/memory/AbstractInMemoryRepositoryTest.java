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

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.adhuc.cena.menu.domain.model.Entity;
import org.adhuc.cena.menu.domain.model.UuidIdentity;

/**
 * The {@link AbstractInMemoryRepository} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("inMemoryRepository")
@DisplayName("Abstract in-memory repository")
public class AbstractInMemoryRepositoryTest {

    private AbstractInMemoryRepositoryTestImpl repository;

    @BeforeEach
    public void setUp() {
        repository = new AbstractInMemoryRepositoryTestImpl();
    }

    @Test
    @DisplayName("throws IllegalArgumentException when saving null entity")
    public void saveNullEntity() {
        assertThrows(IllegalArgumentException.class, () -> repository.save(null));
    }

    @Nested
    @DisplayName("with no entity")
    class WithNoEntity {

        @Test
        @DisplayName("returns empty list")
        public void findAllEmpty() {
            assertThat(repository.findAll()).isEmpty();
        }

    }

    @Nested
    @DisplayName("with entity")
    class WithEntity {

        private EntityTestImpl entity;

        @BeforeEach
        public void setUp() {
            entity = new EntityTestImpl();
            repository.save(entity);
        }

        @Test
        @DisplayName("returns a list containing entity")
        public void findAllAfterSaveContainsSavedEntity() {
            assertThat(repository.findAll()).containsExactly(entity);
        }

        @Test
        @DisplayName("returns an empty entity when finding by unknown id")
        public void findOneNotExisting() {
            assertThat(repository.findOne(new EntityTestImpl().id())).isNotPresent();
        }

        @Test
        @DisplayName("returns entity when finding by its id")
        public void findOneExisting() {
            assertThat(repository.findOne(entity.id())).isPresent().contains(entity);
        }

        @Test
        @DisplayName("overwrites known entity when saving with same id")
        public void saveExistingIngredientOverwritePreviousValue() {
            EntityTestImpl newEntity = new EntityTestImpl(entity.id());
            repository.save(newEntity);
            assertThat(repository.findAll()).containsExactly(newEntity);
        }

        @Nested
        @DisplayName("and second entity")
        class AndSecondEntity {

            private EntityTestImpl secondEntity;

            @BeforeEach
            public void setUp() {
                secondEntity = new EntityTestImpl();
                repository.save(secondEntity);
            }

            @Test
            @DisplayName("returns a list containing both entities")
            public void findAllAfterMultipleSaveContainsSavedEntities() {
                assertThat(repository.findAll()).containsExactlyInAnyOrder(entity, secondEntity);
            }

        }

    }

    private static class AbstractInMemoryRepositoryTestImpl
            extends AbstractInMemoryRepository<EntityTestImpl, UuidIdentity> {
        @Override
        public Class<EntityTestImpl> entityType() {
            return EntityTestImpl.class;
        }
    }

    private static class EntityTestImpl implements Entity<UuidIdentity> {
        private UuidIdentity id;

        public EntityTestImpl() {
            id = new UuidIdentity(UUID.randomUUID()) {
            };
        }

        public EntityTestImpl(UuidIdentity id) {
            this.id = id;
        }

        @Override
        public UuidIdentity id() {
            return id;
        }
    }

}
