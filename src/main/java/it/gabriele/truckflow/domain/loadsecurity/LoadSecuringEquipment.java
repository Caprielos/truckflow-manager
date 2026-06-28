package it.gabriele.truckflow.domain.loadsecurity;

import java.util.Objects;

public final class LoadSecuringEquipment {

    private final LoadSecuringEquipmentType type;
    private final int quantity;
    private final double capacityDan;

    private LoadSecuringEquipment(LoadSecuringEquipmentType type, int quantity, double capacityDan) {
        if (type == null) {
            throw new IllegalArgumentException("Il tipo dispositivo fissaggio è obbligatorio.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("La quantità non può essere negativa.");
        }
        if (capacityDan < 0 || Double.isNaN(capacityDan) || Double.isInfinite(capacityDan)) {
            throw new IllegalArgumentException("La portata in daN deve essere valida e non negativa.");
        }
        this.type = type;
        this.quantity = quantity;
        this.capacityDan = capacityDan;
    }

    public static LoadSecuringEquipment of(LoadSecuringEquipmentType type, int quantity, double capacityDan) {
        return new LoadSecuringEquipment(type, quantity, capacityDan);
    }

    public LoadSecuringEquipmentType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getCapacityDan() {
        return capacityDan;
    }

    public double totalCapacityDan() {
        return quantity * capacityDan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoadSecuringEquipment that)) return false;
        return quantity == that.quantity
                && Double.compare(capacityDan, that.capacityDan) == 0
                && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, quantity, capacityDan);
    }
}
