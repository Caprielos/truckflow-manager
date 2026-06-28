package it.gabriele.truckflow.domain.fleet;

/**
 * Tipo operativo del convoglio.
 */
public enum VehicleCombinationType {

    SINGLE_VEHICLE,
    TRUCK_AND_TRAILER,
    ARTICULATED_VEHICLE;

    public boolean hasTowedUnit() {
        return this == TRUCK_AND_TRAILER || this == ARTICULATED_VEHICLE;
    }

    public boolean requiresTrailerLicense() {
        return hasTowedUnit();
    }
}
