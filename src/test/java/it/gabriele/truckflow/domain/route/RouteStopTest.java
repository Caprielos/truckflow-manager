package it.gabriele.truckflow.domain.route;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.facility.Facility;
import it.gabriele.truckflow.domain.facility.FacilityType;
import it.gabriele.truckflow.domain.location.Address;
import it.gabriele.truckflow.domain.location.Location;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TimeWindow;
import org.junit.jupiter.api.Test;

/** Testa RouteStop. */
class RouteStopTest {

  @Test
  void shouldCreateRouteStop() {
    Facility facility = milanFacility();
    TimeWindow timeWindow = TimeWindow.of("08:00", "12:00");

    RouteStop stop = RouteStop.of(1, RouteStopType.PICKUP, facility, timeWindow, Notes.empty());

    assertEquals(1, stop.getSequenceNumber());
    assertEquals(RouteStopType.PICKUP, stop.getType());
    assertEquals(facility, stop.getFacility());
    assertEquals(timeWindow, stop.getPlannedTimeWindow());
    assertEquals(Notes.empty(), stop.getNotes());
  }

  @Test
  void shouldNotAllowInvalidSequenceNumber() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            RouteStop.of(
                0,
                RouteStopType.PICKUP,
                milanFacility(),
                TimeWindow.of("08:00", "12:00"),
                Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            RouteStop.of(
                -1,
                RouteStopType.PICKUP,
                milanFacility(),
                TimeWindow.of("08:00", "12:00"),
                Notes.empty()));
  }

  @Test
  void shouldNotAllowNullMandatoryFields() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            RouteStop.of(1, null, milanFacility(), TimeWindow.of("08:00", "12:00"), Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            RouteStop.of(
                1, RouteStopType.PICKUP, null, TimeWindow.of("08:00", "12:00"), Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () -> RouteStop.of(1, RouteStopType.PICKUP, milanFacility(), null, Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            RouteStop.of(
                1, RouteStopType.PICKUP, milanFacility(), TimeWindow.of("08:00", "12:00"), null));
  }

  @Test
  void shouldDetectStopTypes() {
    RouteStop start =
        RouteStop.of(
            1,
            RouteStopType.START,
            milanFacility(),
            TimeWindow.of("08:00", "09:00"),
            Notes.empty());
    RouteStop pickup =
        RouteStop.of(
            2,
            RouteStopType.PICKUP,
            milanFacility(),
            TimeWindow.of("09:00", "10:00"),
            Notes.empty());
    RouteStop delivery =
        RouteStop.of(
            3,
            RouteStopType.DELIVERY,
            romeFacility(),
            TimeWindow.of("14:00", "15:00"),
            Notes.empty());
    RouteStop end =
        RouteStop.of(
            4, RouteStopType.END, romeFacility(), TimeWindow.of("15:00", "16:00"), Notes.empty());

    assertTrue(start.isStart());
    assertTrue(pickup.isPickup());
    assertTrue(delivery.isDelivery());
    assertTrue(end.isEnd());
  }

  @Test
  void shouldDetectCargoOperation() {
    RouteStop pickup =
        RouteStop.of(
            1,
            RouteStopType.PICKUP,
            milanFacility(),
            TimeWindow.of("08:00", "12:00"),
            Notes.empty());
    RouteStop delivery =
        RouteStop.of(
            2,
            RouteStopType.DELIVERY,
            romeFacility(),
            TimeWindow.of("14:00", "18:00"),
            Notes.empty());
    RouteStop fuelStop =
        RouteStop.of(
            3,
            RouteStopType.FUEL_STOP,
            romeFacility(),
            TimeWindow.of("18:00", "19:00"),
            Notes.empty());

    assertTrue(pickup.isCargoOperation());
    assertTrue(delivery.isCargoOperation());
    assertFalse(fuelStop.isCargoOperation());
  }

  @Test
  void shouldCheckIfStopsAreAtSameFacility() {
    Facility facility = milanFacility();

    RouteStop first =
        RouteStop.of(
            1, RouteStopType.START, facility, TimeWindow.of("08:00", "09:00"), Notes.empty());
    RouteStop second =
        RouteStop.of(
            2, RouteStopType.PICKUP, facility, TimeWindow.of("09:00", "10:00"), Notes.empty());
    RouteStop third =
        RouteStop.of(
            3,
            RouteStopType.DELIVERY,
            romeFacility(),
            TimeWindow.of("14:00", "15:00"),
            Notes.empty());

    assertTrue(first.isAtSameFacility(second));
    assertFalse(first.isAtSameFacility(third));
  }

  @Test
  void shouldNotCompareSameFacilityWithNullStop() {
    RouteStop stop =
        RouteStop.of(
            1,
            RouteStopType.PICKUP,
            milanFacility(),
            TimeWindow.of("08:00", "12:00"),
            Notes.empty());

    assertThrows(IllegalArgumentException.class, () -> stop.isAtSameFacility(null));
  }

  @Test
  void shouldCheckIfStopIsBeforeAnotherStop() {
    RouteStop first =
        RouteStop.of(
            1,
            RouteStopType.PICKUP,
            milanFacility(),
            TimeWindow.of("08:00", "12:00"),
            Notes.empty());
    RouteStop second =
        RouteStop.of(
            2,
            RouteStopType.DELIVERY,
            romeFacility(),
            TimeWindow.of("14:00", "18:00"),
            Notes.empty());

    assertTrue(first.isBefore(second));
    assertFalse(second.isBefore(first));
  }

  @Test
  void shouldNotCompareOrderWithNullStop() {
    RouteStop stop =
        RouteStop.of(
            1,
            RouteStopType.PICKUP,
            milanFacility(),
            TimeWindow.of("08:00", "12:00"),
            Notes.empty());

    assertThrows(IllegalArgumentException.class, () -> stop.isBefore(null));
  }

  @Test
  void shouldFormatSingleLine() {
    RouteStop stop =
        RouteStop.of(
            1,
            RouteStopType.PICKUP,
            milanFacility(),
            TimeWindow.of("08:00", "12:00"),
            Notes.empty());

    assertEquals("1 - PICKUP - MIL-WH-01 - 08:00 - 12:00", stop.formatSingleLine());
  }

  @Test
  void shouldConsiderEquivalentRouteStopsEqual() {
    RouteStop first =
        RouteStop.of(
            1,
            RouteStopType.PICKUP,
            milanFacility(),
            TimeWindow.of("08:00", "12:00"),
            Notes.empty());

    RouteStop second =
        RouteStop.of(
            1,
            RouteStopType.PICKUP,
            milanFacility(),
            TimeWindow.of("08:00", "12:00"),
            Notes.empty());

    assertEquals(first, second);
    assertEquals(first.hashCode(), second.hashCode());
  }

  private static Facility milanFacility() {
    return Facility.active(
        "MIL-WH-01",
        FacilityType.WAREHOUSE,
        Location.of(
            "Magazzino Milano", Address.of("Via Roma 10", "Milano", "20100", "IT"), "Europe/Rome"),
        TimeWindow.of("08:00", "18:00"),
        Notes.empty());
  }

  private static Facility romeFacility() {
    return Facility.active(
        "ROM-WH-01",
        FacilityType.WAREHOUSE,
        Location.of(
            "Magazzino Roma", Address.of("Via Appia 20", "Roma", "00100", "IT"), "Europe/Rome"),
        TimeWindow.of("08:00", "18:00"),
        Notes.empty());
  }
}
