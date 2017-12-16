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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * The configuration for resources handling. Ensures that the resources are provided with a known prefix (/static or
 * /assets) to simplify security configuration.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Configuration
public class ResourceHandlingConfiguration extends WebMvcConfigurerAdapter {

    @Value("${spring.resources.cache-period}")
    private Integer cachePeriod;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/static/assets/")
                .setCachePeriod(cachePeriod);
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/")
                .setCachePeriod(cachePeriod);
        registry.addResourceHandler("*.chunk.js").addResourceLocations("classpath:/static/")
                .setCachePeriod(cachePeriod);
    }

}
