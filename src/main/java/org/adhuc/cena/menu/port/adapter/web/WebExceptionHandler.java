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
package org.adhuc.cena.menu.port.adapter.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * A controller advice ensuring that the client is redirected to the application root if no handler is found.
 * <p>
 * This is necessary when using the HTML5 mode: when a MVC handler is not found, we redirect to the index and let the
 * simple page application's root examine the path and redirect to the route described by the URL.<br/>
 * For example:
 *
 * <pre>
 * http://server/show/some/page
 * </pre>
 *
 * There is no handler for "show/some/page" because it's an SPA route, so we return the "http://server/" view that
 * loads the single page application. Then the SPA handles the route path and displays the matching view.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@ControllerAdvice
public class WebExceptionHandler {

    @Autowired
    private WebIndexController indexController;

    /**
     * Handles a {@link NoHandlerFoundException} and convert it to a view response.
     *
     * @param exception
     *            the exception to convert.
     *
     * @return the view response containing redirection.
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFoundException(NoHandlerFoundException exception) {
        return indexController.index();
    }

}
