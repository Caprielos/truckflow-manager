package it.gabriele.truckflow.domain.fleet;

import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.Weight;

import java.util.Objects;

/**
 * Accessorio di carico/scarico installato su un mezzo.
 */
public final class VehicleLoadingEquipment {

    private final VehicleLoadingEquipmentType type;
    private final VehicleEquipmentPosition position;
    private final Weight capacity;
    private final Notes notes;

    private VehicleLoadingEquipment(
            VehicleLoadingEquipmentType type,
            VehicleEquipmentPosition position,
            Weight capacity,
            Notes notes
    ) {
        if (type == null) {
            throw new IllegalArgumentException("Il tipo accessorio è obbligatorio.");
        }
        if (position == null) {
            throw new IllegalArgumentException("La posizione accessorio è obbligatoria.");
        }
        if (notes == null) {
            throw new IllegalArgumentException("Le note accessorio sono obbligatorie.");
        }
        this.type = type;
        this.position = position;
        this.capacity = capacity;
        this.notes = notes;
    }

    public static VehicleLoadingEquipment of(
            VehicleLoadingEquipmentType type,
            VehicleEquipmentPosition position,
            Weight capacity,
            Notes notes
    ) {
        return new VehicleLoadingEquipment(type, position, capacity, notes);
    }

    public VehicleLoadingEquipmentType getType() {
        return type;
    }

    public VehicleEquipmentPosition getPosition() {
        return position;
    }

    public Weight getCapacity() {
        return capacity;
    }

    public Notes getNotes() {
        return notes;
    }

    public boolean isType(VehicleLoadingEquipmentType expectedType) {
        if (expectedType == null) {
            throw new IllegalArgumentException("Il tipo accessorio da verificare è obbligatorio.");
        }
        return type == expectedType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VehicleLoadingEquipment that)) return false;
        return type == that.type
                && position == that.position
                && Objects.equals(capacity, that.capacity)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, position, capacity, notes);
    }
}
