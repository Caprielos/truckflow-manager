package it.gabriele.truckflow.domain.maintenance;

/**
 * Regole di dominio per la manutenzione.
 */
public final class MaintenanceRules {

    private MaintenanceRules() {
    }

    public static boolean canBeScheduled(MaintenanceWorkOrder workOrder) {
        validateWorkOrder(workOrder);

        return workOrder.getStatus() == MaintenanceStatus.OPEN;
    }

    public static boolean canBeStarted(MaintenanceWorkOrder workOrder) {
        validateWorkOrder(workOrder);

        return workOrder.getStatus() == MaintenanceStatus.SCHEDULED;
    }

    public static boolean canBeCompleted(MaintenanceWorkOrder workOrder) {
        validateWorkOrder(workOrder);

        return workOrder.getStatus() == MaintenanceStatus.IN_PROGRESS;
    }

    public static boolean canBeCancelled(MaintenanceWorkOrder workOrder) {
        validateWorkOrder(workOrder);

        return !workOrder.getStatus().isTerminal();
    }

    public static boolean blocksVehicleAvailability(MaintenanceWorkOrder workOrder) {
        validateWorkOrder(workOrder);

        return workOrder.blocksVehicleAvailability();
    }

    public static boolean shouldMakeVehicleUnavailable(MaintenanceWorkOrder workOrder) {
        validateWorkOrder(workOrder);

        return workOrder.blocksVehicleAvailability()
                || workOrder.getStatus() == MaintenanceStatus.OPEN && workOrder.isSafetyCritical();
    }

    public static boolean requiresAdrSpecialist(MaintenanceWorkOrder workOrder) {
        validateWorkOrder(workOrder);

        return workOrder.isAdrRelated();
    }

    public static boolean requiresTireService(MaintenanceWorkOrder workOrder) {
        validateWorkOrder(workOrder);

        return workOrder.isTireRelated();
    }

    public static boolean isTerminal(MaintenanceWorkOrder workOrder) {
        validateWorkOrder(workOrder);

        return workOrder.isTerminal();
    }

    private static void validateWorkOrder(MaintenanceWorkOrder workOrder) {
        if (workOrder == null) {
            throw new IllegalArgumentException("L'ordine di manutenzione è obbligatorio.");
        }
    }
}
