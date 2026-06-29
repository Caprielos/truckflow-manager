package it.gabriele.truckflow.domain.route;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.facility.Facility;
import it.gabriele.truckflow.domain.facility.FacilityType;
import it.gabriele.truckflow.domain.location.Address;
import it.gabriele.truckflow.domain.location.Location;
import it.gabriele.truckflow.domain.shared.Distance;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TimeWindow;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Testa RoutePlanRules. */
class RoutePlanRulesTest {

  @Test
  void shouldDetectCargoOperations() {
    RoutePlan routePlan = standardRoutePlan();

    assertTrue(RoutePlanRules.hasCargoOperations(routePlan));
  }

  @Test
  void shouldDetectRouteWithoutCargoOperations() {
    RoutePlan routePlan =
        RoutePlan.of(
            "RTE-001",
            List.of(
                RouteStop.of(
                    1,
                    RouteStopType.START,
                    milanFacility(),
                    TimeWindow.of("08:00", "09:00"),
                    Notes.empty()),
                RouteStop.of(
                    2,
                    RouteStopType.END,
                    romeFacility(),
                    TimeWindow.of("16:00", "17:00"),
                    Notes.empty())),
            Distance.ofKilometers(580),
            Notes.empty());

    assertFalse(RoutePlanRules.hasCargoOperations(routePlan));
  }

  @Test
  void shouldCheckPickupAndDeliveryPresence() {
    RoutePlan routePlan = standardRoutePlan();

    assertTrue(RoutePlanRules.hasPickupAndDelivery(routePlan));
  }

  @Test
  void shouldDetectMissingDelivery() {
    RoutePlan routePlan =
        RoutePlan.of(
            "RTE-001",
            List.of(
                RouteStop.of(
                    1,
                    RouteStopType.START,
                    milanFacility(),
                    TimeWindow.of("08:00", "09:00"),
                    Notes.empty()),
                RouteStop.of(
                    2,
                    RouteStopType.PICKUP,
                    milanFacility(),
                    TimeWindow.of("09:00", "10:00"),
                    Notes.empty()),
                RouteStop.of(
                    3,
                    RouteStopType.END,
                    romeFacility(),
                    TimeWindow.of("16:00", "17:00"),
                    Notes.empty())),
            Distance.ofKilometers(580),
            Notes.empty());

    assertFalse(RoutePlanRules.hasPickupAndDelivery(routePlan));
  }

  @Test
  void shouldCheckIfRouteIsWithinMaxDistance() {
    RoutePlan routePlan = standardRoutePlan();

    assertTrue(RoutePlanRules.isWithinMaxDistance(routePlan, Distance.ofKilometers(600)));
    assertFalse(RoutePlanRules.isWithinMaxDistance(routePlan, Distance.ofKilometers(500)));
  }

  @Test
  void shouldCheckStartAndEndFacilitiesAreDifferent() {
    RoutePlan routePlan = standardRoutePlan();

    assertTrue(RoutePlanRules.startsAndEndsAtDifferentFacilities(routePlan));
  }

  @Test
  void shouldDetectSameStartAndEndFacility() {
    RoutePlan routePlan =
        RoutePlan.of(
            "RTE-001",
            List.of(
                RouteStop.of(
                    1,
                    RouteStopType.START,
                    milanFacility(),
                    TimeWindow.of("08:00", "09:00"),
                    Notes.empty()),
                RouteStop.of(
                    2,
                    RouteStopType.PICKUP,
                    milanFacility(),
                    TimeWindow.of("09:00", "10:00"),
                    Notes.empty()),
                RouteStop.of(
                    3,
                    RouteStopType.DELIVERY,
                    milanFacility(),
                    TimeWindow.of("14:00", "15:00"),
                    Notes.empty()),
                RouteStop.of(
                    4,
                    RouteStopType.END,
                    milanFacility(),
                    TimeWindow.of("16:00", "17:00"),
                    Notes.empty())),
            Distance.ofKilometers(20),
            Notes.empty());

    assertFalse(RoutePlanRules.startsAndEndsAtDifferentFacilities(routePlan));
  }

  @Test
  void shouldCheckIfRouteUsesOnlyActiveFacilities() {
    RoutePlan routePlan = standardRoutePlan();

    assertTrue(RoutePlanRules.usesOnlyActiveFacilities(routePlan));
  }

