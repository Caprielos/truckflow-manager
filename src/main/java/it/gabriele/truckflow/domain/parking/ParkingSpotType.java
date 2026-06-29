package it.gabriele.truckflow.domain.parking;

/**
 * Tipo fisico di posto parcheggio.
 */
public enum ParkingSpotType {

    VAN_SPACE,
    RIGID_TRUCK_SPACE,
    TRACTOR_UNIT_SPACE,
    TRAILER_SPACE,
    SEMI_TRAILER_SPACE,
    FULL_COMBINATION_SPACE,
    LONG_COMBINATION_SPACE,
    EQUIPMENT_SPACE,
    MAINTENANCE_WAITING_SPACE;

    public boolean canHost(ParkingResourceType resourceType) {
        if (resourceType == null) {
            throw new IllegalArgumentException("Il tipo risorsa parcheggio è obbligatorio.");
        }

        return switch (this) {
            case VAN_SPACE -> resourceType == ParkingResourceType.VAN;
            case RIGID_TRUCK_SPACE -> resourceType == ParkingResourceType.RIGID_TRUCK || resourceType == ParkingResourceType.VAN;
            case TRACTOR_UNIT_SPACE -> resourceType == ParkingResourceType.TRACTOR_UNIT;
            case TRAILER_SPACE -> resourceType == ParkingResourceType.TRAILER || resourceType == ParkingResourceType.SEMI_TRAILER;
            case SEMI_TRAILER_SPACE -> resourceType == ParkingResourceType.SEMI_TRAILER;
            case FULL_COMBINATION_SPACE -> resourceType == ParkingResourceType.ARTICULATED_VEHICLE
                    || resourceType == ParkingResourceType.TRUCK_AND_TRAILER;
            case LONG_COMBINATION_SPACE -> resourceType.isCombination();
            case EQUIPMENT_SPACE -> resourceType == ParkingResourceType.EQUIPMENT || resourceType == ParkingResourceType.OTHER;
            case MAINTENANCE_WAITING_SPACE -> resourceType != ParkingResourceType.EQUIPMENT;
        };
    }
}
