package it.gabriele.truckflow.domain.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa il Value Object TemperatureRange.
 */
class TemperatureRangeTest {

    @Test
    void shouldCreateTemperatureRange() {
        TemperatureRange range = TemperatureRange.ofCelsius(2, 8);

        assertEquals(2, range.getMinCelsius());
        assertEquals(8, range.getMaxCelsius());
    }

    @Test
    void shouldAllowSameMinimumAndMaximumTemperature() {
        TemperatureRange range = TemperatureRange.ofCelsius(5, 5);

        assertEquals(5, range.getMinCelsius());
        assertEquals(5, range.getMaxCelsius());
    }

    @Test
    void shouldNotAllowMinimumGreaterThanMaximum() {
        assertThrows(IllegalArgumentException.class, () -> TemperatureRange.ofCelsius(10, 2));
    }

    @Test
    void shouldNotAllowInvalidNumbers() {
        assertThrows(IllegalArgumentException.class, () -> TemperatureRange.ofCelsius(Double.NaN, 8));
        assertThrows(IllegalArgumentException.class, () -> TemperatureRange.ofCelsius(2, Double.NaN));

        assertThrows(IllegalArgumentException.class, () -> TemperatureRange.ofCelsius(Double.POSITIVE_INFINITY, 8));
        assertThrows(IllegalArgumentException.class, () -> TemperatureRange.ofCelsius(2, Double.POSITIVE_INFINITY));
    }

    @Test
    void shouldCheckIfTemperatureIsInsideRange() {
        TemperatureRange range = TemperatureRange.ofCelsius(2, 8);

        assertTrue(range.contains(2));
        assertTrue(range.contains(5));
        assertTrue(range.contains(8));
        assertFalse(range.contains(10));
    }

    @Test
    void shouldNotCheckInvalidTemperature() {
        TemperatureRange range = TemperatureRange.ofCelsius(2, 8);

        assertThrows(IllegalArgumentException.class, () -> range.contains(Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> range.contains(Double.POSITIVE_INFINITY));
    }

    @Test
    void shouldCheckIfRequiredRangeIsCoveredByAvailableRange() {
        TemperatureRange requiredRange = TemperatureRange.ofCelsius(2, 8);
        TemperatureRange vehicleRange = TemperatureRange.ofCelsius(0, 10);

        assertTrue(requiredRange.isCoveredBy(vehicleRange));
    }

    @Test
    void shouldDetectWhenRequiredRangeIsNotCovered() {
        TemperatureRange requiredRange = TemperatureRange.ofCelsius(2, 8);
        TemperatureRange vehicleRange = TemperatureRange.ofCelsius(4, 10);

        assertFalse(requiredRange.isCoveredBy(vehicleRange));
    }

    @Test
    void shouldNotCheckCoverageWithNullRange() {
        TemperatureRange range = TemperatureRange.ofCelsius(2, 8);

        assertThrows(IllegalArgumentException.class, () -> range.isCoveredBy(null));
    }

    @Test
    void shouldConsiderEquivalentRangesEqual() {
        TemperatureRange first = TemperatureRange.ofCelsius(2, 8);
        TemperatureRange second = TemperatureRange.ofCelsius(2, 8);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }
}
