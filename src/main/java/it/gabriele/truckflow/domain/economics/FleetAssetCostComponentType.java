package it.gabriele.truckflow.domain.economics;

/**
 * Componenti acquistabili che compongono il costo reale di un bene flotta.
 * Il tipo OTHER permette di tracciare anche costi non previsti senza perdere il prezzo.
 */
public enum FleetAssetCostComponentType {
    VAN,
    RIGID_TRUCK,
    TRACTOR_UNIT,
    DRAWBAR_TRAILER,
    CENTER_AXLE_TRAILER,
    SEMI_TRAILER,
    CHASSIS,
    ENGINE_OR_POWERTRAIN,
    BODY_CONFIGURATION,
    CURTAINSIDER_BODY,
    BOX_BODY,
    REFRIGERATED_BODY,
    TIPPER_BODY,
    TANK_BODY,
    CONTAINER_CARRIER_BODY,
    REFRIGERATION_UNIT,
    HYDRAULIC_TAIL_LIFT,
    TRUCK_MOUNTED_CRANE,
    LOADING_RAMP,
    FORKLIFT,
    PALLET_JACK,
    LOAD_SECURING_EQUIPMENT,
    ADR_KIT,
    ATP_EQUIPMENT,
    TIRE_SET,
    SINGLE_TIRE,
    SPARE_WHEEL,
    TELEMATICS_DEVICE,
    GPS_TRACKER,
    TACHOGRAPH,
    DASHCAM,
    CAMERA_SYSTEM,
    FUEL_CARD_DEVICE,
    WORKSHOP_TOOLING,
    WAREHOUSE_EQUIPMENT,
    SOFTWARE_LICENSE,
    EXTENDED_WARRANTY,
    REGISTRATION,
    ROAD_TAX,
    DEALER_DELIVERY,
    INITIAL_INSURANCE,
    FINANCING_FEE,
    CUSTOMS_OR_IMPORT_DUTY,
    OTHER;

    public boolean isVehicleUnit() {
        return switch (this) {
            case VAN, RIGID_TRUCK, TRACTOR_UNIT, DRAWBAR_TRAILER, CENTER_AXLE_TRAILER, SEMI_TRAILER, CHASSIS -> true;
            default -> false;
        };
    }

    public boolean isBodyOrEquipment() {
        return switch (this) {
            case BODY_CONFIGURATION, CURTAINSIDER_BODY, BOX_BODY, REFRIGERATED_BODY, TIPPER_BODY,
                 TANK_BODY, CONTAINER_CARRIER_BODY, REFRIGERATION_UNIT, HYDRAULIC_TAIL_LIFT,
                 TRUCK_MOUNTED_CRANE, LOADING_RAMP, ADR_KIT, ATP_EQUIPMENT, LOAD_SECURING_EQUIPMENT -> true;
            default -> false;
        };
    }

    public boolean isTireRelated() {
        return this == TIRE_SET || this == SINGLE_TIRE || this == SPARE_WHEEL;
    }

    public boolean isAdministrativePurchaseCost() {
        return switch (this) {
            case REGISTRATION, ROAD_TAX, DEALER_DELIVERY, INITIAL_INSURANCE, FINANCING_FEE,
                 CUSTOMS_OR_IMPORT_DUTY, EXTENDED_WARRANTY -> true;
            default -> false;
        };
    }
}
