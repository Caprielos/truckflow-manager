package it.gabriele.truckflow.domain.route;

import it.gabriele.truckflow.domain.facility.Facility;
import it.gabriele.truckflow.domain.facility.FacilityType;
import it.gabriele.truckflow.domain.location.Address;
import it.gabriele.truckflow.domain.location.Location;
import it.gabriele.truckflow.domain.shared.Distance;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TimeWindow;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa RoutePlan.
 */
class RoutePlanTest {

    @Test
    void shouldCreateRoutePlan() {
        RoutePlan routePlan = standardRoutePlan();

        assertEquals("RTE-001", routePlan.getRouteNumber());
        assertEquals(4, routePlan.getStopCount());
        assertEquals(Distance.ofKilometers(580), routePlan.getEstimatedDistance());
        assertEquals(Notes.empty(), routePlan.getNotes());
    }

    @Test
    void shouldNormalizeRouteNumber() {
        RoutePlan routePlan = RoutePlan.of(
                "  rte_001  ",
                standardStops(),
                Distance.ofKilometers(580),
                Notes.empty()
        );

        assertEquals("RTE_001", routePlan.getRouteNumber());
    }

    @Test
    void shouldNotAllowInvalidRouteNumber() {
        assertThrows(IllegalArgumentException.class, () -> RoutePlan.of(
                null,
                standardStops(),
                Distance.ofKilometers(580),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> RoutePlan.of(
                "RTE 001",
                standardStops(),
                Distance.ofKilometers(580),
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowTooLongRouteNumber() {
        String tooLongRouteNumber = "A".repeat(51);

        assertThrows(IllegalArgumentException.class, () -> RoutePlan.of(
                tooLongRouteNumber,
                standardStops(),
                Distance.ofKilometers(580),
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowNullOrTooShortStopsList() {
        assertThrows(IllegalArgumentException.class, () -> RoutePlan.of(
                "RTE-001",
                null,
                Distance.ofKilometers(580),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> RoutePlan.of(
                "RTE-001",
                List.of(startStop()),
                Distance.ofKilometers(580),
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowNullStopsInsideList() {
        assertThrows(IllegalArgumentException.class, () -> RoutePlan.of(
                "RTE-001",
                Arrays.asList(startStop(), null, endStop()),
                Distance.ofKilometers(580),
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowNullDistanceOrNotes() {
        assertThrows(IllegalArgumentException.class, () -> RoutePlan.of(
                "RTE-001",
                standardStops(),
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> RoutePlan.of(
                "RTE-001",
                standardStops(),
                Distance.ofKilometers(580),
                null
        ));
    }

    @Test
    void shouldOrderStopsBySequenceNumber() {
        RouteStop start = startStop();
        RouteStop pickup = pickupStop();
        RouteStop delivery = deliveryStop();
        RouteStop end = endStop();

        RoutePlan routePlan = RoutePlan.of(
                "RTE-001",
                List.of(end, delivery, start, pickup),
                Distance.ofKilometers(580),
                Notes.empty()
        );

        assertEquals(start, routePlan.getStops().get(0));
        assertEquals(pickup, routePlan.getStops().get(1));
        assertEquals(delivery, routePlan.getStops().get(2));
        assertEquals(end, routePlan.getStops().get(3));
    }

    @Test
    void shouldNotAllowNonProgressiveSequenceNumbers() {
        RouteStop start = RouteStop.of(
                1,
                RouteStopType.START,
                milanFacility(),
                TimeWindow.of("08:00", "09:00"),
                Notes.empty()
        );

        RouteStop endWithWrongSequence = RouteStop.of(
                3,
                RouteStopType.END,
                romeFacility(),
                TimeWindow.of("16:00", "17:00"),
                Notes.empty()
        );

        assertThrows(IllegalArgumentException.class, () -> RoutePlan.of(
                "RTE-001",
                List.of(start, endWithWrongSequence),
                Distance.ofKilometers(580),
                Notes.empty()
        ));
    }

    @Test
    void shouldRequireFirstStopToBeStart() {
        RouteStop pickup = RouteStop.of(
                1,
                RouteStopType.PICKUP,
                milanFacility(),
                TimeWindow.of("08:00", "09:00"),
                Notes.empty()
        );

        RouteStop end = RouteStop.of(
                2,
                RouteStopType.END,
                romeFacility(),
                TimeWindow.of("16:00", "17:00"),
                Notes.empty()
        );

        assertThrows(IllegalArgumentException.class, () -> RoutePlan.of(
                "RTE-001",
                List.of(pickup, end),
                Distance.ofKilometers(580),
                Notes.empty()
        ));
    }

    @Test
    void shouldRequireLastStopToBeEnd() {
        RouteStop start = RouteStop.of(
                1,
                RouteStopType.START,
                milanFacility(),
                TimeWindow.of("08:00", "09:00"),
                Notes.empty()
        );

        RouteStop delivery = RouteStop.of(
                2,
                RouteStopType.DELIVERY,
                romeFacility(),
                TimeWindow.of("16:00", "17:00"),
                Notes.empty()
        );

        assertThrows(IllegalArgumentException.class, () -> RoutePlan.of(
                "RTE-001",
                List.of(start, delivery),
                Distance.ofKilometers(580),
                Notes.empty()
        ));
    }

    @Test
    void shouldReturnUnmodifiableStops() {
        RoutePlan routePlan = standardRoutePlan();

        assertThrows(UnsupportedOperationException.class,
                () -> routePlan.getStops().add(endStop()));
    }

    @Test
    void shouldExposeStartAndEndStops() {
        RoutePlan routePlan = standardRoutePlan();

        assertTrue(routePlan.getStartStop().isStart());
        assertTrue(routePlan.getEndStop().isEnd());
    }

    @Test
    void shouldReturnCargoOperationStops() {
        RoutePlan routePlan = standardRoutePlan();

        List<RouteStop> cargoStops = routePlan.getCargoOperationStops();

        assertEquals(2, cargoStops.size());
        assertTrue(cargoStops.get(0).isPickup());
        assertTrue(cargoStops.get(1).isDelivery());
    }

    @Test
    void shouldDetectPickupAndDeliveryStops() {
        RoutePlan routePlan = standardRoutePlan();

        assertTrue(routePlan.hasPickupStop());
        assertTrue(routePlan.hasDeliveryStop());
    }

    @Test
    void shouldDetectInternationalRoute() {
        RoutePlan routePlan = RoutePlan.of(
                "RTE-001",
                List.of(
                        RouteStop.of(1, RouteStopType.START, milanFacility(), TimeWindow.of("08:00", "09:00"), Notes.empty()),
                        RouteStop.of(2, RouteStopType.END, parisFacility(), TimeWindow.of("16:00", "17:00"), Notes.empty())
                ),
                Distance.ofKilometers(850),
                Notes.empty()
        );

        assertTrue(routePlan.isInternational());
    }

    @Test
    void shouldDetectNotes() {
        RoutePlan routePlan = RoutePlan.of(
                "RTE-001",
                standardStops(),
                Distance.ofKilometers(580),
                Notes.of("Evitare traffico urbano")
        );

        assertTrue(routePlan.hasNotes());
    }

    @Test
    void shouldFormatSingleLine() {
        RoutePlan routePlan = standardRoutePlan();

        assertEquals("RTE-001 - stops: 4 - 580.0 km", routePlan.formatSingleLine());
    }

    @Test
    void shouldConsiderEquivalentRoutePlansEqual() {
        RoutePlan first = standardRoutePlan();
        RoutePlan second = standardRoutePlan();

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    private static RoutePlan standardRoutePlan() {
        return RoutePlan.of(
                "RTE-001",
                standardStops(),
                Distance.ofKilometers(580),
                Notes.empty()
        );
    }

    private static List<RouteStop> standardStops() {
        return List.of(
                startStop(),
                pickupStop(),
                deliveryStop(),
                endStop()
        );
    }

    private static RouteStop startStop() {
        return RouteStop.of(
                1,
                RouteStopType.START,
                milanFacility(),
                TimeWindow.of("08:00", "09:00"),
                Notes.empty()
        );
    }

    private static RouteStop pickupStop() {
        return RouteStop.of(
                2,
                RouteStopType.PICKUP,
                milanFacility(),
                TimeWindow.of("09:00", "10:00"),
                Notes.empty()
        );
    }

    private static RouteStop deliveryStop() {
        return RouteStop.of(
                3,
                RouteStopType.DELIVERY,
                romeFacility(),
                TimeWindow.of("14:00", "15:00"),
                Notes.empty()
        );
    }

    private static RouteStop endStop() {
        return RouteStop.of(
                4,
                RouteStopType.END,
                romeFacility(),
                TimeWindow.of("16:00", "17:00"),
                Notes.empty()
        );
    }

    private static Facility milanFacility() {
        return Facility.active(
                "MIL-WH-01",
                FacilityType.WAREHOUSE,
                Location.of(
                        "Magazzino Milano",
                        Address.of("Via Roma 10", "Milano", "20100", "IT"),
                        "Europe/Rome"
                ),
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        );
    }

    private static Facility romeFacility() {
        return Facility.active(
                "ROM-WH-01",
                FacilityType.WAREHOUSE,
                Location.of(
                        "Magazzino Roma",
                        Address.of("Via Appia 20", "Roma", "00100", "IT"),
                        "Europe/Rome"
                ),
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        );
    }

    private static Facility parisFacility() {
        return Facility.active(
                "PAR-WH-01",
                FacilityType.WAREHOUSE,
                Location.of(
                        "Warehouse Paris",
                        Address.of("Rue de Paris 1", "Paris", "75000", "FR"),
                        "Europe/Paris"
                ),
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        );
    }
}
