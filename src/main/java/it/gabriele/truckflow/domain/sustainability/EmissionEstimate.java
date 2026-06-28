package it.gabriele.truckflow.domain.sustainability;

import it.gabriele.truckflow.domain.shared.Distance;
import it.gabriele.truckflow.domain.shared.Notes;

import java.util.Objects;

/**
 * Stima interna delle emissioni di una spedizione o missione.
 */
public final class EmissionEstimate {

    private static final int MAX_CODE_LENGTH = 50;

    private final String estimateNumber;
    private final String shipmentNumber;
    private final String routeNumber;
    private final Distance distance;
    private final FuelType fuelType;
    private final EmissionStandard emissionStandard;
    private final double estimatedEnergyAmount;
    private final double estimatedCo2Kg;
    private final EmissionRating rating;
    private final Notes notes;

    private EmissionEstimate(
            String estimateNumber,
            String shipmentNumber,
            String routeNumber,
            Distance distance,
            FuelType fuelType,
            EmissionStandard emissionStandard,
            double estimatedEnergyAmount,
            double estimatedCo2Kg,
            EmissionRating rating,
            Notes notes
    ) {
        this.estimateNumber = validateCode(estimateNumber, "Il numero stima emissioni è obbligatorio.");
        this.shipmentNumber = validateCode(shipmentNumber, "Il numero spedizione è obbligatorio.");
        this.routeNumber = validateCode(routeNumber, "Il numero tratta è obbligatorio.");

        if (distance == null) {
            throw new IllegalArgumentException("La distanza della stima emissioni è obbligatoria.");
        }

        if (fuelType == null) {
            throw new IllegalArgumentException("Il tipo carburante è obbligatorio.");
        }

        if (emissionStandard == null) {
            throw new IllegalArgumentException("La classe emissiva è obbligatoria.");
        }

        validateNonNegativeFinite(estimatedEnergyAmount, "Il consumo energetico stimato non è valido.");
        validateNonNegativeFinite(estimatedCo2Kg, "La CO2 stimata non è valida.");

        if (rating == null) {
            throw new IllegalArgumentException("Il rating emissioni è obbligatorio.");
        }

        if (notes == null) {
            throw new IllegalArgumentException("Le note emissioni sono obbligatorie.");
        }

        this.distance = distance;
        this.fuelType = fuelType;
        this.emissionStandard = emissionStandard;
        this.estimatedEnergyAmount = estimatedEnergyAmount;
        this.estimatedCo2Kg = estimatedCo2Kg;
        this.rating = rating;
        this.notes = notes;
    }

    public static EmissionEstimate of(
            String estimateNumber,
            String shipmentNumber,
            String routeNumber,
            Distance distance,
            FuelType fuelType,
            EmissionStandard emissionStandard,
            double estimatedEnergyAmount,
            double estimatedCo2Kg,
            EmissionRating rating,
            Notes notes
    ) {
        return new EmissionEstimate(
                estimateNumber,
                shipmentNumber,
                routeNumber,
                distance,
                fuelType,
                emissionStandard,
                estimatedEnergyAmount,
                estimatedCo2Kg,
                rating,
                notes
        );
    }

    private static String validateCode(String code, String nullMessage) {
        if (code == null) {
            throw new IllegalArgumentException(nullMessage);
        }

        String normalizedCode = code.trim().toUpperCase();

        if (normalizedCode.isEmpty()) {
            throw new IllegalArgumentException(nullMessage);
        }

        if (normalizedCode.length() > MAX_CODE_LENGTH) {
            throw new IllegalArgumentException("Il codice non può superare " + MAX_CODE_LENGTH + " caratteri.");
        }

        if (!normalizedCode.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il codice può contenere solo lettere, numeri, trattini e underscore.");
        }

        return normalizedCode;
    }

    private static void validateNonNegativeFinite(double value, String message) {
        if (Double.isNaN(value) || Double.isInfinite(value) || value < 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public String getEstimateNumber() {
        return estimateNumber;
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public Distance getDistance() {
        return distance;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public EmissionStandard getEmissionStandard() {
        return emissionStandard;
    }

    public double getEstimatedEnergyAmount() {
        return estimatedEnergyAmount;
    }

    public double getEstimatedCo2Kg() {
        return estimatedCo2Kg;
    }

    public EmissionRating getRating() {
        return rating;
    }

    public Notes getNotes() {
        return notes;
    }

    public boolean isZeroTailpipeEmission() {
        return fuelType.isZeroTailpipeEmission();
    }

    public boolean isLowEmissionVehicle() {
        return emissionStandard.isLowEmissionStandard()
                || fuelType.isLowerEmissionAlternative();
    }

    public boolean isHighImpact() {
        return rating == EmissionRating.HIGH
                || rating == EmissionRating.VERY_HIGH;
    }

    public boolean hasNotes() {
        return notes.hasText();
    }

    public boolean isForShipment(String shipmentNumber) {
        return this.shipmentNumber.equals(validateCode(shipmentNumber, "Il numero spedizione da verificare è obbligatorio."));
    }

    public boolean isForRoute(String routeNumber) {
        return this.routeNumber.equals(validateCode(routeNumber, "Il numero tratta da verificare è obbligatorio."));
    }

    public String formatSingleLine() {
        return estimateNumber
                + " - shipment: " + shipmentNumber
                + " - route: " + routeNumber
                + " - CO2: " + estimatedCo2Kg + " kg"
                + " - " + rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmissionEstimate that)) return false;
        return Double.compare(estimatedEnergyAmount, that.estimatedEnergyAmount) == 0
                && Double.compare(estimatedCo2Kg, that.estimatedCo2Kg) == 0
                && estimateNumber.equals(that.estimateNumber)
                && shipmentNumber.equals(that.shipmentNumber)
                && routeNumber.equals(that.routeNumber)
                && distance.equals(that.distance)
                && fuelType == that.fuelType
                && emissionStandard == that.emissionStandard
                && rating == that.rating
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                estimateNumber,
                shipmentNumber,
                routeNumber,
                distance,
                fuelType,
                emissionStandard,
                estimatedEnergyAmount,
                estimatedCo2Kg,
                rating,
                notes
        );
    }

    @Override
    public String toString() {
        return formatSingleLine();
    }
}
