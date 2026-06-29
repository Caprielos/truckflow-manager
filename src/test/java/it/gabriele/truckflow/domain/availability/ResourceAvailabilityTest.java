package it.gabriele.truckflow.domain.availability;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TimeWindow;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/** Testa ResourceAvailability. */
class ResourceAvailabilityTest {

  @Test
  void shouldCreateAvailableResource() {
    ResourceAvailability availability = driverAvailable();

    assertEquals(AvailabilityResourceType.DRIVER, availability.getResourceType());
    assertEquals("DRV-001", availability.getResourceCode());
    assertEquals(dateRange(), availability.getDateRange());
    assertEquals(timeWindow(), availability.getTimeWindow());
    assertEquals(AvailabilityStatus.AVAILABLE, availability.getStatus());
    assertTrue(availability.isAvailable());
    assertTrue(availability.isBookable());
    assertFalse(availability.isBlocking());
  }

  @Test
  void shouldCreateBlockingStatuses() {
    assertTrue(
        ResourceAvailability.reserved(
                AvailabilityResourceType.DRIVER,
                "DRV-001",
                dateRange(),
                timeWindow(),
                Notes.empty())
            .isBlocking());

    assertTrue(
        ResourceAvailability.assigned(
                AvailabilityResourceType.VEHICLE_COMBINATION,
                "COMBO-001",
                dateRange(),
                timeWindow(),
                Notes.empty())
            .isBlocking());

    assertTrue(
        ResourceAvailability.maintenance(
                AvailabilityResourceType.VEHICLE,
                "TRUCK-001",
                dateRange(),
                timeWindow(),
                Notes.empty())
            .isBlocking());

    assertTrue(
        ResourceAvailability.onLeave(
                AvailabilityResourceType.DRIVER,
                "DRV-001",
                dateRange(),
                timeWindow(),
                Notes.empty())
            .isBlocking());
  }

  @Test
  void shouldNormalizeResourceCode() {
    ResourceAvailability availability =
        ResourceAvailability.available(
            AvailabilityResourceType.DRIVER,
            "  drv_001  ",
            dateRange(),
            timeWindow(),
            Notes.empty());

    assertEquals("DRV_001", availability.getResourceCode());
  }

  @Test
  void shouldNotAllowInvalidResourceCode() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            ResourceAvailability.available(
                AvailabilityResourceType.DRIVER, null, dateRange(), timeWindow(), Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            ResourceAvailability.available(
                AvailabilityResourceType.DRIVER,
                "DRV 001",
                dateRange(),
                timeWindow(),
                Notes.empty()));
  }

  @Test
  void shouldNotAllowNullMandatoryFields() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            ResourceAvailability.of(
                null,
                "DRV-001",
                dateRange(),
                timeWindow(),
                AvailabilityStatus.AVAILABLE,
                Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            ResourceAvailability.of(
                AvailabilityResourceType.DRIVER,
                "DRV-001",
                null,
                timeWindow(),
                AvailabilityStatus.AVAILABLE,
                Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            ResourceAvailability.of(
                AvailabilityResourceType.DRIVER,
                "DRV-001",
                dateRange(),
                null,
                AvailabilityStatus.AVAILABLE,
                Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            ResourceAvailability.of(
                AvailabilityResourceType.DRIVER,
                "DRV-001",
                dateRange(),
                timeWindow(),
                null,
                Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            ResourceAvailability.of(
                AvailabilityResourceType.DRIVER,
                "DRV-001",
                dateRange(),
                timeWindow(),
                AvailabilityStatus.AVAILABLE,
                null));
  }

  @Test
  void shouldCheckSameResource() {
    ResourceAvailability first = driverAvailable();
    ResourceAvailability second =
        ResourceAvailability.assigned(
            AvailabilityResourceType.DRIVER, "DRV-001", dateRange(), timeWindow(), Notes.empty());

    ResourceAvailability differentResource =
        ResourceAvailability.available(
            AvailabilityResourceType.DRIVER, "DRV-002", dateRange(), timeWindow(), Notes.empty());

    assertTrue(first.isSameResource(second));
    assertTrue(first.isSameResource(AvailabilityResourceType.DRIVER, "drv-001"));
    assertFalse(first.isSameResource(differentResource));
  }

  @Test
  void shouldCheckOverlaps() {
    ResourceAvailability available = driverAvailable();

    ResourceAvailability overlapping =
        ResourceAvailability.reserved(
            AvailabilityResourceType.DRIVER,
            "DRV-001",
            dateRange(),
            TimeWindow.of("12:00", "16:00"),
            Notes.empty());

    ResourceAvailability differentResource =
        ResourceAvailability.reserved(
            AvailabilityResourceType.DRIVER,
            "DRV-002",
            dateRange(),
            TimeWindow.of("12:00", "16:00"),
            Notes.empty());

    assertTrue(available.overlapsWith(overlapping));
    assertFalse(available.overlapsWith(differentResource));
  }

  @Test
  void shouldDetectNotes() {
    ResourceAvailability availability =
        ResourceAvailability.unavailable(
            AvailabilityResourceType.DRIVER,
            "DRV-001",
            dateRange(),
            timeWindow(),
            Notes.of("Permesso personale"));

    assertTrue(availability.hasNotes());
  }

  @Test
  void shouldFormatSingleLine() {
    assertEquals("DRIVER - DRV-001 - AVAILABLE", driverAvailable().formatSingleLine());
  }

  @Test
  void shouldConsiderEquivalentAvailabilityEqual() {
    ResourceAvailability first = driverAvailable();
    ResourceAvailability second = driverAvailable();

    assertEquals(first, second);
    assertEquals(first.hashCode(), second.hashCode());
  }

  private static ResourceAvailability driverAvailable() {
    return ResourceAvailability.available(
        AvailabilityResourceType.DRIVER, "DRV-001", dateRange(), timeWindow(), Notes.empty());
  }

  private static DateRange dateRange() {
    return DateRange.of(LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 1));
  }

  private static TimeWindow timeWindow() {
    return TimeWindow.of("08:00", "18:00");
  }
}
