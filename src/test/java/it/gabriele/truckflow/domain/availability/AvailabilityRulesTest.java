package it.gabriele.truckflow.domain.availability;

import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TimeWindow;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa AvailabilityRules.
 */
class AvailabilityRulesTest {

    @Test
    void shouldConfirmResourceAvailableWhenAvailableAndNotBlocked() {
        List<ResourceAvailability> records = List.of(driverAvailable());

        assertTrue(AvailabilityRules.isResourceAvailableForWindow(
                records,
                AvailabilityResourceType.DRIVER,
                "DRV-001",
                dateRange(),
                TimeWindow.of("09:00", "12:00")
        ));
    }

    @Test
    void shouldRejectResourceWhenNoAvailableSlotExists() {
        List<ResourceAvailability> records = List.of();

        assertFalse(AvailabilityRules.isResourceAvailableForWindow(
                records,
                AvailabilityResourceType.DRIVER,
                "DRV-001",
                dateRange(),
                TimeWindow.of("09:00", "12:00")
        ));
    }

    @Test
    void shouldRejectResourceWhenBlockingRecordOverlaps() {
        List<ResourceAvailability> records = List.of(
                driverAvailable(),
                ResourceAvailability.assigned(
                        AvailabilityResourceType.DRIVER,
                        "DRV-001",
                        dateRange(),
                        TimeWindow.of("10:00", "14:00"),
                        Notes.empty()
                )
        );

        assertFalse(AvailabilityRules.isResourceAvailableForWindow(
                records,
                AvailabilityResourceType.DRIVER,
                "DRV-001",
                dateRange(),
                TimeWindow.of("09:00", "12:00")
        ));

        assertTrue(AvailabilityRules.hasBlockingRecordForWindow(
                records,
                AvailabilityResourceType.DRIVER,
                "DRV-001",
                dateRange(),
                TimeWindow.of("09:00", "12:00")
        ));
    }

    @Test
    void shouldIgnoreDifferentResources() {
        List<ResourceAvailability> records = List.of(
                driverAvailable(),
                ResourceAvailability.assigned(
                        AvailabilityResourceType.DRIVER,
                        "DRV-002",
                        dateRange(),
                        TimeWindow.of("10:00", "14:00"),
                        Notes.empty()
                )
        );

        assertTrue(AvailabilityRules.isResourceAvailableForWindow(
                records,
                AvailabilityResourceType.DRIVER,
                "DRV-001",
                dateRange(),
                TimeWindow.of("09:00", "12:00")
        ));
    }

    @Test
    void shouldCheckIfNewAvailabilityRecordCanBeAdded() {
        ResourceAvailability available = driverAvailable();

        ResourceAvailability anotherAvailable = ResourceAvailability.available(
                AvailabilityResourceType.DRIVER,
                "DRV-001",
                dateRange(),
                TimeWindow.of("12:00", "16:00"),
                Notes.empty()
        );

        ResourceAvailability blocking = ResourceAvailability.assigned(
                AvailabilityResourceType.DRIVER,
                "DRV-001",
                dateRange(),
                TimeWindow.of("12:00", "16:00"),
                Notes.empty()
        );

        assertTrue(AvailabilityRules.canAddAvailabilityRecord(
                List.of(available),
                anotherAvailable
        ));

        assertFalse(AvailabilityRules.canAddAvailabilityRecord(
                List.of(available),
                blocking
        ));
    }

    @Test
    void shouldFindRecordsForResource() {
        List<ResourceAvailability> records = List.of(
                driverAvailable(),
                ResourceAvailability.available(
                        AvailabilityResourceType.DRIVER,
                        "DRV-002",
                        dateRange(),
                        timeWindow(),
                        Notes.empty()
                ),
                ResourceAvailability.available(
                        AvailabilityResourceType.VEHICLE_COMBINATION,
                        "COMBO-001",
                        dateRange(),
                        timeWindow(),
                        Notes.empty()
                )
        );

        List<ResourceAvailability> driverRecords = AvailabilityRules.findRecordsForResource(
                records,
                AvailabilityResourceType.DRIVER,
                "drv-001"
        );

        assertEquals(1, driverRecords.size());
        assertEquals("DRV-001", driverRecords.getFirst().getResourceCode());
    }

    @Test
    void shouldNotAllowNullValues() {
        List<ResourceAvailability> records = List.of(driverAvailable());

        assertThrows(IllegalArgumentException.class, () -> AvailabilityRules.isResourceAvailableForWindow(
                null,
                AvailabilityResourceType.DRIVER,
                "DRV-001",
                dateRange(),
                timeWindow()
        ));

        assertThrows(IllegalArgumentException.class, () -> AvailabilityRules.isResourceAvailableForWindow(
                records,
                null,
                "DRV-001",
                dateRange(),
                timeWindow()
        ));

        assertThrows(IllegalArgumentException.class, () -> AvailabilityRules.isResourceAvailableForWindow(
                records,
                AvailabilityResourceType.DRIVER,
                null,
                dateRange(),
                timeWindow()
        ));

        assertThrows(IllegalArgumentException.class, () -> AvailabilityRules.isResourceAvailableForWindow(
                records,
                AvailabilityResourceType.DRIVER,
                "DRV-001",
                null,
                timeWindow()
        ));

        assertThrows(IllegalArgumentException.class, () -> AvailabilityRules.isResourceAvailableForWindow(
                records,
                AvailabilityResourceType.DRIVER,
                "DRV-001",
                dateRange(),
                null
        ));

        assertThrows(IllegalArgumentException.class, () -> AvailabilityRules.canAddAvailabilityRecord(
                records,
                null
        ));
    }

    @Test
    void shouldNotAllowNullRecordsInsideList() {
        List<ResourceAvailability> recordsWithNull = Arrays.asList(driverAvailable(), null);

        assertThrows(IllegalArgumentException.class, () -> AvailabilityRules.findRecordsForResource(
                recordsWithNull,
                AvailabilityResourceType.DRIVER,
                "DRV-001"
        ));
    }

    private static ResourceAvailability driverAvailable() {
        return ResourceAvailability.available(
                AvailabilityResourceType.DRIVER,
                "DRV-001",
                dateRange(),
                timeWindow(),
                Notes.empty()
        );
    }

    private static DateRange dateRange() {
        return DateRange.of(
                LocalDate.of(2026, 7, 1),
                LocalDate.of(2026, 7, 1)
        );
    }

    private static TimeWindow timeWindow() {
        return TimeWindow.of("08:00", "18:00");
    }
}
