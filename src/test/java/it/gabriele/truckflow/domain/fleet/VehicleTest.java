package it.gabriele.truckflow.domain.fleet;

import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TemperatureRange;
import it.gabriele.truckflow.domain.shared.Volume;
import it.gabriele.truckflow.domain.shared.Weight;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa Vehicle.
 */
class VehicleTest {

    @Test
    void shouldCreateCargoVehicle() {
        Vehicle vehicle = standardTruck();

        assertEquals("TRUCK-001", vehicle.getFleetNumber());
        assertEquals("AB 123 CD", vehicle.getLicensePlate());
        assertEquals(VehicleType.RIGID_TRUCK, vehicle.getType());
        assertEquals(VehicleStatus.AVAILABLE, vehicle.getStatus());
        assertEquals(Weight.ofKilograms(12000), vehicle.getMaxPayload());
        assertEquals(Dimension.ofMeters(7, 2.4, 2.5), vehicle.getCargoSpaceDimension());
        assertEquals(Notes.empty(), vehicle.getNotes());
        assertTrue(vehicle.canCarryCargo());
        assertTrue(vehicle.isAvailable());
        assertTrue(vehicle.canBeAssigned());
    }

    @Test
    void shouldCreateNonCargoVehicle() {
        Vehicle vehicle = Vehicle.nonCargoVehicle(
                "tractor-001",
                "TR 001 AA",
                VehicleType.TRACTOR_UNIT,
                VehicleStatus.AVAILABLE,
                Notes.empty()
        );

        assertEquals("TRACTOR-001", vehicle.getFleetNumber());
        assertEquals("TR 001 AA", vehicle.getLicensePlate());
        assertEquals(VehicleType.TRACTOR_UNIT, vehicle.getType());
        assertFalse(vehicle.canCarryCargo());
        assertTrue(vehicle.isPoweredUnit());
    }

    @Test
    void shouldCreateRefrigeratedVehicle() {
        TemperatureRange range = TemperatureRange.ofCelsius(0, 8);

        Vehicle vehicle = Vehicle.cargoVehicle(
                "frigo-001",
                "FR 001 AA",
                VehicleType.REFRIGERATED_TRUCK,
                VehicleStatus.AVAILABLE,
                Weight.ofKilograms(8000),
                Dimension.ofMeters(6, 2.4, 2.4),
                range,
                Notes.empty()
        );

        assertTrue(vehicle.supportsTemperatureControl());
        assertEquals(range, vehicle.getTemperatureRange());
    }

    @Test
    void shouldNormalizeFleetNumberAndLicensePlate() {
        Vehicle vehicle = Vehicle.cargoVehicle(
                "  truck_001  ",
                "  ab 123 cd  ",
                VehicleType.RIGID_TRUCK,
                VehicleStatus.AVAILABLE,
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                Notes.empty()
        );

        assertEquals("TRUCK_001", vehicle.getFleetNumber());
        assertEquals("AB 123 CD", vehicle.getLicensePlate());
    }

    @Test
    void shouldNotAllowInvalidFleetNumber() {
        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                null,
                "AB 123 CD",
                VehicleType.RIGID_TRUCK,
                VehicleStatus.AVAILABLE,
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "TRUCK 001",
                "AB 123 CD",
                VehicleType.RIGID_TRUCK,
                VehicleStatus.AVAILABLE,
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowInvalidLicensePlate() {
        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "TRUCK-001",
                null,
                VehicleType.RIGID_TRUCK,
                VehicleStatus.AVAILABLE,
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "TRUCK-001",
                "AB@123",
                VehicleType.RIGID_TRUCK,
                VehicleStatus.AVAILABLE,
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowNullMandatoryFields() {
        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "TRUCK-001",
                "AB 123 CD",
                null,
                VehicleStatus.AVAILABLE,
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "TRUCK-001",
                "AB 123 CD",
                VehicleType.RIGID_TRUCK,
                null,
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "TRUCK-001",
                "AB 123 CD",
                VehicleType.RIGID_TRUCK,
                VehicleStatus.AVAILABLE,
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                null
        ));
    }

