package it.gabriele.truckflow.domain.cargo;

import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TemperatureRange;
import it.gabriele.truckflow.domain.shared.Volume;
import it.gabriele.truckflow.domain.shared.Weight;

import java.util.Objects;

/**
 * Rappresenta un elemento di carico.
 * Esempio: pallet, scatola, bancale, lotto di merce.
 */
public final class CargoItem {

    private static final int MAX_DESCRIPTION_LENGTH = 200;

    private final String description;
    private final CargoCategory category;
    private final Weight weight;
    private final Dimension dimension;
    private final TemperatureRange requiredTemperatureRange;
    private final Notes notes;

    private CargoItem(
            String description,
            CargoCategory category,
            Weight weight,
            Dimension dimension,
            TemperatureRange requiredTemperatureRange,
            Notes notes
    ) {
        if (description == null) {
            throw new IllegalArgumentException("La descrizione del carico è obbligatoria.");
        }

        String normalizedDescription = description.trim();

        if (normalizedDescription.isEmpty()) {
            throw new IllegalArgumentException("La descrizione del carico non può essere vuota.");
        }

        if (normalizedDescription.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("La descrizione del carico non può superare " + MAX_DESCRIPTION_LENGTH + " caratteri.");
        }

        if (category == null) {
            throw new IllegalArgumentException("La categoria del carico è obbligatoria.");
        }

        if (weight == null) {
            throw new IllegalArgumentException("Il peso del carico è obbligatorio.");
        }

        if (dimension == null) {
            throw new IllegalArgumentException("Le dimensioni del carico sono obbligatorie.");
        }

        if (notes == null) {
            throw new IllegalArgumentException("Le note del carico sono obbligatorie.");
        }

        if (category.requiresTemperatureControl() && requiredTemperatureRange == null) {
            throw new IllegalArgumentException("Questa categoria di carico richiede un intervallo di temperatura.");
        }

        this.description = normalizedDescription;
        this.category = category;
        this.weight = weight;
        this.dimension = dimension;
        this.requiredTemperatureRange = requiredTemperatureRange;
        this.notes = notes;
    }

    public static CargoItem of(
            String description,
            CargoCategory category,
            Weight weight,
            Dimension dimension,
            Notes notes
    ) {
        return new CargoItem(description, category, weight, dimension, null, notes);
    }

    public static CargoItem temperatureControlled(
            String description,
            CargoCategory category,
            Weight weight,
            Dimension dimension,
            TemperatureRange requiredTemperatureRange,
            Notes notes
    ) {
        if (requiredTemperatureRange == null) {
            throw new IllegalArgumentException("L'intervallo di temperatura richiesto è obbligatorio.");
        }

        return new CargoItem(description, category, weight, dimension, requiredTemperatureRange, notes);
    }

    public String getDescription() {
        return description;
    }

    public CargoCategory getCategory() {
        return category;
    }

    public Weight getWeight() {
        return weight;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public TemperatureRange getRequiredTemperatureRange() {
        return requiredTemperatureRange;
    }

    public Notes getNotes() {
        return notes;
    }

    public Volume calculateVolume() {
        return dimension.calculateVolume();
    }

    public boolean requiresTemperatureControl() {
        return requiredTemperatureRange != null || category.requiresTemperatureControl();
    }

    public boolean hasNotes() {
        return notes.hasText();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CargoItem cargoItem)) return false;
        return description.equals(cargoItem.description)
                && category == cargoItem.category
                && weight.equals(cargoItem.weight)
                && dimension.equals(cargoItem.dimension)
                && Objects.equals(requiredTemperatureRange, cargoItem.requiredTemperatureRange)
                && notes.equals(cargoItem.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, category, weight, dimension, requiredTemperatureRange, notes);
    }

    @Override
    public String toString() {
        return description + " (" + category + ")";
    }
}
