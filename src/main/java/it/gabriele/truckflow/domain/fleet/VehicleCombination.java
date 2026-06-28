package it.gabriele.truckflow.domain.fleet;

import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TemperatureRange;
import it.gabriele.truckflow.domain.shared.Volume;
import it.gabriele.truckflow.domain.shared.Weight;

import java.util.Objects;

/**
 * Rappresenta una combinazione veicolare operativa.
 * Esempio: furgone singolo, camion rigido, trattore stradale + semirimorchio.
 */
public final class VehicleCombination {

    private static final int MAX_COMBINATION_NUMBER_LENGTH = 50;

    private final String combinationNumber;
    private final Vehicle poweredUnit;
    private final Vehicle trailer;
    private final Notes notes;

    private VehicleCombination(
            String combinationNumber,
            Vehicle poweredUnit,
            Vehicle trailer,
            Notes notes
    ) {
        this.combinationNumber = validateCombinationNumber(combinationNumber);

        if (poweredUnit == null) {
            throw new IllegalArgumentException("L'unità motrice è obbligatoria.");
        }

        if (!poweredUnit.isPoweredUnit()) {
            throw new IllegalArgumentException("L'unità motrice deve essere un veicolo motorizzato.");
        }

        if (trailer != null && !trailer.isTrailer()) {
            throw new IllegalArgumentException("Il rimorchio deve essere un veicolo rimorchiato.");
        }

        if (trailer == null && !poweredUnit.canCarryCargo()) {
            throw new IllegalArgumentException("Una combinazione senza rimorchio deve avere un veicolo cargo.");
        }

        if (notes == null) {
            throw new IllegalArgumentException("Le note della combinazione sono obbligatorie.");
        }

        this.poweredUnit = poweredUnit;
        this.trailer = trailer;
        this.notes = notes;
    }

    public static VehicleCombination singleVehicle(
            String combinationNumber,
            Vehicle vehicle,
            Notes notes
    ) {
        return new VehicleCombination(combinationNumber, vehicle, null, notes);
    }

    public static VehicleCombination withTrailer(
            String combinationNumber,
            Vehicle poweredUnit,
            Vehicle trailer,
            Notes notes
    ) {
        if (trailer == null) {
            throw new IllegalArgumentException("Il rimorchio è obbligatorio.");
        }

        return new VehicleCombination(combinationNumber, poweredUnit, trailer, notes);
    }

    private static String validateCombinationNumber(String combinationNumber) {
        if (combinationNumber == null) {
            throw new IllegalArgumentException("Il numero combinazione è obbligatorio.");
        }

        String normalizedCombinationNumber = combinationNumber.trim().toUpperCase();

        if (normalizedCombinationNumber.isEmpty()) {
            throw new IllegalArgumentException("Il numero combinazione non può essere vuoto.");
        }

        if (normalizedCombinationNumber.length() > MAX_COMBINATION_NUMBER_LENGTH) {
            throw new IllegalArgumentException("Il numero combinazione non può superare " + MAX_COMBINATION_NUMBER_LENGTH + " caratteri.");
        }

        if (!normalizedCombinationNumber.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il numero combinazione può contenere solo lettere, numeri, trattini e underscore.");
        }

        return normalizedCombinationNumber;
    }

    public String getCombinationNumber() {
        return combinationNumber;
    }

    public Vehicle getPoweredUnit() {
        return poweredUnit;
    }

    public Vehicle getTrailer() {
        return trailer;
    }

    public Notes getNotes() {
        return notes;
    }

    public boolean hasTrailer() {
        return trailer != null;
    }

    public Vehicle getCargoUnit() {
        if (trailer != null && trailer.canCarryCargo()) {
            return trailer;
        }

        if (poweredUnit.canCarryCargo()) {
            return poweredUnit;
        }

        throw new IllegalStateException("La combinazione non ha un'unità cargo.");
    }

    public Weight getMaxPayload() {
        return getCargoUnit().getMaxPayload();
    }

    public Dimension getCargoSpaceDimension() {
        return getCargoUnit().getCargoSpaceDimension();
    }

    public Volume calculateCargoSpaceVolume() {
        return getCargoUnit().calculateCargoSpaceVolume();
    }

    public boolean canBeAssigned() {
        return poweredUnit.canBeAssigned()
                && (trailer == null || trailer.canBeAssigned());
    }

    public boolean canCarryWeight(Weight weight) {
        if (weight == null) {
            throw new IllegalArgumentException("Il peso da verificare è obbligatorio.");
        }

        return getCargoUnit().canCarryWeight(weight);
    }

    public boolean canFitDimension(Dimension dimension) {
        if (dimension == null) {
            throw new IllegalArgumentException("Le dimensioni da verificare sono obbligatorie.");
        }

        return getCargoUnit().canFitDimension(dimension);
    }

    public boolean supportsTemperatureControl() {
        return getCargoUnit().supportsTemperatureControl();
    }

    public boolean canSupportTemperatureRange(TemperatureRange requiredTemperatureRange) {
        if (requiredTemperatureRange == null) {
            throw new IllegalArgumentException("L'intervallo di temperatura richiesto è obbligatorio.");
        }

        return getCargoUnit().canSupportTemperatureRange(requiredTemperatureRange);
    }

    public boolean hasNotes() {
        return notes.hasText();
    }

    public String formatSingleLine() {
        if (trailer == null) {
            return combinationNumber + " - " + poweredUnit.getFleetNumber();
        }

        return combinationNumber + " - " + poweredUnit.getFleetNumber() + " + " + trailer.getFleetNumber();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VehicleCombination that)) return false;
        return combinationNumber.equals(that.combinationNumber)
                && poweredUnit.equals(that.poweredUnit)
                && Objects.equals(trailer, that.trailer)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(combinationNumber, poweredUnit, trailer, notes);
    }

    @Override
    public String toString() {
        return formatSingleLine();
    }
}
