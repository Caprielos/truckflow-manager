package it.gabriele.truckflow.domain.tracking;

/**
 * Tipo di evento registrato durante il tracking operativo.
 */
public enum TrackingEventType {

    POSITION_RECORDED(false, false, true),
    DEPARTED(true, false, false),
    ARRIVED(true, false, false),
    PICKUP_COMPLETED(true, false, false),
    DELIVERY_COMPLETED(true, false, false),
    DELAY_REPORTED(false, true, false),
    INCIDENT_REPORTED(false, true, false),
    MISSION_COMPLETED(true, false, false),
    CAN_BUS_SNAPSHOT(false, false, false),
    HARSH_BRAKING(false, true, true),
    SPEEDING(false, true, true),
    FUEL_LEVEL_RECORDED(false, false, true);

    private final boolean operationalMilestone;
    private final boolean exceptionEvent;
    private final boolean requiresCoordinates;

    TrackingEventType(
            boolean operationalMilestone,
            boolean exceptionEvent,
            boolean requiresCoordinates
    ) {
        this.operationalMilestone = operationalMilestone;
        this.exceptionEvent = exceptionEvent;
        this.requiresCoordinates = requiresCoordinates;
    }

    public boolean isOperationalMilestone() {
        return operationalMilestone;
    }

    public boolean isExceptionEvent() {
        return exceptionEvent;
    }

    public boolean requiresCoordinates() {
        return requiresCoordinates;
    }
}
