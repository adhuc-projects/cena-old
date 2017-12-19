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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import org.adhuc.cena.menu.configuration.MenuGenerationProperties.Authentication;
import org.adhuc.cena.menu.configuration.MenuGenerationProperties.ResourceSecurity;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * The spring configuration for security in web layer.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String    BASE_API_PATH = "/api/**";

    private SecurityProperties     securityProperties;
    private Authentication         authentication;
    private List<ResourceSecurity> securedResources;

    public WebSecurityConfiguration(@NonNull SecurityProperties securityProperties,
            @NonNull MenuGenerationProperties menuGenerationProperties) {
        this.securityProperties = securityProperties;
        authentication = menuGenerationProperties.getAuthentication();
        securedResources = menuGenerationProperties.getSecuredResources();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizationConfigurer =
                http.authorizeRequests();
        for (ResourceSecurity securedResource : securedResources) {
            log.info("Secure paths {} for http method {}", securedResource.getPaths(), securedResource.getMethod());
            authorizationConfigurer
                    .antMatchers(securedResource.getMethod(), securedResource.getPaths().toArray(new String[0]))
                    .authenticated();
        }
        authorizationConfigurer.antMatchers(BASE_API_PATH).permitAll();
        http.csrf().disable();
        http.httpBasic().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, e) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> users = new ArrayList<>();
        users.addAll(users());
        users.add(ingredientManager());
        users.add(actuatorManager());
        return new InMemoryUserDetailsManager(users);
    }

    private List<UserDetails> users() {
        return authentication.getUsers().stream()
                .map(u -> User.withUsername(u.getUsername()).password(u.getPassword()).roles("USER").build())
                .collect(Collectors.toList());
    }

    private UserDetails ingredientManager() {
        return User.withUsername(authentication.getIngredientManager().getUsername())
                .password(authentication.getIngredientManager().getPassword()).roles("INGREDIENT_MANAGER").build();
    }

    private UserDetails actuatorManager() {
        return User.withUsername(securityProperties.getUser().getName())
                .password(securityProperties.getUser().getPassword())
                .roles(securityProperties.getUser().getRole().toArray(new String[0])).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
