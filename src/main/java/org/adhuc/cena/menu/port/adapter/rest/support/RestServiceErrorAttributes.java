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
package org.adhuc.cena.menu.port.adapter.rest.support;

import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;

import org.adhuc.cena.menu.exception.CenaException;
import org.adhuc.cena.menu.exception.ExceptionCode;

/**
 * An {@link org.springframework.boot.autoconfigure.web.ErrorAttributes ErrorAttributes} implementation based on the
 * {@link DefaultErrorAttributes} implementation, that provides the {@link ExceptionCode} value in the response.
 *
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Component
public class RestServiceErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        final Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
        // Explicitly remove "exception" attribute
        errorAttributes.remove("exception");
        addExceptionCode(errorAttributes, requestAttributes);
        return errorAttributes;
    }

    private void addExceptionCode(Map<String, Object> errorAttributes, RequestAttributes requestAttributes) {
        Throwable error = getError(requestAttributes);
        if (error != null) {
            while (ServletException.class.isAssignableFrom(error.getClass()) && error.getCause() != null) {
                error = ((ServletException) error).getCause();
            }
            addExceptionCode(errorAttributes, error);
        }
    }

    private void addExceptionCode(Map<String, Object> errorAttributes, Throwable error) {
        ExceptionCode exceptionCode = ExceptionCode.INTERNAL_ERROR;
        if (CenaException.class.isAssignableFrom(error.getClass())) {
            final CenaException cenaException = (CenaException) error;
            exceptionCode = cenaException.exceptionCode();
        }
        errorAttributes.put("code", exceptionCode.code());
    }

}
