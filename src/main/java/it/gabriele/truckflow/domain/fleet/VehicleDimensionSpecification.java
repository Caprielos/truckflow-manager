package it.gabriele.truckflow.domain.fleet;

import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Volume;

import java.util.Objects;

/**
 * Dimensioni esterne e interne del mezzo/allestimento.
 */
public final class VehicleDimensionSpecification {

    private final Dimension externalDimension;
    private final Dimension cargoSpaceDimension;
    private final Double loadFloorHeightMeters;
    private final Integer epalCapacity;

    private VehicleDimensionSpecification(
            Dimension externalDimension,
            Dimension cargoSpaceDimension,
            Double loadFloorHeightMeters,
            Integer epalCapacity
    ) {
        if (externalDimension == null) {
            throw new IllegalArgumentException("Le dimensioni esterne sono obbligatorie.");
        }
        if (loadFloorHeightMeters != null && loadFloorHeightMeters < 0) {
            throw new IllegalArgumentException("L'altezza piano di carico non può essere negativa.");
        }
        if (epalCapacity != null && epalCapacity < 0) {
            throw new IllegalArgumentException("La capacità pallet non può essere negativa.");
        }
        this.externalDimension = externalDimension;
        this.cargoSpaceDimension = cargoSpaceDimension;
        this.loadFloorHeightMeters = loadFloorHeightMeters;
        this.epalCapacity = epalCapacity;
    }

    public static VehicleDimensionSpecification of(
            Dimension externalDimension,
            Dimension cargoSpaceDimension,
            Double loadFloorHeightMeters,
            Integer epalCapacity
    ) {
        return new VehicleDimensionSpecification(externalDimension, cargoSpaceDimension, loadFloorHeightMeters, epalCapacity);
    }

    public Dimension getExternalDimension() {
        return externalDimension;
    }

    public Dimension getCargoSpaceDimension() {
        return cargoSpaceDimension;
    }

    public Double getLoadFloorHeightMeters() {
        return loadFloorHeightMeters;
    }

    public Integer getEpalCapacity() {
        return epalCapacity;
    }

    public boolean hasCargoSpace() {
        return cargoSpaceDimension != null;
    }

    public Volume calculateCargoVolume() {
        if (cargoSpaceDimension == null) {
            throw new IllegalStateException("Il mezzo non ha un vano di carico interno.");
        }
        return cargoSpaceDimension.calculateVolume();
    }

    public int estimateEpalCapacity() {
        if (epalCapacity != null) {
            return epalCapacity;
        }
        if (cargoSpaceDimension == null) {
            return 0;
        }
        return (int) Math.floor(cargoSpaceDimension.getLengthMeters() / 1.2)
                * (int) Math.floor(cargoSpaceDimension.getWidthMeters() / 0.8);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VehicleDimensionSpecification that)) return false;
        return externalDimension.equals(that.externalDimension)
                && Objects.equals(cargoSpaceDimension, that.cargoSpaceDimension)
                && Objects.equals(loadFloorHeightMeters, that.loadFloorHeightMeters)
                && Objects.equals(epalCapacity, that.epalCapacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(externalDimension, cargoSpaceDimension, loadFloorHeightMeters, epalCapacity);
    }
}
