package it.gabriele.truckflow.domain.sustainability;

import java.util.List;
import java.util.Objects;

/**
 * Regole di dominio per la sostenibilità.
 */
public final class SustainabilityRules {

    private SustainabilityRules() {
    }

    public static boolean isLowEmissionTransport(EmissionEstimate estimate) {
        validateEstimate(estimate);

        return estimate.isLowEmissionVehicle()
                && estimate.getRating() == EmissionRating.LOW;
    }

    public static boolean isHighImpactTransport(EmissionEstimate estimate) {
        validateEstimate(estimate);

        return estimate.isHighImpact();
    }

    public static boolean requiresSustainabilityReview(EmissionEstimate estimate) {
        validateEstimate(estimate);

        return estimate.getRating() == EmissionRating.VERY_HIGH
                || estimate.getEmissionStandard() == EmissionStandard.UNKNOWN
                || estimate.getFuelType() == FuelType.UNKNOWN;
    }

    public static boolean isZeroTailpipeEmission(EmissionEstimate estimate) {
        validateEstimate(estimate);

        return estimate.isZeroTailpipeEmission();
    }

    public static boolean hasBetterEmissionStandard(
            EmissionEstimate first,
            EmissionEstimate second
    ) {
        validateEstimate(first);
        validateEstimate(second);

        return first.getEmissionStandard().getLevel() > second.getEmissionStandard().getLevel();
    }

    public static double calculateTotalCo2Kg(List<EmissionEstimate> estimates) {
        validateEstimates(estimates);

        return estimates.stream()
                .mapToDouble(EmissionEstimate::getEstimatedCo2Kg)
                .sum();
    }

    public static boolean containsHighImpactEstimate(List<EmissionEstimate> estimates) {
        validateEstimates(estimates);

        return estimates.stream()
                .anyMatch(EmissionEstimate::isHighImpact);
    }

    public static boolean allEstimatesAreLowEmission(List<EmissionEstimate> estimates) {
        validateEstimates(estimates);

        if (estimates.isEmpty()) {
            return false;
        }

        return estimates.stream()
                .allMatch(SustainabilityRules::isLowEmissionTransport);
    }

    private static void validateEstimate(EmissionEstimate estimate) {
        if (estimate == null) {
            throw new IllegalArgumentException("La stima emissioni è obbligatoria.");
        }
    }

    private static void validateEstimates(List<EmissionEstimate> estimates) {
        if (estimates == null) {
            throw new IllegalArgumentException("La lista stime emissioni è obbligatoria.");
        }

        if (estimates.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("La lista stime emissioni non può contenere valori nulli.");
        }
    }
}
