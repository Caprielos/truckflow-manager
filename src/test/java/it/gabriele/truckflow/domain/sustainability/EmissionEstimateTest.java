package it.gabriele.truckflow.domain.sustainability;

import it.gabriele.truckflow.domain.shared.Distance;
import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa EmissionEstimate.
 */
class EmissionEstimateTest {

    @Test
    void shouldCreateEmissionEstimate() {
        EmissionEstimate estimate = standardEstimate();

        assertEquals("EMI-001", estimate.getEstimateNumber());
        assertEquals("SHP-001", estimate.getShipmentNumber());
        assertEquals("RTE-001", estimate.getRouteNumber());
        assertEquals(Distance.ofKilometers(580), estimate.getDistance());
        assertEquals(FuelType.DIESEL, estimate.getFuelType());
        assertEquals(EmissionStandard.EURO_6, estimate.getEmissionStandard());
        assertEquals(180.5, estimate.getEstimatedEnergyAmount());
        assertEquals(480.7, estimate.getEstimatedCo2Kg());
        assertEquals(EmissionRating.MEDIUM, estimate.getRating());
        assertFalse(estimate.isZeroTailpipeEmission());
        assertTrue(estimate.isLowEmissionVehicle());
        assertFalse(estimate.isHighImpact());
    }

    @Test
    void shouldNormalizeCodes() {
        EmissionEstimate estimate = EmissionEstimate.of(
                "  emi_001  ",
                "  shp_001  ",
                "  rte_001  ",
                Distance.ofKilometers(100),
                FuelType.ELECTRIC,
                EmissionStandard.ZERO_EMISSION,
                80,
                0,
                EmissionRating.LOW,
                Notes.empty()
        );

        assertEquals("EMI_001", estimate.getEstimateNumber());
        assertEquals("SHP_001", estimate.getShipmentNumber());
        assertEquals("RTE_001", estimate.getRouteNumber());
    }

    @Test
    void shouldRejectInvalidCodes() {
        assertThrows(IllegalArgumentException.class, () -> EmissionEstimate.of(
                null,
                "SHP-001",
                "RTE-001",
                Distance.ofKilometers(100),
                FuelType.DIESEL,
                EmissionStandard.EURO_6,
                10,
                20,
                EmissionRating.LOW,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> EmissionEstimate.of(
                "EMI 001",
                "SHP-001",
                "RTE-001",
                Distance.ofKilometers(100),
                FuelType.DIESEL,
                EmissionStandard.EURO_6,
                10,
                20,
                EmissionRating.LOW,
                Notes.empty()
        ));
    }

    @Test
    void shouldRejectNullMandatoryFields() {
        assertThrows(IllegalArgumentException.class, () -> EmissionEstimate.of(
                "EMI-001",
                "SHP-001",
                "RTE-001",
                null,
                FuelType.DIESEL,
                EmissionStandard.EURO_6,
                10,
                20,
                EmissionRating.LOW,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> EmissionEstimate.of(
                "EMI-001",
                "SHP-001",
                "RTE-001",
                Distance.ofKilometers(100),
                null,
                EmissionStandard.EURO_6,
                10,
                20,
                EmissionRating.LOW,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> EmissionEstimate.of(
                "EMI-001",
                "SHP-001",
                "RTE-001",
                Distance.ofKilometers(100),
                FuelType.DIESEL,
                null,
                10,
                20,
                EmissionRating.LOW,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> EmissionEstimate.of(
                "EMI-001",
                "SHP-001",
                "RTE-001",
                Distance.ofKilometers(100),
                FuelType.DIESEL,
                EmissionStandard.EURO_6,
                10,
                20,
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> EmissionEstimate.of(
                "EMI-001",
                "SHP-001",
                "RTE-001",
                Distance.ofKilometers(100),
                FuelType.DIESEL,
                EmissionStandard.EURO_6,
                10,
                20,
                EmissionRating.LOW,
                null
        ));
    }

    @Test
    void shouldRejectInvalidNumericValues() {
        assertThrows(IllegalArgumentException.class, () -> EmissionEstimate.of(
                "EMI-001",
                "SHP-001",
                "RTE-001",
                Distance.ofKilometers(100),
                FuelType.DIESEL,
                EmissionStandard.EURO_6,
                -1,
                20,
                EmissionRating.LOW,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> EmissionEstimate.of(
                "EMI-001",
                "SHP-001",
                "RTE-001",
                Distance.ofKilometers(100),
                FuelType.DIESEL,
                EmissionStandard.EURO_6,
                10,
                Double.NaN,
                EmissionRating.LOW,
                Notes.empty()
        ));
    }

    @Test
    void shouldDetectZeroTailpipeEmission() {
        EmissionEstimate estimate = EmissionEstimate.of(
                "EMI-002",
                "SHP-001",
                "RTE-001",
                Distance.ofKilometers(100),
                FuelType.ELECTRIC,
                EmissionStandard.ZERO_EMISSION,
                120,
                0,
                EmissionRating.LOW,
                Notes.empty()
        );

        assertTrue(estimate.isZeroTailpipeEmission());
        assertTrue(estimate.isLowEmissionVehicle());
    }

    @Test
    void shouldCheckShipmentAndRoute() {
        EmissionEstimate estimate = standardEstimate();

        assertTrue(estimate.isForShipment("shp-001"));
        assertTrue(estimate.isForRoute("rte-001"));
        assertFalse(estimate.isForShipment("SHP-999"));
        assertFalse(estimate.isForRoute("RTE-999"));
    }

    @Test
    void shouldDetectNotes() {
        EmissionEstimate estimate = EmissionEstimate.of(
                "EMI-001",
                "SHP-001",
                "RTE-001",
                Distance.ofKilometers(580),
                FuelType.DIESEL,
                EmissionStandard.EURO_6,
                180.5,
                480.7,
                EmissionRating.MEDIUM,
                Notes.of("Stima calcolata da modello interno")
        );

        assertTrue(estimate.hasNotes());
    }

    @Test
    void shouldFormatSingleLine() {
        assertEquals(
                "EMI-001 - shipment: SHP-001 - route: RTE-001 - CO2: 480.7 kg - MEDIUM",
                standardEstimate().formatSingleLine()
        );
    }

    @Test
    void shouldConsiderEquivalentEstimatesEqual() {
        EmissionEstimate first = standardEstimate();
        EmissionEstimate second = standardEstimate();

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldExposeEnumDetails() {
        assertTrue(FuelType.DIESEL.isCombustionBased());
        assertTrue(FuelType.ELECTRIC.isZeroTailpipeEmission());
        assertTrue(FuelType.HVO.isLowerEmissionAlternative());

        assertTrue(EmissionStandard.EURO_6.isLowEmissionStandard());
        assertTrue(EmissionStandard.ZERO_EMISSION.isAtLeast(EmissionStandard.EURO_6));

        assertTrue(EmissionRating.VERY_HIGH.isWorseThan(EmissionRating.HIGH));
    }

    private static EmissionEstimate standardEstimate() {
        return EmissionEstimate.of(
                "EMI-001",
                "SHP-001",
                "RTE-001",
                Distance.ofKilometers(580),
                FuelType.DIESEL,
                EmissionStandard.EURO_6,
                180.5,
                480.7,
                EmissionRating.MEDIUM,
                Notes.empty()
        );
    }
}
