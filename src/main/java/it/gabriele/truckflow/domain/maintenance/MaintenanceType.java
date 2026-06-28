package it.gabriele.truckflow.domain.maintenance;

/**
 * Tipo di manutenzione su mezzo o rimorchio.
 */
public enum MaintenanceType {

    ROUTINE_SERVICE(true, false, false, false),
    SAFETY_INSPECTION(true, true, false, false),
    TIRE_REPLACEMENT(true, true, true, false),
    REPAIR(false, true, false, false),
    REFRIGERATION_UNIT_SERVICE(true, true, false, false),
    ADR_TANK_INSPECTION(true, true, false, true),
    BREAKDOWN(false, true, false, false);

    private final boolean plannedMaintenance;
    private final boolean safetyCritical;
    private final boolean tireRelated;
    private final boolean adrRelated;

    MaintenanceType(
            boolean plannedMaintenance,
            boolean safetyCritical,
            boolean tireRelated,
            boolean adrRelated
    ) {
        this.plannedMaintenance = plannedMaintenance;
        this.safetyCritical = safetyCritical;
        this.tireRelated = tireRelated;
        this.adrRelated = adrRelated;
    }

    public boolean isPlannedMaintenance() {
        return plannedMaintenance;
    }

    public boolean isSafetyCritical() {
        return safetyCritical;
    }

    public boolean isTireRelated() {
        return tireRelated;
    }

    public boolean isAdrRelated() {
        return adrRelated;
    }
}
