package it.gabriele.truckflow.domain.shipment;

/**
 * Rappresenta lo stato operativo di una spedizione.
 */
public enum ShipmentStatus {

    CREATED(false),
    PLANNED(false),
    DISPATCHED(false),
    IN_TRANSIT(false),
    DELIVERED(true),
    CANCELLED(true);

    private final boolean terminal;

    ShipmentStatus(boolean terminal) {
        this.terminal = terminal;
    }

    public boolean isTerminal() {
        return terminal;
    }
}
