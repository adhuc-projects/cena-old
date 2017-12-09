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
package org.adhuc.cena.menu.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The {@link Repository} test class.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Tag("unit")
@Tag("domain")
@DisplayName("Repository")
public class RepositoryTest {

    @Test
    @DisplayName("returns entity when found")
    public void findOneNotNullFound() {
        EntityTestImpl entity = new EntityTestImpl();
        RepositoryTestImpl repository = new RepositoryTestImpl(entity);
        assertThat(repository.findOneNotNull(entity.id())).isEqualTo(entity);
    }

    @Test
    @DisplayName("throws EntityNotFoundException when not found")
    public void findOneNotNullNotFound() {
        RepositoryTestImpl repository = new RepositoryTestImpl(null);
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> repository.findOneNotNull(new EntityTestImpl().id()));
        assertThat(exception.entityType()).isEqualTo(EntityTestImpl.class);
    }

    private static class RepositoryTestImpl implements Repository<EntityTestImpl, UuidIdentity> {
        private EntityTestImpl entity;

        public RepositoryTestImpl(EntityTestImpl entity) {
            this.entity = entity;
        }

        @Override
        public Class<EntityTestImpl> entityType() {
            return EntityTestImpl.class;
        }

        @Override
        public List<EntityTestImpl> findAll() {
            throw new UnsupportedOperationException("Test does not require this method");
        }

        @Override
        public Optional<EntityTestImpl> findOne(UuidIdentity entityId) {
            return Optional.ofNullable(entity);
        }
    }

    private static class EntityTestImpl implements Entity<UuidIdentity> {
        @Override
        public UuidIdentity id() {
            return new UuidIdentity(UUID.randomUUID()) {
            };
        }
    }

}
