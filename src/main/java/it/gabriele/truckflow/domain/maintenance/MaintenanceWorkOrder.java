package it.gabriele.truckflow.domain.maintenance;

import it.gabriele.truckflow.domain.fleet.Vehicle;
import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.Notes;

import java.util.Objects;

/**
 * Rappresenta un ordine di manutenzione per un mezzo o rimorchio.
 */
public final class MaintenanceWorkOrder {

    private static final int MAX_WORK_ORDER_NUMBER_LENGTH = 50;

    private final String workOrderNumber;
    private final Vehicle vehicle;
    private final MaintenanceType type;
    private final MaintenanceStatus status;
    private final DateRange plannedDateRange;
    private final Notes notes;

    private MaintenanceWorkOrder(
            String workOrderNumber,
            Vehicle vehicle,
            MaintenanceType type,
            MaintenanceStatus status,
            DateRange plannedDateRange,
            Notes notes
    ) {
        this.workOrderNumber = validateWorkOrderNumber(workOrderNumber);

        if (vehicle == null) {
            throw new IllegalArgumentException("Il veicolo della manutenzione è obbligatorio.");
        }

        if (type == null) {
            throw new IllegalArgumentException("Il tipo manutenzione è obbligatorio.");
        }

        if (status == null) {
            throw new IllegalArgumentException("Lo stato manutenzione è obbligatorio.");
        }

        if (notes == null) {
            throw new IllegalArgumentException("Le note manutenzione sono obbligatorie.");
        }

        if (status.blocksVehicleAvailability() && plannedDateRange == null) {
            throw new IllegalArgumentException("Una manutenzione programmata o in corso deve avere un intervallo date.");
        }

        this.vehicle = vehicle;
        this.type = type;
        this.status = status;
        this.plannedDateRange = plannedDateRange;
        this.notes = notes;
    }

    public static MaintenanceWorkOrder open(
            String workOrderNumber,
            Vehicle vehicle,
            MaintenanceType type,
            Notes notes
    ) {
        return new MaintenanceWorkOrder(
                workOrderNumber,
                vehicle,
                type,
                MaintenanceStatus.OPEN,
                null,
                notes
        );
    }

    public static MaintenanceWorkOrder scheduled(
            String workOrderNumber,
            Vehicle vehicle,
            MaintenanceType type,
            DateRange plannedDateRange,
            Notes notes
    ) {
        return new MaintenanceWorkOrder(
                workOrderNumber,
                vehicle,
                type,
                MaintenanceStatus.SCHEDULED,
                plannedDateRange,
                notes
        );
    }

    private static String validateWorkOrderNumber(String workOrderNumber) {
        if (workOrderNumber == null) {
            throw new IllegalArgumentException("Il numero ordine manutenzione è obbligatorio.");
        }

        String normalizedWorkOrderNumber = workOrderNumber.trim().toUpperCase();

        if (normalizedWorkOrderNumber.isEmpty()) {
            throw new IllegalArgumentException("Il numero ordine manutenzione non può essere vuoto.");
        }

        if (normalizedWorkOrderNumber.length() > MAX_WORK_ORDER_NUMBER_LENGTH) {
            throw new IllegalArgumentException("Il numero ordine manutenzione non può superare "
                    + MAX_WORK_ORDER_NUMBER_LENGTH + " caratteri.");
        }

        if (!normalizedWorkOrderNumber.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il numero ordine manutenzione può contenere solo lettere, numeri, trattini e underscore.");
        }

        return normalizedWorkOrderNumber;
    }

    public MaintenanceWorkOrder schedule(DateRange plannedDateRange) {
        if (!MaintenanceRules.canBeScheduled(this)) {
            throw new IllegalStateException("La manutenzione non può essere programmata.");
        }

        return new MaintenanceWorkOrder(
                workOrderNumber,
                vehicle,
                type,
                MaintenanceStatus.SCHEDULED,
                plannedDateRange,
                notes
        );
    }

    public MaintenanceWorkOrder start() {
        if (!MaintenanceRules.canBeStarted(this)) {
            throw new IllegalStateException("La manutenzione non può essere avviata.");
        }

        return new MaintenanceWorkOrder(
                workOrderNumber,
                vehicle,
                type,
                MaintenanceStatus.IN_PROGRESS,
                plannedDateRange,
                notes
        );
    }

    public MaintenanceWorkOrder complete() {
        if (!MaintenanceRules.canBeCompleted(this)) {
            throw new IllegalStateException("La manutenzione non può essere completata.");
        }

        return new MaintenanceWorkOrder(
                workOrderNumber,
                vehicle,
                type,
                MaintenanceStatus.COMPLETED,
                plannedDateRange,
                notes
        );
    }

    public MaintenanceWorkOrder cancel() {
        if (!MaintenanceRules.canBeCancelled(this)) {
            throw new IllegalStateException("La manutenzione non può essere cancellata.");
        }

        return new MaintenanceWorkOrder(
                workOrderNumber,
                vehicle,
                type,
                MaintenanceStatus.CANCELLED,
                plannedDateRange,
                notes
        );
    }

    public String getWorkOrderNumber() {
        return workOrderNumber;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public MaintenanceType getType() {
        return type;
    }

    public MaintenanceStatus getStatus() {
        return status;
    }

    public DateRange getPlannedDateRange() {
        return plannedDateRange;
    }

    public Notes getNotes() {
        return notes;
    }

    public String getVehicleFleetNumber() {
        return vehicle.getFleetNumber();
    }

    public String getVehicleLicensePlate() {
        return vehicle.getLicensePlate();
    }

    public boolean isOpen() {
        return status == MaintenanceStatus.OPEN;
    }

    public boolean isScheduled() {
        return status == MaintenanceStatus.SCHEDULED;
    }

    public boolean isInProgress() {
        return status == MaintenanceStatus.IN_PROGRESS;
    }

    public boolean isCompleted() {
        return status == MaintenanceStatus.COMPLETED;
    }

    public boolean isCancelled() {
        return status == MaintenanceStatus.CANCELLED;
    }

    public boolean isTerminal() {
        return status.isTerminal();
    }

    public boolean blocksVehicleAvailability() {
        return status.blocksVehicleAvailability();
    }

    public boolean isSafetyCritical() {
        return type.isSafetyCritical();
    }

    public boolean isTireRelated() {
        return type.isTireRelated();
    }

    public boolean isAdrRelated() {
        return type.isAdrRelated();
    }

    public boolean isForVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Il veicolo da confrontare è obbligatorio.");
        }

        return this.vehicle.equals(vehicle);
    }

    public boolean hasNotes() {
        return notes.hasText();
    }

    public String formatSingleLine() {
        return workOrderNumber
                + " - " + vehicle.getFleetNumber()
                + " - " + type
                + " - " + status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaintenanceWorkOrder that)) return false;
        return workOrderNumber.equals(that.workOrderNumber)
                && vehicle.equals(that.vehicle)
                && type == that.type
                && status == that.status
                && Objects.equals(plannedDateRange, that.plannedDateRange)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workOrderNumber, vehicle, type, status, plannedDateRange, notes);
    }

    @Override
    public String toString() {
        return formatSingleLine();
    }
}
