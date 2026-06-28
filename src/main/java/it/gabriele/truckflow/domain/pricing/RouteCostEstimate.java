package it.gabriele.truckflow.domain.pricing;

import it.gabriele.truckflow.domain.shared.Distance;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;

import java.util.Objects;

/**
 * Stima dei costi legati al percorso.
 * Può arrivare da calcolo manuale, modello interno o provider esterno.
 */
public final class RouteCostEstimate {

    private static final int MAX_ESTIMATE_CODE_LENGTH = 50;

    private final String estimateCode;
    private final CostEstimationSource source;
    private final Distance estimatedDistance;
    private final Money estimatedFuelCost;
    private final Money estimatedTollCost;
    private final Money estimatedVehicleWearCost;
    private final Notes notes;

    private RouteCostEstimate(
            String estimateCode,
            CostEstimationSource source,
            Distance estimatedDistance,
            Money estimatedFuelCost,
            Money estimatedTollCost,
            Money estimatedVehicleWearCost,
            Notes notes
    ) {
        this.estimateCode = validateEstimateCode(estimateCode);

        if (source == null) {
            throw new IllegalArgumentException("La fonte della stima costi è obbligatoria.");
        }

        if (estimatedDistance == null) {
            throw new IllegalArgumentException("La distanza stimata è obbligatoria.");
        }

        if (estimatedFuelCost == null) {
            throw new IllegalArgumentException("Il costo carburante stimato è obbligatorio.");
        }

        if (estimatedTollCost == null) {
            throw new IllegalArgumentException("Il costo pedaggi stimato è obbligatorio.");
        }

        if (estimatedVehicleWearCost == null) {
            throw new IllegalArgumentException("Il costo usura mezzo stimato è obbligatorio.");
        }

        if (notes == null) {
            throw new IllegalArgumentException("Le note della stima costi sono obbligatorie.");
        }

        validateCurrencyCompatibility(estimatedFuelCost, estimatedTollCost, estimatedVehicleWearCost);

        this.source = source;
        this.estimatedDistance = estimatedDistance;
        this.estimatedFuelCost = estimatedFuelCost;
        this.estimatedTollCost = estimatedTollCost;
        this.estimatedVehicleWearCost = estimatedVehicleWearCost;
        this.notes = notes;
    }

    public static RouteCostEstimate of(
            String estimateCode,
            CostEstimationSource source,
            Distance estimatedDistance,
            Money estimatedFuelCost,
            Money estimatedTollCost,
            Money estimatedVehicleWearCost,
            Notes notes
    ) {
        return new RouteCostEstimate(
                estimateCode,
                source,
                estimatedDistance,
                estimatedFuelCost,
                estimatedTollCost,
                estimatedVehicleWearCost,
                notes
        );
    }

    private static String validateEstimateCode(String estimateCode) {
        if (estimateCode == null) {
            throw new IllegalArgumentException("Il codice stima costi è obbligatorio.");
        }

        String normalizedEstimateCode = estimateCode.trim().toUpperCase();

        if (normalizedEstimateCode.isEmpty()) {
            throw new IllegalArgumentException("Il codice stima costi non può essere vuoto.");
        }

        if (normalizedEstimateCode.length() > MAX_ESTIMATE_CODE_LENGTH) {
            throw new IllegalArgumentException("Il codice stima costi non può superare "
                    + MAX_ESTIMATE_CODE_LENGTH + " caratteri.");
        }

        if (!normalizedEstimateCode.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il codice stima costi può contenere solo lettere, numeri, trattini e underscore.");
        }

        return normalizedEstimateCode;
    }

    private static void validateCurrencyCompatibility(
            Money estimatedFuelCost,
            Money estimatedTollCost,
            Money estimatedVehicleWearCost
    ) {
        estimatedFuelCost.add(estimatedTollCost);
        estimatedFuelCost.add(estimatedVehicleWearCost);
    }

    public String getEstimateCode() {
        return estimateCode;
    }

    public CostEstimationSource getSource() {
        return source;
    }

    public Distance getEstimatedDistance() {
        return estimatedDistance;
    }

    public Money getEstimatedFuelCost() {
        return estimatedFuelCost;
    }

    public Money getEstimatedTollCost() {
        return estimatedTollCost;
    }

    public Money getEstimatedVehicleWearCost() {
        return estimatedVehicleWearCost;
    }

    public Notes getNotes() {
        return notes;
    }

    public boolean isFromExternalProvider() {
        return source.isExternalProvider();
    }

    public boolean isManualEstimate() {
        return source.isManual();
    }

    public boolean hasNotes() {
        return notes.hasText();
    }

    public Money calculateEstimatedRouteCost() {
        return estimatedFuelCost
                .add(estimatedTollCost)
                .add(estimatedVehicleWearCost);
    }

    public String formatSingleLine() {
        return estimateCode
                + " - " + source
                + " - distance: " + estimatedDistance
                + " - route cost: " + calculateEstimatedRouteCost();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RouteCostEstimate that)) return false;
        return estimateCode.equals(that.estimateCode)
                && source == that.source
                && estimatedDistance.equals(that.estimatedDistance)
                && estimatedFuelCost.equals(that.estimatedFuelCost)
                && estimatedTollCost.equals(that.estimatedTollCost)
                && estimatedVehicleWearCost.equals(that.estimatedVehicleWearCost)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                estimateCode,
                source,
                estimatedDistance,
                estimatedFuelCost,
                estimatedTollCost,
                estimatedVehicleWearCost,
                notes
        );
    }

    @Override
    public String toString() {
        return formatSingleLine();
    }
}