    @Test
    void shouldRequireCargoDataForCargoVehicle() {
        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "TRUCK-001",
                "AB 123 CD",
                VehicleType.RIGID_TRUCK,
                VehicleStatus.AVAILABLE,
                null,
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "TRUCK-001",
                "AB 123 CD",
                VehicleType.RIGID_TRUCK,
                VehicleStatus.AVAILABLE,
                Weight.ofKilograms(12000),
                null,
                null,
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowCargoDataForNonCargoVehicle() {
        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "TRACTOR-001",
                "TR 001 AA",
                VehicleType.TRACTOR_UNIT,
                VehicleStatus.AVAILABLE,
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                Notes.empty()
        ));
    }

    @Test
    void shouldRequireTemperatureRangeForRefrigeratedVehicle() {
        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "FRIGO-001",
                "FR 001 AA",
                VehicleType.REFRIGERATED_TRUCK,
                VehicleStatus.AVAILABLE,
                Weight.ofKilograms(8000),
                Dimension.ofMeters(6, 2.4, 2.4),
                null,
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowTemperatureRangeForNonRefrigeratedVehicle() {
        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "TRUCK-001",
                "AB 123 CD",
                VehicleType.RIGID_TRUCK,
                VehicleStatus.AVAILABLE,
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                TemperatureRange.ofCelsius(0, 8),
                Notes.empty()
        ));
    }

    @Test
    void shouldCalculateCargoSpaceVolume() {
        Vehicle vehicle = standardTruck();

        assertEquals(Volume.ofCubicMeters(42), vehicle.calculateCargoSpaceVolume());
    }

    @Test
    void shouldNotCalculateCargoSpaceVolumeForNonCargoVehicle() {
        Vehicle tractor = Vehicle.nonCargoVehicle(
                "TRACTOR-001",
                "TR 001 AA",
                VehicleType.TRACTOR_UNIT,
                VehicleStatus.AVAILABLE,
                Notes.empty()
        );

        assertThrows(IllegalStateException.class, tractor::calculateCargoSpaceVolume);
    }

    @Test
    void shouldCheckWeightCapacity() {
        Vehicle vehicle = standardTruck();

        assertTrue(vehicle.canCarryWeight(Weight.ofKilograms(10000)));
        assertTrue(vehicle.canCarryWeight(Weight.ofKilograms(12000)));
        assertFalse(vehicle.canCarryWeight(Weight.ofKilograms(13000)));
    }

    @Test
    void shouldCheckDimensionCapacity() {
        Vehicle vehicle = standardTruck();

        assertTrue(vehicle.canFitDimension(Dimension.ofMeters(6, 2, 2)));
        assertFalse(vehicle.canFitDimension(Dimension.ofMeters(8, 2, 2)));
    }

    @Test
    void shouldCheckTemperatureRangeCapacity() {
        Vehicle vehicle = Vehicle.cargoVehicle(
                "FRIGO-001",
                "FR 001 AA",
                VehicleType.REFRIGERATED_TRUCK,
                VehicleStatus.AVAILABLE,
                Weight.ofKilograms(8000),
                Dimension.ofMeters(6, 2.4, 2.4),
                TemperatureRange.ofCelsius(0, 8),
                Notes.empty()
        );

        assertTrue(vehicle.canSupportTemperatureRange(TemperatureRange.ofCelsius(2, 6)));
        assertFalse(vehicle.canSupportTemperatureRange(TemperatureRange.ofCelsius(-2, 6)));
    }

    @Test
    void shouldNotCheckNullCapacityValues() {
        Vehicle vehicle = standardTruck();

        assertThrows(IllegalArgumentException.class, () -> vehicle.canCarryWeight(null));
        assertThrows(IllegalArgumentException.class, () -> vehicle.canFitDimension(null));
        assertThrows(IllegalArgumentException.class, () -> vehicle.canSupportTemperatureRange(null));
    }

    @Test
    void shouldDetectTrailer() {
        Vehicle trailer = Vehicle.cargoVehicle(
                "TRL-001",
                "TRAILER 01",
                VehicleType.SEMI_TRAILER,
                VehicleStatus.AVAILABLE,
                Weight.ofKilograms(20000),
                Dimension.ofMeters(13.6, 2.4, 2.7),
                null,
                Notes.empty()
        );

        assertTrue(trailer.isTrailer());
        assertFalse(trailer.isPoweredUnit());
    }

    @Test
    void shouldDetectNotes() {
        Vehicle vehicle = Vehicle.cargoVehicle(
                "TRUCK-001",
                "AB 123 CD",
                VehicleType.RIGID_TRUCK,
                VehicleStatus.AVAILABLE,
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                Notes.of("Veicolo con sponda idraulica")
        );

        assertTrue(vehicle.hasNotes());
    }

    @Test
    void shouldFormatSingleLine() {
        Vehicle vehicle = standardTruck();

        assertEquals("TRUCK-001 - AB 123 CD - RIGID_TRUCK - AVAILABLE", vehicle.formatSingleLine());
    }

    @Test
    void shouldConsiderEquivalentVehiclesEqual() {
        Vehicle first = standardTruck();
        Vehicle second = standardTruck();

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    private static Vehicle standardTruck() {
        return Vehicle.cargoVehicle(
                "TRUCK-001",
                "AB 123 CD",
                VehicleType.RIGID_TRUCK,
                VehicleStatus.AVAILABLE,
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                Notes.empty()
        );
    }
}
