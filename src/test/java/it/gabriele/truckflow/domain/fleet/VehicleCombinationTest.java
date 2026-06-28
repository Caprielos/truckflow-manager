package it.gabriele.truckflow.domain.fleet;

import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TemperatureRange;
import it.gabriele.truckflow.domain.shared.Volume;
import it.gabriele.truckflow.domain.shared.Weight;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa VehicleCombination.
 */
class VehicleCombinationTest {

    @Test
    void shouldCreateSingleVehicleCombination() {
        Vehicle truck = rigidTruck();

        VehicleCombination combination = VehicleCombination.singleVehicle(
                "combo-001",
                truck,
                Notes.empty()
        );

        assertEquals("COMBO-001", combination.getCombinationNumber());
        assertEquals(truck, combination.getPoweredUnit());
        assertNull(combination.getTrailer());
        assertFalse(combination.hasTrailer());
        assertEquals(truck, combination.getCargoUnit());
    }

    @Test
    void shouldCreateVehicleCombinationWithTrailer() {
        Vehicle tractor = tractorUnit();
        Vehicle trailer = semiTrailer();

        VehicleCombination combination = VehicleCombination.withTrailer(
                "combo-001",
                tractor,
                trailer,
                Notes.empty()
        );

        assertEquals("COMBO-001", combination.getCombinationNumber());
        assertEquals(tractor, combination.getPoweredUnit());
        assertEquals(trailer, combination.getTrailer());
        assertTrue(combination.hasTrailer());
        assertEquals(trailer, combination.getCargoUnit());
    }

    @Test
    void shouldNormalizeCombinationNumber() {
        VehicleCombination combination = VehicleCombination.singleVehicle(
                "  combo_001  ",
                rigidTruck(),
                Notes.empty()
        );

        assertEquals("COMBO_001", combination.getCombinationNumber());
    }

