package it.gabriele.truckflow.domain.route;

/**
 * Rappresenta il tipo di fermata in una tratta.
 */
public enum RouteStopType {

    START(false),
    PICKUP(true),
    DELIVERY(true),
    REST_BREAK(false),
    FUEL_STOP(false),
    END(false);

    private final boolean cargoOperation;

    RouteStopType(boolean cargoOperation) {
        this.cargoOperation = cargoOperation;
    }

    public boolean isCargoOperation() {
        return cargoOperation;
    }
}
