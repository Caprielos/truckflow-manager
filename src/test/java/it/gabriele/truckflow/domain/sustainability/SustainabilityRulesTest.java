package it.gabriele.truckflow.domain.sustainability;

import it.gabriele.truckflow.domain.shared.Distance;
import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa SustainabilityRules.
 */
class SustainabilityRulesTest {

    @Test
    void shouldDetectLowEmissionTransport() {
        assertTrue(SustainabilityRules.isLowEmissionTransport(lowEmissionEstimate()));
        assertFalse(SustainabilityRules.isLowEmissionTransport(standardEstimate()));
    }

    @Test
    void shouldDetectHighImpactTransport() {
        assertTrue(SustainabilityRules.isHighImpactTransport(highImpactEstimate()));
        assertFalse(SustainabilityRules.isHighImpactTransport(lowEmissionEstimate()));
    }

    @Test
    void shouldRequireReviewForVeryHighOrUnknownEstimates() {
        assertTrue(SustainabilityRules.requiresSustainabilityReview(highImpactEstimate()));

        EmissionEstimate unknown = EmissionEstimate.of(
                "EMI-999",
                "SHP-999",
                "RTE-999",
                Distance.ofKilometers(100),
                FuelType.UNKNOWN,
                EmissionStandard.UNKNOWN,
                0,
                0,
                EmissionRating.MEDIUM,
                Notes.empty()
        );

        assertTrue(SustainabilityRules.requiresSustainabilityReview(unknown));
        assertFalse(SustainabilityRules.requiresSustainabilityReview(lowEmissionEstimate()));
    }

    @Test
    void shouldDetectZeroTailpipeEmission() {
        assertTrue(SustainabilityRules.isZeroTailpipeEmission(lowEmissionEstimate()));
        assertFalse(SustainabilityRules.isZeroTailpipeEmission(standardEstimate()));
    }

    @Test
    void shouldCompareEmissionStandards() {
        assertTrue(SustainabilityRules.hasBetterEmissionStandard(
                lowEmissionEstimate(),
                standardEstimate()
        ));

        assertFalse(SustainabilityRules.hasBetterEmissionStandard(
                standardEstimate(),
                lowEmissionEstimate()
        ));
    }

    @Test
    void shouldCalculateTotalCo2() {
        double total = SustainabilityRules.calculateTotalCo2Kg(List.of(
                standardEstimate(),
                lowEmissionEstimate(),
                highImpactEstimate()
        ));

        assertEquals(1980.7, total);
    }

    @Test
    void shouldCheckListRules() {
        assertTrue(SustainabilityRules.containsHighImpactEstimate(List.of(
                standardEstimate(),
                highImpactEstimate()
        )));

        assertTrue(SustainabilityRules.allEstimatesAreLowEmission(List.of(
                lowEmissionEstimate()
        )));

        assertFalse(SustainabilityRules.allEstimatesAreLowEmission(List.of()));
        assertFalse(SustainabilityRules.allEstimatesAreLowEmission(List.of(
                standardEstimate(),
                lowEmissionEstimate()
        )));
    }

    @Test
    void shouldNotAllowNullValues() {
        EmissionEstimate estimate = standardEstimate();

        assertThrows(IllegalArgumentException.class, () -> SustainabilityRules.isLowEmissionTransport(null));
        assertThrows(IllegalArgumentException.class, () -> SustainabilityRules.isHighImpactTransport(null));
        assertThrows(IllegalArgumentException.class, () -> SustainabilityRules.requiresSustainabilityReview(null));
        assertThrows(IllegalArgumentException.class, () -> SustainabilityRules.isZeroTailpipeEmission(null));
        assertThrows(IllegalArgumentException.class, () -> SustainabilityRules.hasBetterEmissionStandard(null, estimate));
        assertThrows(IllegalArgumentException.class, () -> SustainabilityRules.hasBetterEmissionStandard(estimate, null));
        assertThrows(IllegalArgumentException.class, () -> SustainabilityRules.calculateTotalCo2Kg(null));
        assertThrows(IllegalArgumentException.class, () -> SustainabilityRules.containsHighImpactEstimate(null));
        assertThrows(IllegalArgumentException.class, () -> SustainabilityRules.allEstimatesAreLowEmission(null));
    }

    @Test
    void shouldNotAllowNullItemsInsideList() {
        List<EmissionEstimate> estimatesWithNull = Arrays.asList(standardEstimate(), null);

        assertThrows(IllegalArgumentException.class, () -> SustainabilityRules.calculateTotalCo2Kg(estimatesWithNull));
        assertThrows(IllegalArgumentException.class, () -> SustainabilityRules.containsHighImpactEstimate(estimatesWithNull));
        assertThrows(IllegalArgumentException.class, () -> SustainabilityRules.allEstimatesAreLowEmission(estimatesWithNull));
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

    private static EmissionEstimate lowEmissionEstimate() {
        return EmissionEstimate.of(
                "EMI-002",
                "SHP-002",
                "RTE-002",
                Distance.ofKilometers(200),
                FuelType.ELECTRIC,
                EmissionStandard.ZERO_EMISSION,
                120,
                0,
                EmissionRating.LOW,
                Notes.empty()
        );
    }

    private static EmissionEstimate highImpactEstimate() {
        return EmissionEstimate.of(
                "EMI-003",
                "SHP-003",
                "RTE-003",
                Distance.ofKilometers(1200),
                FuelType.DIESEL,
                EmissionStandard.EURO_3,
                400,
                1500,
                EmissionRating.VERY_HIGH,
                Notes.empty()
        );
    }
}