    @Test
    void shouldNotAllowInvalidCombinationNumber() {
        assertThrows(IllegalArgumentException.class, () -> VehicleCombination.singleVehicle(
                null,
                rigidTruck(),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> VehicleCombination.singleVehicle(
                "COMBO 001",
                rigidTruck(),
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowTooLongCombinationNumber() {
        String tooLongCombinationNumber = "A".repeat(51);

        assertThrows(IllegalArgumentException.class, () -> VehicleCombination.singleVehicle(
                tooLongCombinationNumber,
                rigidTruck(),
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowNullPoweredUnitOrNotes() {
        assertThrows(IllegalArgumentException.class, () -> VehicleCombination.singleVehicle(
                "COMBO-001",
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> VehicleCombination.singleVehicle(
                "COMBO-001",
                rigidTruck(),
                null
        ));
    }

    @Test
    void shouldNotAllowTrailerAsPoweredUnit() {
        assertThrows(IllegalArgumentException.class, () -> VehicleCombination.singleVehicle(
                "COMBO-001",
                semiTrailer(),
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowSingleNonCargoVehicle() {
        assertThrows(IllegalArgumentException.class, () -> VehicleCombination.singleVehicle(
                "COMBO-001",
                tractorUnit(),
                Notes.empty()
        ));
    }

    @Test
    void shouldRequireTrailerInTrailerFactory() {
        assertThrows(IllegalArgumentException.class, () -> VehicleCombination.withTrailer(
                "COMBO-001",
                tractorUnit(),
                null,
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowNonTrailerAsTrailer() {
        assertThrows(IllegalArgumentException.class, () -> VehicleCombination.withTrailer(
                "COMBO-001",
                tractorUnit(),
                rigidTruck(),
                Notes.empty()
        ));
    }

    @Test
    void shouldExposeCargoCapacityFromSingleVehicle() {
        VehicleCombination combination = VehicleCombination.singleVehicle(
                "COMBO-001",
                rigidTruck(),
                Notes.empty()
        );

        assertEquals(Weight.ofKilograms(12000), combination.getMaxPayload());
        assertEquals(Dimension.ofMeters(7, 2.4, 2.5), combination.getCargoSpaceDimension());
        assertEquals(Volume.ofCubicMeters(42), combination.calculateCargoSpaceVolume());
    }

    @Test
    void shouldExposeCargoCapacityFromTrailer() {
        VehicleCombination combination = VehicleCombination.withTrailer(
                "COMBO-001",
                tractorUnit(),
                semiTrailer(),
                Notes.empty()
        );

        assertEquals(Weight.ofKilograms(20000), combination.getMaxPayload());
        assertEquals(Dimension.ofMeters(13.6, 2.4, 2.7), combination.getCargoSpaceDimension());
    }

    @Test
    void shouldCheckIfCombinationCanBeAssigned() {
        VehicleCombination availableCombination = VehicleCombination.withTrailer(
                "COMBO-001",
                tractorUnit(),
                semiTrailer(),
                Notes.empty()
        );

        VehicleCombination unavailableCombination = VehicleCombination.withTrailer(
                "COMBO-002",
                assignedTractorUnit(),
                semiTrailer(),
                Notes.empty()
        );

        assertTrue(availableCombination.canBeAssigned());
        assertFalse(unavailableCombination.canBeAssigned());
    }

    @Test
    void shouldCheckWeightCapacity() {
        VehicleCombination combination = VehicleCombination.withTrailer(
                "COMBO-001",
                tractorUnit(),
                semiTrailer(),
                Notes.empty()
        );

        assertTrue(combination.canCarryWeight(Weight.ofKilograms(15000)));
        assertFalse(combination.canCarryWeight(Weight.ofKilograms(25000)));
    }

    @Test
    void shouldCheckDimensionCapacity() {
        VehicleCombination combination = VehicleCombination.withTrailer(
                "COMBO-001",
                tractorUnit(),
                semiTrailer(),
                Notes.empty()
        );

        assertTrue(combination.canFitDimension(Dimension.ofMeters(10, 2, 2)));
        assertFalse(combination.canFitDimension(Dimension.ofMeters(14, 2, 2)));
    }

    @Test
    void shouldCheckTemperatureCapacity() {
        VehicleCombination combination = VehicleCombination.withTrailer(
                "COMBO-001",
                tractorUnit(),
                refrigeratedTrailer(),
                Notes.empty()
        );

        assertTrue(combination.supportsTemperatureControl());
        assertTrue(combination.canSupportTemperatureRange(TemperatureRange.ofCelsius(2, 6)));
        assertFalse(combination.canSupportTemperatureRange(TemperatureRange.ofCelsius(-2, 6)));
    }

    @Test
    void shouldNotCheckNullCapacityValues() {
        VehicleCombination combination = VehicleCombination.singleVehicle(
                "COMBO-001",
                rigidTruck(),
                Notes.empty()
        );

        assertThrows(IllegalArgumentException.class, () -> combination.canCarryWeight(null));
        assertThrows(IllegalArgumentException.class, () -> combination.canFitDimension(null));
        assertThrows(IllegalArgumentException.class, () -> combination.canSupportTemperatureRange(null));
    }

    @Test
    void shouldDetectNotes() {
        VehicleCombination combination = VehicleCombination.singleVehicle(
                "COMBO-001",
                rigidTruck(),
                Notes.of("Combinazione preferita per tratte urbane")
        );

        assertTrue(combination.hasNotes());
    }

    @Test
    void shouldFormatSingleVehicleCombination() {
        VehicleCombination combination = VehicleCombination.singleVehicle(
                "COMBO-001",
                rigidTruck(),
                Notes.empty()
        );

        assertEquals("COMBO-001 - TRUCK-001", combination.formatSingleLine());
    }

    @Test
    void shouldFormatTrailerCombination() {
        VehicleCombination combination = VehicleCombination.withTrailer(
                "COMBO-001",
                tractorUnit(),
                semiTrailer(),
                Notes.empty()
        );

        assertEquals("COMBO-001 - TRACTOR-001 + TRAILER-001", combination.formatSingleLine());
    }

    @Test
    void shouldConsiderEquivalentCombinationsEqual() {
        VehicleCombination first = VehicleCombination.withTrailer(
                "COMBO-001",
                tractorUnit(),
                semiTrailer(),
                Notes.empty()
        );

        VehicleCombination second = VehicleCombination.withTrailer(
                "COMBO-001",
                tractorUnit(),
                semiTrailer(),
                Notes.empty()
        );

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    private static Vehicle rigidTruck() {
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

    private static Vehicle tractorUnit() {
        return Vehicle.nonCargoVehicle(
                "TRACTOR-001",
                "TR 001 AA",
                "JH4KA8260MC000000",
                VehicleType.TRACTOR_UNIT,
                VehicleStatus.AVAILABLE,
                standardTire(),
                Notes.empty()
        );
    }

    private static Vehicle assignedTractorUnit() {
        return Vehicle.nonCargoVehicle(
                "TRACTOR-002",
                "TR 002 AA",
                "JH4KA8260MC000001",
                VehicleType.TRACTOR_UNIT,
                VehicleStatus.ASSIGNED,
                standardTire(),
                Notes.empty()
        );
    }

    private static Vehicle semiTrailer() {
        return Vehicle.cargoVehicle(
                "TRAILER-001",
                "TRL 001",
                "VF1BBBBB555123456",
                VehicleType.SEMI_TRAILER,
                VehicleBodyType.CURTAIN_SIDE,
                VehicleStatus.AVAILABLE,
                standardTire(),
                Weight.ofKilograms(20000),
                Dimension.ofMeters(13.6, 2.4, 2.7),
                null,
                Notes.empty()
        );
    }

    private static Vehicle refrigeratedTrailer() {
        return Vehicle.cargoVehicle(
                "FRIGO-TRAILER-001",
                "FTR 001",
                "VF1CCCCC555123456",
                VehicleType.REFRIGERATED_TRAILER,
                VehicleBodyType.REFRIGERATED_BOX,
                VehicleStatus.AVAILABLE,
                standardTire(),
                Weight.ofKilograms(18000),
                Dimension.ofMeters(13.6, 2.4, 2.5),
                TemperatureRange.ofCelsius(0, 8),
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

}

