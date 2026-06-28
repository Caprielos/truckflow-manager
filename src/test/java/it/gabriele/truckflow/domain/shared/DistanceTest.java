package it.gabriele.truckflow.domain.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa il Value Object Distance.
 */
class DistanceTest {

    @Test
    void shouldCreateDistanceInKilometers() {
        Distance distance = Distance.ofKilometers(120);

        assertEquals(120, distance.getKilometers());
    }

    @Test
    void shouldConvertMetersToKilometers() {
        Distance distance = Distance.ofMeters(1500);

        assertEquals(1.5, distance.getKilometers());
    }

    @Test
    void shouldNotAllowNegativeDistance() {
        assertThrows(IllegalArgumentException.class, () -> Distance.ofKilometers(-1));
    }

    @Test
    void shouldNotAllowInvalidNumber() {
        assertThrows(IllegalArgumentException.class, () -> Distance.ofKilometers(Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> Distance.ofKilometers(Double.POSITIVE_INFINITY));
    }

    @Test
    void shouldCompareDistances() {
        Distance longDistance = Distance.ofKilometers(500);
        Distance shortDistance = Distance.ofKilometers(100);

        assertTrue(longDistance.isGreaterThan(shortDistance));
        assertTrue(shortDistance.isLessThanOrEqualTo(longDistance));
    }

    @Test
    void shouldConsiderOneKilometerEqualToOneThousandMeters() {
        Distance oneKilometer = Distance.ofKilometers(1);
        Distance oneThousandMeters = Distance.ofMeters(1000);

        assertEquals(oneKilometer, oneThousandMeters);
        assertEquals(oneKilometer.hashCode(), oneThousandMeters.hashCode());
    }

    @Test
    void shouldNotCompareWithNullDistance() {
        Distance distance = Distance.ofKilometers(100);

        assertThrows(IllegalArgumentException.class, () -> distance.isGreaterThan(null));
        assertThrows(IllegalArgumentException.class, () -> distance.isLessThanOrEqualTo(null));
    }
}
