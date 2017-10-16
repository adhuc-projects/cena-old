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
package org.adhuc.cena.menu;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import org.junit.runner.RunWith;

import com.tngtech.archunit.core.importer.ImportOption.DontIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;

/**
 * The menu generation architecture tests.
 *
 * @author Alexandre Carbenay
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "org.adhuc.cena.menu", importOption = DontIncludeTests.class)
public class MenuGenerationArchitectureTest {

    //@formatter:off
    public static final String DOMAIN_PACKAGES      = "org.adhuc.cena.menu.domain..";
    public static final String APP_SERVICE_PACKAGES = "org.adhuc.cena.menu.application..";
    public static final String ADAPTER_PACKAGES     = "org.adhuc.cena.menu.port.adapter..";
    public static final String PERSISTENCE_PACKAGES = "org.adhuc.cena.menu.port.adapter.persistence..";
    public static final String CONTROLLER_PACKAGES  = "org.adhuc.cena.menu.port.adapter.rest..";
    //@formatter:on

    @ArchTest
    public static final ArchRule domainShouldNotAccessElementsOutsideOfDomain =
            noClasses().that().resideInAPackage(DOMAIN_PACKAGES).should().accessClassesThat()
                    .resideInAnyPackage(APP_SERVICE_PACKAGES, ADAPTER_PACKAGES);

    @ArchTest
    public static final ArchRule appServiceShouldNotAccessAdapters            = noClasses().that()
            .resideInAPackage(APP_SERVICE_PACKAGES).should().accessClassesThat().resideInAPackage(ADAPTER_PACKAGES);

    @ArchTest
    public static final ArchRule persistenceShouldNotAccessAppServices        = noClasses().that()
            .resideInAPackage(PERSISTENCE_PACKAGES).should().accessClassesThat().resideInAPackage(APP_SERVICE_PACKAGES);

    @ArchTest
    public static final ArchRule persistenceShouldNotAccessControllers        = noClasses().that()
            .resideInAPackage(PERSISTENCE_PACKAGES).should().accessClassesThat().resideInAPackage(CONTROLLER_PACKAGES);

    @ArchTest
    public static final ArchRule controllersShouldNotAccessPersistence        = noClasses().that()
            .resideInAPackage(CONTROLLER_PACKAGES).should().accessClassesThat().resideInAPackage(PERSISTENCE_PACKAGES);

}
