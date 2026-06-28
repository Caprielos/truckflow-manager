package it.gabriele.truckflow.domain.pricing;

import it.gabriele.truckflow.domain.shared.Distance;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa RouteCostEstimate.
 */
class RouteCostEstimateTest {

    @Test
    void shouldCreateRouteCostEstimate() {
        RouteCostEstimate estimate = standardEstimate();

        assertEquals("EST-001", estimate.getEstimateCode());
        assertEquals(CostEstimationSource.VIAMICHELIN, estimate.getSource());
        assertEquals(Distance.ofKilometers(580), estimate.getEstimatedDistance());
        assertEquals(Money.of("220.00", "EUR"), estimate.getEstimatedFuelCost());
        assertEquals(Money.of("80.00", "EUR"), estimate.getEstimatedTollCost());
        assertEquals(Money.of("60.00", "EUR"), estimate.getEstimatedVehicleWearCost());
        assertEquals(Money.of("360.00", "EUR"), estimate.calculateEstimatedRouteCost());
        assertTrue(estimate.isFromExternalProvider());
        assertFalse(estimate.isManualEstimate());
    }

    @Test
    void shouldNormalizeEstimateCode() {
        RouteCostEstimate estimate = RouteCostEstimate.of(
                "  est_001  ",
                CostEstimationSource.MANUAL,
                Distance.ofKilometers(100),
                Money.of("50.00", "EUR"),
                Money.of("10.00", "EUR"),
                Money.of("20.00", "EUR"),
                Notes.empty()
        );

        assertEquals("EST_001", estimate.getEstimateCode());
        assertTrue(estimate.isManualEstimate());
    }

    @Test
    void shouldRejectInvalidEstimateCode() {
        assertThrows(IllegalArgumentException.class, () -> RouteCostEstimate.of(
                null,
                CostEstimationSource.MANUAL,
                Distance.ofKilometers(100),
                Money.of("50.00", "EUR"),
                Money.of("10.00", "EUR"),
                Money.of("20.00", "EUR"),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> RouteCostEstimate.of(
                "EST 001",
                CostEstimationSource.MANUAL,
                Distance.ofKilometers(100),
                Money.of("50.00", "EUR"),
                Money.of("10.00", "EUR"),
                Money.of("20.00", "EUR"),
                Notes.empty()
        ));
    }

    @Test
    void shouldRejectNullMandatoryFields() {
        assertThrows(IllegalArgumentException.class, () -> RouteCostEstimate.of(
                "EST-001",
                null,
                Distance.ofKilometers(100),
                Money.of("50.00", "EUR"),
                Money.of("10.00", "EUR"),
                Money.of("20.00", "EUR"),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> RouteCostEstimate.of(
                "EST-001",
                CostEstimationSource.MANUAL,
                null,
                Money.of("50.00", "EUR"),
                Money.of("10.00", "EUR"),
                Money.of("20.00", "EUR"),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> RouteCostEstimate.of(
                "EST-001",
                CostEstimationSource.MANUAL,
                Distance.ofKilometers(100),
                null,
                Money.of("10.00", "EUR"),
                Money.of("20.00", "EUR"),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> RouteCostEstimate.of(
                "EST-001",
                CostEstimationSource.MANUAL,
                Distance.ofKilometers(100),
                Money.of("50.00", "EUR"),
                Money.of("10.00", "EUR"),
                Money.of("20.00", "EUR"),
                null
        ));
    }

    @Test
    void shouldRejectDifferentCurrencies() {
        assertThrows(IllegalArgumentException.class, () -> RouteCostEstimate.of(
                "EST-001",
                CostEstimationSource.MANUAL,
                Distance.ofKilometers(100),
                Money.of("50.00", "EUR"),
                Money.of("10.00", "USD"),
                Money.of("20.00", "EUR"),
                Notes.empty()
        ));
    }

    @Test
    void shouldDetectNotes() {
        RouteCostEstimate estimate = RouteCostEstimate.of(
                "EST-001",
                CostEstimationSource.VIAMICHELIN,
                Distance.ofKilometers(580),
                Money.of("220.00", "EUR"),
                Money.of("80.00", "EUR"),
                Money.of("60.00", "EUR"),
                Notes.of("Stima importata")
        );

        assertTrue(estimate.hasNotes());
    }

    @Test
    void shouldFormatSingleLine() {
        assertEquals(
                "EST-001 - VIAMICHELIN - distance: 580.0 km - route cost: 360.00 EUR",
                standardEstimate().formatSingleLine()
        );
    }

    @Test
    void shouldConsiderEquivalentEstimatesEqual() {
        RouteCostEstimate first = standardEstimate();
        RouteCostEstimate second = standardEstimate();

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldExposeSourceDetails() {
        assertTrue(CostEstimationSource.VIAMICHELIN.isExternalProvider());
        assertTrue(CostEstimationSource.HERE_MAPS.isExternalProvider());
        assertFalse(CostEstimationSource.INTERNAL_MODEL.isExternalProvider());
        assertTrue(CostEstimationSource.MANUAL.isManual());
    }

    private static RouteCostEstimate standardEstimate() {
        return RouteCostEstimate.of(
                "EST-001",
                CostEstimationSource.VIAMICHELIN,
                Distance.ofKilometers(580),
                Money.of("220.00", "EUR"),
                Money.of("80.00", "EUR"),
                Money.of("60.00", "EUR"),
                Notes.empty()
        );
    }
}
