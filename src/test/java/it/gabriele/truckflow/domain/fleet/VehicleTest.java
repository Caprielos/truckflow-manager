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
        assertEquals("1HGCM82633A004352", vehicle.getChassisNumber());
        assertEquals(VehicleType.RIGID_TRUCK, vehicle.getType());
        assertEquals(VehicleBodyType.BOX, vehicle.getBodyType());
        assertEquals(VehicleStatus.AVAILABLE, vehicle.getStatus());
        assertEquals(standardTire(), vehicle.getTireSpecification());
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
                "JH4KA8260MC000000",
                VehicleType.TRACTOR_UNIT,
                VehicleStatus.AVAILABLE,
                standardTire(),
                Notes.empty()
        );

        assertEquals("TRACTOR-001", vehicle.getFleetNumber());
        assertEquals("TR 001 AA", vehicle.getLicensePlate());
        assertEquals("JH4KA8260MC000000", vehicle.getChassisNumber());
        assertEquals(VehicleType.TRACTOR_UNIT, vehicle.getType());
        assertEquals(VehicleBodyType.NONE, vehicle.getBodyType());
        assertFalse(vehicle.canCarryCargo());
        assertTrue(vehicle.isPoweredUnit());
    }

    @Test
    void shouldCreateRefrigeratedVehicle() {
        TemperatureRange range = TemperatureRange.ofCelsius(0, 8);

        Vehicle vehicle = Vehicle.cargoVehicle(
                "frigo-001",
                "FR 001 AA",
                "WDB9301231L123456",
                VehicleType.REFRIGERATED_TRUCK,
                VehicleBodyType.REFRIGERATED_BOX,
                VehicleStatus.AVAILABLE,
                standardTire(),
                Weight.ofKilograms(8000),
                Dimension.ofMeters(6, 2.4, 2.4),
                range,
                Notes.empty()
        );

        assertTrue(vehicle.supportsTemperatureControl());
        assertEquals(range, vehicle.getTemperatureRange());
    }

    @Test
    void shouldCreateTankVehicle() {
        Vehicle vehicle = Vehicle.cargoVehicle(
                "TANK-001",
                "TK 001 AA",
                "VF1AAAAA555123456",
                VehicleType.SEMI_TRAILER,
                VehicleBodyType.TANK_FUEL,
                VehicleStatus.AVAILABLE,
                standardTire(),
                Weight.ofKilograms(25000),
                Dimension.ofMeters(12, 2.5, 3.5),
                null,
                Notes.empty()
        );

        assertTrue(vehicle.hasTankBody());
        assertTrue(vehicle.hasFuelTankBody());
        assertFalse(vehicle.hasGasTankBody());
    }

    @Test
    void shouldNormalizeFleetNumberLicensePlateAndChassisNumber() {
        Vehicle vehicle = Vehicle.cargoVehicle(
                "  truck_001  ",
                "  ab 123 cd  ",
                "  1hgcm82633a004352  ",
                VehicleType.RIGID_TRUCK,
                VehicleBodyType.BOX,
                VehicleStatus.AVAILABLE,
                standardTire(),
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                Notes.empty()
        );

        assertEquals("TRUCK_001", vehicle.getFleetNumber());
        assertEquals("AB 123 CD", vehicle.getLicensePlate());
        assertEquals("1HGCM82633A004352", vehicle.getChassisNumber());
    }

    @Test
    void shouldNotAllowInvalidFleetNumber() {
        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                null,
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
        ));

        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "TRUCK 001",
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
        ));
    }

    @Test
    void shouldNotAllowInvalidLicensePlate() {
        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "TRUCK-001",
                null,
                "1HGCM82633A004352",
                VehicleType.RIGID_TRUCK,
                VehicleBodyType.BOX,
                VehicleStatus.AVAILABLE,
                standardTire(),
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "TRUCK-001",
                "AB@123",
                "1HGCM82633A004352",
                VehicleType.RIGID_TRUCK,
                VehicleBodyType.BOX,
                VehicleStatus.AVAILABLE,
                standardTire(),
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowInvalidChassisNumber() {
        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "TRUCK-001",
                "AB 123 CD",
                null,
                VehicleType.RIGID_TRUCK,
                VehicleBodyType.BOX,
                VehicleStatus.AVAILABLE,
                standardTire(),
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "TRUCK-001",
                "AB 123 CD",
                "123",
                VehicleType.RIGID_TRUCK,
                VehicleBodyType.BOX,
                VehicleStatus.AVAILABLE,
                standardTire(),
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "TRUCK-001",
                "AB 123 CD",
                "1HGCM82633I004352",
                VehicleType.RIGID_TRUCK,
                VehicleBodyType.BOX,
                VehicleStatus.AVAILABLE,
                standardTire(),
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
                "1HGCM82633A004352",
                null,
                VehicleBodyType.BOX,
                VehicleStatus.AVAILABLE,
                standardTire(),
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "TRUCK-001",
                "AB 123 CD",
                "1HGCM82633A004352",
                VehicleType.RIGID_TRUCK,
                null,
                VehicleStatus.AVAILABLE,
                standardTire(),
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "TRUCK-001",
                "AB 123 CD",
                "1HGCM82633A004352",
                VehicleType.RIGID_TRUCK,
                VehicleBodyType.BOX,
                null,
                standardTire(),
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "TRUCK-001",
                "AB 123 CD",
                "1HGCM82633A004352",
                VehicleType.RIGID_TRUCK,
                VehicleBodyType.BOX,
                VehicleStatus.AVAILABLE,
                null,
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                Notes.empty()
        ));
    }

    @Test
    void shouldRequireCargoBodyForCargoVehicle() {
        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "TRUCK-001",
                "AB 123 CD",
                "1HGCM82633A004352",
                VehicleType.RIGID_TRUCK,
                VehicleBodyType.NONE,
                VehicleStatus.AVAILABLE,
                standardTire(),
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowCargoBodyForNonCargoVehicle() {
        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "TRACTOR-001",
                "TR 001 AA",
                "JH4KA8260MC000000",
                VehicleType.TRACTOR_UNIT,
                VehicleBodyType.BOX,
                VehicleStatus.AVAILABLE,
                standardTire(),
                null,
                null,
                null,
                Notes.empty()
        ));
    }

    @Test
    void shouldRequireCargoDataForCargoVehicle() {
        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "TRUCK-001",
                "AB 123 CD",
                "1HGCM82633A004352",
                VehicleType.RIGID_TRUCK,
                VehicleBodyType.BOX,
                VehicleStatus.AVAILABLE,
                standardTire(),
                null,
                Dimension.ofMeters(7, 2.4, 2.5),
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "TRUCK-001",
                "AB 123 CD",
                "1HGCM82633A004352",
                VehicleType.RIGID_TRUCK,
                VehicleBodyType.BOX,
                VehicleStatus.AVAILABLE,
                standardTire(),
                Weight.ofKilograms(12000),
                null,
                null,
                Notes.empty()
        ));
    }

    @Test
    void shouldRequireTemperatureRangeForRefrigeratedVehicle() {
        assertThrows(IllegalArgumentException.class, () -> Vehicle.cargoVehicle(
                "FRIGO-001",
                "FR 001 AA",
                "WDB9301231L123456",
                VehicleType.REFRIGERATED_TRUCK,
                VehicleBodyType.REFRIGERATED_BOX,
                VehicleStatus.AVAILABLE,
                standardTire(),
                Weight.ofKilograms(8000),
                Dimension.ofMeters(6, 2.4, 2.4),
                null,
                Notes.empty()
        ));
    }

    @Test
    void shouldAllowRefrigeratedBodyForRigidTruckWhenTemperatureRangeIsProvided() {
        Vehicle vehicle = Vehicle.cargoVehicle(
                "TRUCK-001",
                "AB 123 CD",
                "1HGCM82633A004352",
                VehicleType.RIGID_TRUCK,
                VehicleBodyType.REFRIGERATED_BOX,
                VehicleStatus.AVAILABLE,
                standardTire(),
                Weight.ofKilograms(12000),
                Dimension.ofMeters(7, 2.4, 2.5),
                TemperatureRange.ofCelsius(0, 8),
                Notes.empty()
        );

        assertTrue(vehicle.supportsTemperatureControl());
        assertEquals(TemperatureRange.ofCelsius(0, 8), vehicle.getTemperatureRange());
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
                "JH4KA8260MC000000",
                VehicleType.TRACTOR_UNIT,
                VehicleStatus.AVAILABLE,
                standardTire(),
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
                "WDB9301231L123456",
                VehicleType.REFRIGERATED_TRUCK,
                VehicleBodyType.REFRIGERATED_BOX,
                VehicleStatus.AVAILABLE,
                standardTire(),
                Weight.ofKilograms(8000),
                Dimension.ofMeters(6, 2.4, 2.4),
                TemperatureRange.ofCelsius(0, 8),
                Notes.empty()
        );

        assertTrue(vehicle.canSupportTemperatureRange(TemperatureRange.ofCelsius(2, 6)));
        assertFalse(vehicle.canSupportTemperatureRange(TemperatureRange.ofCelsius(-2, 6)));
    }

    @Test
    void shouldDetectTrailer() {
        Vehicle trailer = semiTrailer();

        assertTrue(trailer.isTrailer());
        assertFalse(trailer.isPoweredUnit());
    }

    @Test
    void shouldDetectNotes() {
        Vehicle vehicle = Vehicle.cargoVehicle(
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
                Notes.of("Veicolo con sponda idraulica")
        );

        assertTrue(vehicle.hasNotes());
    }

    @Test
    void shouldFormatSingleLine() {
        Vehicle vehicle = standardTruck();

        assertEquals(
                "TRUCK-001 - AB 123 CD - 1HGCM82633A004352 - RIGID_TRUCK - BOX - AVAILABLE",
                vehicle.formatSingleLine()
        );
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
