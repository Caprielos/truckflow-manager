package it.gabriele.truckflow.domain.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa il Value Object Volume.
 */
class VolumeTest {

    @Test
    void shouldCreateVolumeInCubicMeters() {
        Volume volume = Volume.ofCubicMeters(12);

        assertEquals(12, volume.getCubicMeters());
    }

    @Test
    void shouldConvertLitersToCubicMeters() {
        Volume volume = Volume.ofLiters(1500);

        assertEquals(1.5, volume.getCubicMeters());
    }

    @Test
    void shouldNotAllowNegativeVolume() {
        assertThrows(IllegalArgumentException.class, () -> Volume.ofCubicMeters(-1));
    }

    @Test
    void shouldNotAllowInvalidNumber() {
        assertThrows(IllegalArgumentException.class, () -> Volume.ofCubicMeters(Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> Volume.ofCubicMeters(Double.POSITIVE_INFINITY));
    }

    @Test
    void shouldCompareVolumes() {
        Volume largeVolume = Volume.ofCubicMeters(20);
        Volume smallVolume = Volume.ofCubicMeters(10);

        assertTrue(largeVolume.isGreaterThan(smallVolume));
        assertTrue(smallVolume.isLessThanOrEqualTo(largeVolume));
    }

    @Test
    void shouldConsiderOneCubicMeterEqualToOneThousandLiters() {
        Volume oneCubicMeter = Volume.ofCubicMeters(1);
        Volume oneThousandLiters = Volume.ofLiters(1000);

        assertEquals(oneCubicMeter, oneThousandLiters);
        assertEquals(oneCubicMeter.hashCode(), oneThousandLiters.hashCode());
    }

    @Test
    void shouldNotCompareWithNullVolume() {
        Volume volume = Volume.ofCubicMeters(10);

        assertThrows(IllegalArgumentException.class, () -> volume.isGreaterThan(null));
        assertThrows(IllegalArgumentException.class, () -> volume.isLessThanOrEqualTo(null));
    }
}
