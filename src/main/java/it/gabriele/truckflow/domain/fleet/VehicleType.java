package it.gabriele.truckflow.domain.fleet;

/**
 * Rappresenta il tipo di veicolo presente nella flotta.
 */
public enum VehicleType {

    VAN(true, false, true),
    RIGID_TRUCK(true, false, true),
    REFRIGERATED_TRUCK(true, true, true),
    TRACTOR_UNIT(false, false, true),
    SEMI_TRAILER(true, false, false),
    REFRIGERATED_TRAILER(true, true, false);

    private final boolean cargoCapable;
    private final boolean temperatureControlCapable;
    private final boolean poweredUnit;

    VehicleType(boolean cargoCapable, boolean temperatureControlCapable, boolean poweredUnit) {
        this.cargoCapable = cargoCapable;
        this.temperatureControlCapable = temperatureControlCapable;
        this.poweredUnit = poweredUnit;
    }

    public boolean canCarryCargo() {
        return cargoCapable;
    }

    public boolean supportsTemperatureControl() {
        return temperatureControlCapable;
    }

    public boolean isPoweredUnit() {
        return poweredUnit;
    }

    public boolean isTrailer() {
        return !poweredUnit;
    }
}
