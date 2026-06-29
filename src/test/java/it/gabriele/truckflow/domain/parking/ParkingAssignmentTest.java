package it.gabriele.truckflow.domain.parking;

import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParkingAssignmentTest {

    @Test
    void shouldParkVanInNumberedSpot() {
        ParkingSpot spot = ParkingSpot.available(
                "DEPOT-MIL-01",
                "100",
                ParkingSpotType.VAN_SPACE,
                7.0,
                3.0,
                false,
                Notes.empty()
        );

        ParkedResource van = ParkedResource.van("VAN-001", "Furgone AA123BB", 5.4);

        ParkingAssignment assignment = ParkingAssignment.active(
                "PARK-001",
                spot,
                van,
                LocalDateTime.of(2026, 6, 29, 18, 30),
                Notes.of("Furgone pronto per consegna urbana")
        );

        assertEquals("DEPOT-MIL-01", assignment.getFacilityCode());
        assertEquals("100", assignment.getSpotNumber());
        assertTrue(assignment.isActive());
        assertTrue(assignment.isReadyForMission());
        assertTrue(assignment.containsResource("van-001"));
    }

    @Test
    void shouldParkReadyArticulatedVehicleAsSingleOperationalUnit() {
        ParkingSpot spot = ParkingSpot.available(
                "DEPOT-MIL-01",
                "A12",
                ParkingSpotType.FULL_COMBINATION_SPACE,
                18.75,
                3.2,
                true,
                Notes.of("Posto lungo con presa per semirimorchio frigo")
        );

        ParkedResource articulatedVehicle = ParkedResource.articulatedVehicle(
                "COMBO-001",
                "TRACTOR-001",
                "SEMI-001",
                "Trattore Scania + semirimorchio frigo già agganciato",
                16.5,
                true
        );

        ParkingAssignment assignment = ParkingAssignment.active(
                "PARK-COMBO-001",
                spot,
                articulatedVehicle,
                LocalDateTime.of(2026, 6, 29, 20, 0),
                Notes.of("Convoglio pronto per partenza notturna")
        );

        assertTrue(assignment.parksCombination());
        assertTrue(assignment.isReadyForMission());
        assertTrue(ParkingRules.isReadyCombinationParked(assignment));
        assertTrue(assignment.containsResource("TRACTOR-001"));
        assertTrue(assignment.containsResource("SEMI-001"));
    }

    @Test
    void shouldParkTruckAndTrailerAutotrenoAsReadyCombination() {
        ParkingSpot spot = ParkingSpot.available(
                "DEPOT-MIL-01",
                "B20",
                ParkingSpotType.LONG_COMBINATION_SPACE,
                18.75,
                3.2,
                false,
                Notes.empty()
        );

        ParkedResource autotreno = ParkedResource.truckAndTrailer(
                "COMBO-AT-001",
                "RIGID-001",
                "TRAILER-001",
                "Autotreno centinato completo",
                18.2,
                true
        );

        ParkingAssignment assignment = ParkingAssignment.active(
                "PARK-AT-001",
                spot,
                autotreno,
                LocalDateTime.of(2026, 6, 29, 21, 15),
                Notes.empty()
        );

        assertTrue(assignment.parksCombination());
        assertTrue(assignment.isReadyForMission());
        assertEquals(2, assignment.getParkedResource().componentCount());
    }

    @Test
    void shouldRejectCombinationInVanSpot() {
        ParkingSpot spot = ParkingSpot.available(
                "DEPOT-MIL-01",
                "100",
                ParkingSpotType.VAN_SPACE,
                7.0,
                3.0,
                false,
                Notes.empty()
        );

        ParkedResource articulatedVehicle = ParkedResource.articulatedVehicle(
                "COMBO-001",
                "TRACTOR-001",
                "SEMI-001",
                "Trattore + semirimorchio",
                16.5,
                true
        );

        assertFalse(ParkingRules.canPark(spot, articulatedVehicle));
        assertThrows(IllegalArgumentException.class, () -> ParkingAssignment.active(
                "PARK-ERR",
                spot,
                articulatedVehicle,
                LocalDateTime.of(2026, 6, 29, 20, 0),
                Notes.empty()
        ));
    }

    @Test
    void shouldDetectOccupiedSpotAndParkedResourcesAtGivenTime() {
        ParkingSpot spot = ParkingSpot.available(
                "DEPOT-MIL-01",
                "A12",
                ParkingSpotType.FULL_COMBINATION_SPACE,
                18.75,
                3.2,
                true,
                Notes.empty()
        );
        ParkedResource articulatedVehicle = ParkedResource.articulatedVehicle(
                "COMBO-001",
                "TRACTOR-001",
                "SEMI-001",
                "Trattore + semirimorchio",
                16.5,
                true
        );
        ParkingAssignment assignment = ParkingAssignment.active(
                "PARK-COMBO-001",
                spot,
                articulatedVehicle,
                LocalDateTime.of(2026, 6, 29, 20, 0),
                Notes.empty()
        );

        LocalDateTime checkTime = LocalDateTime.of(2026, 6, 29, 22, 0);

        assertFalse(ParkingRules.isSpotFreeAt("DEPOT-MIL-01", "A12", checkTime, List.of(assignment)));
        assertTrue(ParkingRules.isResourceAlreadyParkedAt("TRACTOR-001", checkTime, List.of(assignment)));
        assertTrue(ParkingRules.isResourceAlreadyParkedAt("SEMI-001", checkTime, List.of(assignment)));
        assertFalse(ParkingRules.isResourceAlreadyParkedAt("VAN-999", checkTime, List.of(assignment)));
    }
}
