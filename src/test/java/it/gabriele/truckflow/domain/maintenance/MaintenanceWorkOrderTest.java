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
 * Testa MaintenanceWorkOrder.
 */
class MaintenanceWorkOrderTest {

    @Test
    void shouldCreateOpenMaintenanceWorkOrder() {
        MaintenanceWorkOrder workOrder = openWorkOrder();

        assertEquals("MNT-001", workOrder.getWorkOrderNumber());
        assertEquals(standardVehicle(), workOrder.getVehicle());
        assertEquals(MaintenanceType.ROUTINE_SERVICE, workOrder.getType());
        assertEquals(MaintenanceStatus.OPEN, workOrder.getStatus());
        assertNull(workOrder.getPlannedDateRange());
        assertTrue(workOrder.isOpen());
        assertFalse(workOrder.blocksVehicleAvailability());
    }

    @Test
    void shouldCreateScheduledMaintenanceWorkOrder() {
        MaintenanceWorkOrder workOrder = MaintenanceWorkOrder.scheduled(
                "MNT-001",
                standardVehicle(),
                MaintenanceType.SAFETY_INSPECTION,
                dateRange(),
                Notes.empty()
        );

        assertTrue(workOrder.isScheduled());
        assertEquals(dateRange(), workOrder.getPlannedDateRange());
        assertTrue(workOrder.blocksVehicleAvailability());
        assertTrue(workOrder.isSafetyCritical());
    }

    @Test
    void shouldNormalizeWorkOrderNumber() {
        MaintenanceWorkOrder workOrder = MaintenanceWorkOrder.open(
                "  mnt_001  ",
                standardVehicle(),
                MaintenanceType.ROUTINE_SERVICE,
                Notes.empty()
        );

        assertEquals("MNT_001", workOrder.getWorkOrderNumber());
    }

    @Test
    void shouldRejectInvalidWorkOrderNumber() {
        assertThrows(IllegalArgumentException.class, () -> MaintenanceWorkOrder.open(
                null,
                standardVehicle(),
                MaintenanceType.ROUTINE_SERVICE,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> MaintenanceWorkOrder.open(
                "MNT 001",
                standardVehicle(),
                MaintenanceType.ROUTINE_SERVICE,
                Notes.empty()
        ));
    }

    @Test
    void shouldRejectNullMandatoryFields() {
        assertThrows(IllegalArgumentException.class, () -> MaintenanceWorkOrder.open(
                "MNT-001",
                null,
                MaintenanceType.ROUTINE_SERVICE,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> MaintenanceWorkOrder.open(
                "MNT-001",
                standardVehicle(),
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> MaintenanceWorkOrder.open(
                "MNT-001",
                standardVehicle(),
                MaintenanceType.ROUTINE_SERVICE,
                null
        ));

        assertThrows(IllegalArgumentException.class, () -> MaintenanceWorkOrder.scheduled(
                "MNT-001",
                standardVehicle(),
                MaintenanceType.ROUTINE_SERVICE,
                null,
                Notes.empty()
        ));
    }

    @Test
    void shouldMoveThroughLifecycle() {
        MaintenanceWorkOrder open = openWorkOrder();
        MaintenanceWorkOrder scheduled = open.schedule(dateRange());
        MaintenanceWorkOrder inProgress = scheduled.start();
        MaintenanceWorkOrder completed = inProgress.complete();

        assertTrue(scheduled.isScheduled());
        assertTrue(inProgress.isInProgress());
        assertTrue(completed.isCompleted());
        assertTrue(completed.isTerminal());
    }

    @Test
    void shouldNotAllowInvalidLifecycleTransitions() {
        MaintenanceWorkOrder open = openWorkOrder();

        assertThrows(IllegalStateException.class, open::start);
        assertThrows(IllegalStateException.class, open::complete);

        MaintenanceWorkOrder scheduled = open.schedule(dateRange());

        assertThrows(IllegalStateException.class, () -> scheduled.schedule(dateRange()));
        assertThrows(IllegalStateException.class, scheduled::complete);

        MaintenanceWorkOrder completed = scheduled.start().complete();

        assertThrows(IllegalStateException.class, () -> completed.schedule(dateRange()));
        assertThrows(IllegalStateException.class, completed::start);
        assertThrows(IllegalStateException.class, completed::complete);
    }

    @Test
    void shouldCancelNonTerminalWorkOrder() {
        MaintenanceWorkOrder open = openWorkOrder();
        MaintenanceWorkOrder scheduled = open.schedule(dateRange());

        assertTrue(open.cancel().isCancelled());
        assertTrue(scheduled.cancel().isCancelled());
    }

    @Test
    void shouldNotCancelTerminalWorkOrder() {
        MaintenanceWorkOrder completed = openWorkOrder()
                .schedule(dateRange())
                .start()
                .complete();

        MaintenanceWorkOrder cancelled = openWorkOrder().cancel();

        assertThrows(IllegalStateException.class, completed::cancel);
        assertThrows(IllegalStateException.class, cancelled::cancel);
    }

    @Test
    void shouldExposeVehicleData() {
        MaintenanceWorkOrder workOrder = openWorkOrder();

        assertEquals("TRUCK-001", workOrder.getVehicleFleetNumber());
        assertEquals("AB 123 CD", workOrder.getVehicleLicensePlate());
        assertTrue(workOrder.isForVehicle(standardVehicle()));
        assertThrows(IllegalArgumentException.class, () -> workOrder.isForVehicle(null));
    }

    @Test
    void shouldDetectMaintenanceTypeDetails() {
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

        assertTrue(tireWorkOrder.isTireRelated());
        assertTrue(tireWorkOrder.isSafetyCritical());

        assertTrue(adrWorkOrder.isAdrRelated());
        assertTrue(adrWorkOrder.isSafetyCritical());
    }

    @Test
    void shouldDetectNotes() {
        MaintenanceWorkOrder workOrder = MaintenanceWorkOrder.open(
                "MNT-001",
                standardVehicle(),
                MaintenanceType.REPAIR,
                Notes.of("Rumore anomalo al motore")
        );

        assertTrue(workOrder.hasNotes());
    }

    @Test
    void shouldFormatSingleLine() {
        assertEquals(
                "MNT-001 - TRUCK-001 - ROUTINE_SERVICE - OPEN",
                openWorkOrder().formatSingleLine()
        );
    }

    @Test
    void shouldConsiderEquivalentWorkOrdersEqual() {
        MaintenanceWorkOrder first = openWorkOrder();
        MaintenanceWorkOrder second = openWorkOrder();

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
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
