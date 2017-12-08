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

import static org.springframework.util.Assert.notNull;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import org.adhuc.cena.menu.configuration.MenuGenerationProperties.Documentation;

/**
 * A configuration to enable documentation endpoint.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Configuration
public class DocumentationConfiguration extends WebMvcConfigurerAdapter {

    private static final String DOCUMENTATION_RESOURCE_LOCATION = "classpath:/static/docs/asciidoc/";

    private final Documentation documentation;

    public DocumentationConfiguration(MenuGenerationProperties menuGenerationProperties) {
        notNull(menuGenerationProperties, "Cannot initialize documentation configuration with null properties");
        documentation = menuGenerationProperties.getDocumentation();
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        if (documentation.isEnabled()) {
            registry.addResourceHandler(documentation.getPath() + "/**")
                    .addResourceLocations(DOCUMENTATION_RESOURCE_LOCATION);
        }
    }

}
