package it.gabriele.truckflow.domain.cargo;

/**
 * Rappresenta la categoria della merce trasportata.
 */
public enum CargoCategory {

    GENERAL(false),
    FOOD(false),
    REFRIGERATED_FOOD(true),
    PHARMACEUTICAL(true),
    FRAGILE(false),
    HAZARDOUS_MATERIAL(false),
    OVERSIZED(false),
    LIQUID(false),
    ELECTRONICS(false),
    CONSTRUCTION_MATERIAL(false);

    private final boolean requiresTemperatureControl;

    CargoCategory(boolean requiresTemperatureControl) {
        this.requiresTemperatureControl = requiresTemperatureControl;
    }

    public boolean requiresTemperatureControl() {
        return requiresTemperatureControl;
    }
}
