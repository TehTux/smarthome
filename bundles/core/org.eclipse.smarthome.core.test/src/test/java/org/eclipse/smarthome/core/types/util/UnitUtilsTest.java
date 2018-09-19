/**
 * Copyright (c) 2014,2018 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.smarthome.core.types.util;

import static org.eclipse.smarthome.core.library.unit.MetricPrefix.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.measure.Quantity;
import javax.measure.quantity.Temperature;

import org.eclipse.smarthome.core.library.dimension.Intensity;
import org.eclipse.smarthome.core.library.unit.ImperialUnits;
import org.eclipse.smarthome.core.library.unit.SIUnits;
import org.eclipse.smarthome.core.library.unit.SmartHomeUnits;
import org.junit.Test;

public class UnitUtilsTest {

    @Test
    public void forBaseUnitsOfDifferentSystems_shouldBeTrue() {
        assertTrue(UnitUtils.isDifferentMeasurementSystem(SIUnits.CELSIUS, ImperialUnits.FAHRENHEIT));
        assertTrue(UnitUtils.isDifferentMeasurementSystem(ImperialUnits.MILES_PER_HOUR, SIUnits.KILOMETRE_PER_HOUR));
    }

    @Test
    public void forBaseUnitsOfSameSystem_ShouldBeFalse() {
        assertFalse(UnitUtils.isDifferentMeasurementSystem(CENTI(SIUnits.METRE), SIUnits.METRE));
        assertFalse(UnitUtils.isDifferentMeasurementSystem(SIUnits.METRE, MILLI(SIUnits.METRE)));
        assertFalse(UnitUtils.isDifferentMeasurementSystem(CENTI(SIUnits.METRE), MILLI(SIUnits.METRE)));
        assertFalse(UnitUtils.isDifferentMeasurementSystem(ImperialUnits.MILE, ImperialUnits.INCH));
        assertFalse(UnitUtils.isDifferentMeasurementSystem(HECTO(SIUnits.PASCAL), SIUnits.PASCAL));
    }

    @Test
    public void forDerivedUnitsOfDifferentSystems_shouldBeTrue() {
        assertTrue(UnitUtils.isDifferentMeasurementSystem(CENTI(SIUnits.METRE), ImperialUnits.INCH));
        assertTrue(UnitUtils.isDifferentMeasurementSystem(ImperialUnits.MILE, KILO(SIUnits.METRE)));
    }

    @Test
    public void whenValidDimensionIsGiven_shouldCreateQuantityClass() {
        Class<? extends Quantity<?>> temperature = UnitUtils.parseDimension("Temperature");
        assertTrue(Temperature.class.isAssignableFrom(temperature));

        Class<? extends Quantity<?>> intensity = UnitUtils.parseDimension("Intensity");
        assertTrue(Intensity.class.isAssignableFrom(intensity));
    }

    @Test
    public void shouldParseUnitFromPattern() {
        assertThat(UnitUtils.parseUnit("%.2f °F"), is(ImperialUnits.FAHRENHEIT));
        assertThat(UnitUtils.parseUnit("%.2f °C"), is(SIUnits.CELSIUS));
        assertThat(UnitUtils.parseUnit("myLabel km"), is(KILO(SIUnits.METRE)));
        assertThat(UnitUtils.parseUnit("%.2f %%"), is(SmartHomeUnits.PERCENT));
        assertThat(UnitUtils.parseUnit("myLabel %unit%"), is(nullValue()));
    }

    @Test
    public void testParsePureUnit() {
        assertThat(UnitUtils.parseUnit("m"), is(SIUnits.METRE));
        assertThat(UnitUtils.parseUnit("%"), is(SmartHomeUnits.PERCENT));
    }
}
