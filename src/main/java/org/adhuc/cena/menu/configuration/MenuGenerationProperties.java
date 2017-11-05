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
package org.adhuc.cena.menu.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Configuration properties to configure the menu generation application.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Data
@ConfigurationProperties(prefix = "cena.menu-generation")
public class MenuGenerationProperties {

    private Documentation  documentation  = new Documentation();
    private Authentication authentication = new Authentication();

    @Data
    public static class Documentation {
        /** Enable API documentation endpoint. Default is true. */
        private boolean enabled = true;
        /** Path to documentation resources. Default is '/api/docs'. */
        private String  path    = "/api/docs";
    }

    @Data
    public static class Authentication {

        private UsernamePassword       ingredientManager;
        private List<UsernamePassword> users;

    }

    @Data
    public static class UsernamePassword {
        /** The username. */
        private String username;
        /** The password. */
        private String password;
    }

}