  @Test
  void shouldDetectInactiveFacility() {
    RoutePlan routePlan =
        RoutePlan.of(
            "RTE-001",
            List.of(
                RouteStop.of(
                    1,
                    RouteStopType.START,
                    milanFacility(),
                    TimeWindow.of("08:00", "09:00"),
                    Notes.empty()),
                RouteStop.of(
                    2,
                    RouteStopType.PICKUP,
                    inactiveMilanFacility(),
                    TimeWindow.of("09:00", "10:00"),
                    Notes.empty()),
                RouteStop.of(
                    3,
                    RouteStopType.DELIVERY,
                    romeFacility(),
                    TimeWindow.of("14:00", "15:00"),
                    Notes.empty()),
                RouteStop.of(
                    4,
                    RouteStopType.END,
                    romeFacility(),
                    TimeWindow.of("16:00", "17:00"),
                    Notes.empty())),
            Distance.ofKilometers(580),
            Notes.empty());

    assertFalse(RoutePlanRules.usesOnlyActiveFacilities(routePlan));
  }

  @Test
  void shouldDetectInternationalRoute() {
    RoutePlan routePlan =
        RoutePlan.of(
            "RTE-001",
            List.of(
                RouteStop.of(
                    1,
                    RouteStopType.START,
                    milanFacility(),
                    TimeWindow.of("08:00", "09:00"),
                    Notes.empty()),
                RouteStop.of(
                    2,
                    RouteStopType.END,
                    parisFacility(),
                    TimeWindow.of("16:00", "17:00"),
                    Notes.empty())),
            Distance.ofKilometers(850),
            Notes.empty());

    assertTrue(RoutePlanRules.isInternational(routePlan));
  }

  @Test
  void shouldCheckIfRouteIsOperationallyUsable() {
    RoutePlan routePlan = standardRoutePlan();

    assertTrue(RoutePlanRules.isOperationallyUsable(routePlan));
  }

  @Test
  void shouldDetectRouteNotOperationallyUsableWithoutDelivery() {
    RoutePlan routePlan =
        RoutePlan.of(
            "RTE-001",
            List.of(
                RouteStop.of(
                    1,
                    RouteStopType.START,
                    milanFacility(),
                    TimeWindow.of("08:00", "09:00"),
                    Notes.empty()),
                RouteStop.of(
                    2,
                    RouteStopType.PICKUP,
                    milanFacility(),
                    TimeWindow.of("09:00", "10:00"),
                    Notes.empty()),
                RouteStop.of(
                    3,
                    RouteStopType.END,
                    romeFacility(),
                    TimeWindow.of("16:00", "17:00"),
                    Notes.empty())),
            Distance.ofKilometers(580),
            Notes.empty());

    assertFalse(RoutePlanRules.isOperationallyUsable(routePlan));
  }

  @Test
  void shouldNotAllowNullRoutePlan() {
    assertThrows(IllegalArgumentException.class, () -> RoutePlanRules.hasCargoOperations(null));
    assertThrows(IllegalArgumentException.class, () -> RoutePlanRules.hasPickupAndDelivery(null));
    assertThrows(
        IllegalArgumentException.class,
        () -> RoutePlanRules.isWithinMaxDistance(null, Distance.ofKilometers(100)));
    assertThrows(
        IllegalArgumentException.class,
        () -> RoutePlanRules.startsAndEndsAtDifferentFacilities(null));
    assertThrows(
        IllegalArgumentException.class, () -> RoutePlanRules.usesOnlyActiveFacilities(null));
    assertThrows(IllegalArgumentException.class, () -> RoutePlanRules.isInternational(null));
    assertThrows(IllegalArgumentException.class, () -> RoutePlanRules.isOperationallyUsable(null));
  }

  @Test
  void shouldNotAllowNullMaxDistance() {
    RoutePlan routePlan = standardRoutePlan();

    assertThrows(
        IllegalArgumentException.class, () -> RoutePlanRules.isWithinMaxDistance(routePlan, null));
  }

  private static RoutePlan standardRoutePlan() {
    return RoutePlan.of(
        "RTE-001",
        List.of(
            RouteStop.of(
                1,
                RouteStopType.START,
                milanFacility(),
                TimeWindow.of("08:00", "09:00"),
                Notes.empty()),
            RouteStop.of(
                2,
                RouteStopType.PICKUP,
                milanFacility(),
                TimeWindow.of("09:00", "10:00"),
                Notes.empty()),
            RouteStop.of(
                3,
                RouteStopType.DELIVERY,
                romeFacility(),
                TimeWindow.of("14:00", "15:00"),
                Notes.empty()),
            RouteStop.of(
                4,
                RouteStopType.END,
                romeFacility(),
                TimeWindow.of("16:00", "17:00"),
                Notes.empty())),
        Distance.ofKilometers(580),
        Notes.empty());
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

  private static Facility inactiveMilanFacility() {
    return Facility.inactive(
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

  private static Facility parisFacility() {
    return Facility.active(
        "PAR-WH-01",
        FacilityType.WAREHOUSE,
        Location.of(
            "Warehouse Paris",
            Address.of("Rue de Paris 1", "Paris", "75000", "FR"),
            "Europe/Paris"),
        TimeWindow.of("08:00", "18:00"),
        Notes.empty());
  }
}
