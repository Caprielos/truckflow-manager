package it.gabriele.truckflow.domain.maintenance;

import it.gabriele.truckflow.domain.fleet.TireSpecification;
import it.gabriele.truckflow.domain.fleet.Vehicle;
import it.gabriele.truckflow.domain.fleet.VehicleBodyType;
import it.gabriele.truckflow.domain.fleet.VehicleStatus;
import it.gabriele.truckflow.domain.fleet.VehicleType;
import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.Weight;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa MaintenanceRules.
 */
class MaintenanceRulesTest {

    @Test
    void shouldCheckLifecycleRules() {
        MaintenanceWorkOrder open = openWorkOrder();
        MaintenanceWorkOrder scheduled = open.schedule(dateRange());
        MaintenanceWorkOrder inProgress = scheduled.start();
        MaintenanceWorkOrder completed = inProgress.complete();

        assertTrue(MaintenanceRules.canBeScheduled(open));
        assertFalse(MaintenanceRules.canBeStarted(open));
        assertFalse(MaintenanceRules.canBeCompleted(open));

        assertFalse(MaintenanceRules.canBeScheduled(scheduled));
        assertTrue(MaintenanceRules.canBeStarted(scheduled));
        assertFalse(MaintenanceRules.canBeCompleted(scheduled));

        assertFalse(MaintenanceRules.canBeScheduled(inProgress));
        assertFalse(MaintenanceRules.canBeStarted(inProgress));
        assertTrue(MaintenanceRules.canBeCompleted(inProgress));

        assertTrue(MaintenanceRules.isTerminal(completed));
    }

    @Test
    void shouldCheckCancellationRules() {
        MaintenanceWorkOrder open = openWorkOrder();
        MaintenanceWorkOrder completed = open.schedule(dateRange()).start().complete();
        MaintenanceWorkOrder cancelled = open.cancel();

        assertTrue(MaintenanceRules.canBeCancelled(open));
        assertFalse(MaintenanceRules.canBeCancelled(completed));
        assertFalse(MaintenanceRules.canBeCancelled(cancelled));
    }

    @Test
    void shouldCheckVehicleAvailabilityBlocking() {
        MaintenanceWorkOrder openRoutine = openWorkOrder();

        MaintenanceWorkOrder scheduledRoutine = openRoutine.schedule(dateRange());

        MaintenanceWorkOrder openRepair = MaintenanceWorkOrder.open(
                "MNT-002",
                standardVehicle(),
                MaintenanceType.REPAIR,
                Notes.empty()
        );

        assertFalse(MaintenanceRules.blocksVehicleAvailability(openRoutine));
        assertTrue(MaintenanceRules.blocksVehicleAvailability(scheduledRoutine));

        assertFalse(MaintenanceRules.shouldMakeVehicleUnavailable(openRoutine));
        assertTrue(MaintenanceRules.shouldMakeVehicleUnavailable(scheduledRoutine));
        assertTrue(MaintenanceRules.shouldMakeVehicleUnavailable(openRepair));
    }

    @Test
    void shouldCheckSpecialMaintenanceRequirements() {
        MaintenanceWorkOrder tireWorkOrder = MaintenanceWorkOrder.open(
                "MNT-002",
                standardVehicle(),
                MaintenanceType.TIRE_REPLACEMENT,
                Notes.empty()
        );

        MaintenanceWorkOrder adrWorkOrder = MaintenanceWorkOrder.open(
                "MNT-003",
                standardVehicle(),
                MaintenanceType.ADR_TANK_INSPECTION,
                Notes.empty()
        );

        assertTrue(MaintenanceRules.requiresTireService(tireWorkOrder));
        assertFalse(MaintenanceRules.requiresAdrSpecialist(tireWorkOrder));

        assertTrue(MaintenanceRules.requiresAdrSpecialist(adrWorkOrder));
        assertFalse(MaintenanceRules.requiresTireService(adrWorkOrder));
    }

    @Test
    void shouldNotAllowNullWorkOrder() {
        assertThrows(IllegalArgumentException.class, () -> MaintenanceRules.canBeScheduled(null));
        assertThrows(IllegalArgumentException.class, () -> MaintenanceRules.canBeStarted(null));
        assertThrows(IllegalArgumentException.class, () -> MaintenanceRules.canBeCompleted(null));
        assertThrows(IllegalArgumentException.class, () -> MaintenanceRules.canBeCancelled(null));
        assertThrows(IllegalArgumentException.class, () -> MaintenanceRules.blocksVehicleAvailability(null));
        assertThrows(IllegalArgumentException.class, () -> MaintenanceRules.shouldMakeVehicleUnavailable(null));
        assertThrows(IllegalArgumentException.class, () -> MaintenanceRules.requiresAdrSpecialist(null));
        assertThrows(IllegalArgumentException.class, () -> MaintenanceRules.requiresTireService(null));
        assertThrows(IllegalArgumentException.class, () -> MaintenanceRules.isTerminal(null));
    }

    @Test
    void shouldExposeEnumDetails() {
        assertTrue(MaintenanceType.ROUTINE_SERVICE.isPlannedMaintenance());
        assertFalse(MaintenanceType.BREAKDOWN.isPlannedMaintenance());
        assertTrue(MaintenanceType.TIRE_REPLACEMENT.isTireRelated());
        assertTrue(MaintenanceType.ADR_TANK_INSPECTION.isAdrRelated());

        assertFalse(MaintenanceStatus.OPEN.isTerminal());
        assertFalse(MaintenanceStatus.OPEN.blocksVehicleAvailability());
        assertTrue(MaintenanceStatus.SCHEDULED.blocksVehicleAvailability());
        assertTrue(MaintenanceStatus.COMPLETED.isTerminal());
    }

    private static MaintenanceWorkOrder openWorkOrder() {
        return MaintenanceWorkOrder.open(
                "MNT-001",
                standardVehicle(),
                MaintenanceType.ROUTINE_SERVICE,
                Notes.empty()
        );
    }

    private static Vehicle standardVehicle() {
        return Vehicle.cargoVehicle(
                "TRUCK-001",
                "AB 123 CD",
                "1HGCM82633A004352",
                VehicleType.RIGID_TRUCK,
                VehicleBodyType.BOX,
                VehicleStatus.AVAILABLE,
                standardTire(),
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                Notes.empty()
        );
    }

    private static TireSpecification standardTire() {
        return TireSpecification.of(
                "Michelin",
                "X Multi",
                "315/70 R22.5",
                154,
                "L"
        );
    }

    private static DateRange dateRange() {
        return DateRange.of(
                LocalDate.of(2026, 7, 1),
                LocalDate.of(2026, 7, 2)
        );
    }
}
