package it.gabriele.truckflow.domain.fleet;

/**
 * Allestimento base montato sopra il telaio.
 */
public enum VehicleBodyBaseType {

    NONE(false, false, false, false, false, false),
    FIXED_OPEN_BOX(true, false, false, false, false, true),
    REAR_TIPPER(true, false, false, false, true, true),
    THREE_WAY_TIPPER(true, false, false, false, true, true),
    CURTAIN_SIDE(true, false, false, false, false, false),
    DRY_BOX(true, false, false, false, false, false),
    ISOTHERMAL_BOX(true, true, false, false, false, false),
    REFRIGERATED_BOX(true, true, false, false, false, false),
    TANK(true, false, true, true, false, false),
    SILO(true, false, true, false, true, false),
    FLATBED(true, false, false, false, false, true),
    LOW_LOADER(true, false, false, false, false, true),
    CONTAINER_CHASSIS(true, false, false, false, false, true),
    SWAP_BODY_CARRIER(true, false, false, false, false, true),
    HOOKLIFT_CHASSIS(true, false, false, false, false, true),
    WALKING_FLOOR(true, false, false, false, true, false),
    CAR_TRANSPORTER(true, false, false, false, false, true),
    COIL_CARRIER(true, false, false, false, false, false),
    LIVESTOCK_BODY(true, false, false, false, false, false),
    CONCRETE_MIXER(true, false, false, false, true, false),
    CRANE_PLATFORM(true, false, false, false, false, true);

    private final boolean cargoBody;
    private final boolean temperatureControlled;
    private final boolean tank;
    private final boolean liquidTankCompatible;
    private final boolean bulkBody;
    private final boolean openBody;

    VehicleBodyBaseType(boolean cargoBody, boolean temperatureControlled, boolean tank,
                        boolean liquidTankCompatible, boolean bulkBody, boolean openBody) {
        this.cargoBody = cargoBody;
        this.temperatureControlled = temperatureControlled;
        this.tank = tank;
        this.liquidTankCompatible = liquidTankCompatible;
        this.bulkBody = bulkBody;
        this.openBody = openBody;
    }

    public boolean isCargoBody() {
        return cargoBody;
    }

    public boolean supportsTemperatureControl() {
        return temperatureControlled;
    }

    public boolean isTank() {
        return tank;
    }

    public boolean isLiquidTankCompatible() {
        return liquidTankCompatible;
    }

    public boolean isBulkBody() {
        return bulkBody;
    }

    public boolean isOpenBody() {
        return openBody;
    }
}
