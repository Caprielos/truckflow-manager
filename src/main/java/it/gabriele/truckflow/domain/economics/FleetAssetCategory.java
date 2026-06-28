package it.gabriele.truckflow.domain.economics;

/**
 * Categoria di un bene acquistato dall'azienda e tracciato economicamente.
 */
public enum FleetAssetCategory {
    VAN,
    RIGID_TRUCK,
    TRACTOR_UNIT,
    DRAWBAR_TRAILER,
    CENTER_AXLE_TRAILER,
    SEMI_TRAILER,
    BODY_EQUIPMENT,
    REFRIGERATION_UNIT,
    LOADING_EQUIPMENT,
    TIRE_SET,
    SINGLE_TIRE,
    TELEMATICS_DEVICE,
    WORKSHOP_EQUIPMENT,
    OTHER;

    public boolean isVehicleUnit() {
        return switch (this) {
            case VAN, RIGID_TRUCK, TRACTOR_UNIT, DRAWBAR_TRAILER, CENTER_AXLE_TRAILER, SEMI_TRAILER -> true;
            default -> false;
        };
    }

    public boolean isEquipment() {
        return switch (this) {
            case BODY_EQUIPMENT, REFRIGERATION_UNIT, LOADING_EQUIPMENT, TELEMATICS_DEVICE, WORKSHOP_EQUIPMENT -> true;
            default -> false;
        };
    }
}
