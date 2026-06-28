package it.gabriele.truckflow.domain.driver;

/**
 * Rappresenta lo stato operativo di un autista.
 */
public enum DriverStatus {

    AVAILABLE(true),
    ASSIGNED(false),
    ON_LEAVE(false),
    SUSPENDED(false),
    INACTIVE(false);

    private final boolean assignable;

    DriverStatus(boolean assignable) {
        this.assignable = assignable;
    }

    public boolean canBeAssigned() {
        return assignable;
    }
}
