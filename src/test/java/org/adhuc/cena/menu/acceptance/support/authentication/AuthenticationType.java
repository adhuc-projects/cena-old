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
package org.adhuc.cena.menu.acceptance.support.authentication;

import org.adhuc.cena.menu.acceptance.support.authentication.AcceptanceAuthenticationMother.AcceptanceAuthenticationKey;

/**
 * The authentication types.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
public enum AuthenticationType {

    COMMUNITY_USER(AcceptanceAuthenticationKey.COMMUNITY_USER),
    AUTHENTICATED_USER(AcceptanceAuthenticationKey.AUTHENTICATED_USER),
    INGREDIENT_MANAGER(AcceptanceAuthenticationKey.INGREDIENT_MANAGER);

    private AcceptanceAuthenticationKey authenticationKey;

    private AuthenticationType(AcceptanceAuthenticationKey authenticationKey) {
        this.authenticationKey = authenticationKey;
    }

    protected AcceptanceAuthenticationKey authenticationKey() {
        return authenticationKey;
    }

}
