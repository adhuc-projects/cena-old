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
package org.adhuc.cena.menu.port.adapter.rest;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.adhuc.cena.menu.exception.CenaException;
import org.adhuc.cena.menu.exception.ExceptionCode;

/**
 * An exception occurring while executing a rest-service call with invalid request.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRestRequestException extends CenaException {

    private static final ExceptionCode EXCEPTION_CODE = ExceptionCode.INVALID_REQUEST;

    /**
     * Constructs a new {@code InvalidRestRequestException} with the specified errors.
     *
     * @param errors
     *            the request validation errors.
     */
    public InvalidRestRequestException(final Errors errors) {
        super(EXCEPTION_CODE);
    }

}
