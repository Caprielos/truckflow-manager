package it.gabriele.truckflow.domain.fleet;

import it.gabriele.truckflow.domain.cargo.CargoCategory;
import it.gabriele.truckflow.domain.cargo.CargoItem;
import it.gabriele.truckflow.domain.cargo.CargoLoad;
import it.gabriele.truckflow.domain.customer.Customer;
import it.gabriele.truckflow.domain.customer.CustomerAccount;
import it.gabriele.truckflow.domain.customer.CustomerContact;
import it.gabriele.truckflow.domain.customer.CustomerContactRole;
import it.gabriele.truckflow.domain.customer.CustomerType;
import it.gabriele.truckflow.domain.facility.Facility;
import it.gabriele.truckflow.domain.facility.FacilityType;
import it.gabriele.truckflow.domain.location.Address;
import it.gabriele.truckflow.domain.location.Location;
import it.gabriele.truckflow.domain.order.TransportOrder;
import it.gabriele.truckflow.domain.order.TransportServiceType;
import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TemperatureRange;
import it.gabriele.truckflow.domain.shared.TimeWindow;
import it.gabriele.truckflow.domain.shared.Weight;
import it.gabriele.truckflow.domain.shipment.Shipment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa VehicleCombinationRules.
 */
class VehicleCombinationRulesTest {

    @Test
    void shouldCheckEnoughWeightCapacity() {
        VehicleCombination combination = standardCombination();

        assertTrue(VehicleCombinationRules.hasEnoughWeightCapacity(
                combination,
                standardCargoLoad()
        ));

        assertFalse(VehicleCombinationRules.hasEnoughWeightCapacity(
                combination,
                tooHeavyCargoLoad()
        ));
    }

    @Test
    void shouldCheckEnoughVolumeCapacity() {
        VehicleCombination combination = standardCombination();

        assertTrue(VehicleCombinationRules.hasEnoughVolumeCapacity(
                combination,
                standardCargoLoad()
        ));

        assertFalse(VehicleCombinationRules.hasEnoughVolumeCapacity(
                combination,
                tooLargeVolumeCargoLoad()
        ));
    }

    @Test
    void shouldCheckEnoughSpaceForEveryItem() {
        VehicleCombination combination = standardCombination();

        assertTrue(VehicleCombinationRules.hasEnoughSpaceForEveryItem(
                combination,
                standardCargoLoad()
        ));

        assertFalse(VehicleCombinationRules.hasEnoughSpaceForEveryItem(
                combination,
                itemTooLongCargoLoad()
        ));
    }

    @Test
    void shouldSupportStandardCargoTemperatureRequirement() {
        VehicleCombination combination = standardCombination();

        assertTrue(VehicleCombinationRules.supportsRequiredTemperature(
                combination,
                standardCargoLoad()
        ));
    }

    @Test
    void shouldDetectMissingTemperatureSupport() {
        VehicleCombination standardCombination = standardCombination();

        assertFalse(VehicleCombinationRules.supportsRequiredTemperature(
                standardCombination,
                refrigeratedCargoLoad()
        ));
    }

    @Test
    void shouldDetectTemperatureSupport() {
        VehicleCombination refrigeratedCombination = refrigeratedCombination();

        assertTrue(VehicleCombinationRules.supportsRequiredTemperature(
                refrigeratedCombination,
                refrigeratedCargoLoad()
        ));
    }

    @Test
    void shouldCheckIfCombinationCanPhysicallyCarryCargoLoad() {
        VehicleCombination combination = standardCombination();

        assertTrue(VehicleCombinationRules.canPhysicallyCarry(
                combination,
                standardCargoLoad()
        ));

        assertFalse(VehicleCombinationRules.canPhysicallyCarry(
                combination,
                tooHeavyCargoLoad()
        ));

        assertFalse(VehicleCombinationRules.canPhysicallyCarry(
                combination,
                itemTooLongCargoLoad()
        ));
    }

    @Test
    void shouldCheckIfCombinationCanBeAssignedToCargoLoad() {
        VehicleCombination availableCombination = standardCombination();
        VehicleCombination unavailableCombination = unavailableCombination();

        assertTrue(VehicleCombinationRules.canBeAssignedToCargoLoad(
                availableCombination,
                standardCargoLoad()
        ));

        assertFalse(VehicleCombinationRules.canBeAssignedToCargoLoad(
                unavailableCombination,
                standardCargoLoad()
        ));
    }

    @Test
    void shouldCheckIfCombinationCanBeAssignedToShipment() {
        VehicleCombination combination = standardCombination();
        Shipment shipment = standardShipment();

        assertTrue(VehicleCombinationRules.canBeAssignedToShipment(
                combination,
                shipment
        ));
    }

    @Test
    void shouldNotAssignIncompatibleCombinationToRefrigeratedShipment() {
        VehicleCombination standardCombination = standardCombination();
        Shipment refrigeratedShipment = refrigeratedShipment();

        assertFalse(VehicleCombinationRules.canBeAssignedToShipment(
                standardCombination,
                refrigeratedShipment
        ));
    }

    @Test
    void shouldAssignRefrigeratedCombinationToRefrigeratedShipment() {
        VehicleCombination refrigeratedCombination = refrigeratedCombination();
        Shipment refrigeratedShipment = refrigeratedShipment();

        assertTrue(VehicleCombinationRules.canBeAssignedToShipment(
                refrigeratedCombination,
                refrigeratedShipment
        ));
    }

    @Test
    void shouldNotAllowNullValues() {
        VehicleCombination combination = standardCombination();
        CargoLoad cargoLoad = standardCargoLoad();
        Shipment shipment = standardShipment();

        assertThrows(IllegalArgumentException.class,
                () -> VehicleCombinationRules.hasEnoughWeightCapacity(null, cargoLoad));

        assertThrows(IllegalArgumentException.class,
                () -> VehicleCombinationRules.hasEnoughWeightCapacity(combination, null));

        assertThrows(IllegalArgumentException.class,
                () -> VehicleCombinationRules.hasEnoughVolumeCapacity(null, cargoLoad));

        assertThrows(IllegalArgumentException.class,
                () -> VehicleCombinationRules.hasEnoughSpaceForEveryItem(null, cargoLoad));

        assertThrows(IllegalArgumentException.class,
                () -> VehicleCombinationRules.supportsRequiredTemperature(null, cargoLoad));

        assertThrows(IllegalArgumentException.class,
                () -> VehicleCombinationRules.canPhysicallyCarry(null, cargoLoad));

        assertThrows(IllegalArgumentException.class,
                () -> VehicleCombinationRules.canBeAssignedToCargoLoad(null, cargoLoad));

        assertThrows(IllegalArgumentException.class,
                () -> VehicleCombinationRules.canBeAssignedToShipment(null, shipment));

        assertThrows(IllegalArgumentException.class,
                () -> VehicleCombinationRules.canBeAssignedToShipment(combination, null));
    }

    private static VehicleCombination standardCombination() {
        return VehicleCombination.withTrailer(
                "COMBO-001",
                tractorUnit(),
                semiTrailer(),
                Notes.empty()
        );
    }

    private static VehicleCombination refrigeratedCombination() {
        return VehicleCombination.withTrailer(
                "COMBO-002",
                tractorUnit(),
                refrigeratedTrailer(),
                Notes.empty()
        );
    }

    private static VehicleCombination unavailableCombination() {
        return VehicleCombination.withTrailer(
                "COMBO-003",
                assignedTractorUnit(),
                semiTrailer(),
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

    private static CargoLoad standardCargoLoad() {
        return CargoLoad.of(CargoItem.of(
                "Merce generale",
                CargoCategory.GENERAL,
                Weight.ofKilograms(10000),
                Dimension.ofMeters(10, 2, 2),
                Notes.empty()
        ));
    }

    private static CargoLoad tooHeavyCargoLoad() {
        return CargoLoad.of(CargoItem.of(
                "Merce pesante",
                CargoCategory.GENERAL,
                Weight.ofKilograms(21000),
                Dimension.ofMeters(10, 2, 2),
                Notes.empty()
        ));
    }

    private static CargoLoad itemTooLongCargoLoad() {
        return CargoLoad.of(CargoItem.of(
                "Merce troppo lunga",
                CargoCategory.OVERSIZED,
                Weight.ofKilograms(5000),
                Dimension.ofMeters(14, 2, 2),
                Notes.empty()
        ));
    }

    private static CargoLoad tooLargeVolumeCargoLoad() {
        CargoItem first = CargoItem.of(
                "Primo collo voluminoso",
                CargoCategory.GENERAL,
                Weight.ofKilograms(4000),
                Dimension.ofMeters(7, 2.4, 2.7),
                Notes.empty()
        );

        CargoItem second = CargoItem.of(
                "Secondo collo voluminoso",
                CargoCategory.GENERAL,
                Weight.ofKilograms(4000),
                Dimension.ofMeters(7, 2.4, 2.7),
                Notes.empty()
        );

        return CargoLoad.of(first, second);
    }

    private static CargoLoad refrigeratedCargoLoad() {
        return CargoLoad.of(CargoItem.temperatureControlled(
                "Latte fresco",
                CargoCategory.REFRIGERATED_FOOD,
                Weight.ofKilograms(5000),
                Dimension.ofMeters(8, 2, 2),
                TemperatureRange.ofCelsius(2, 6),
                Notes.empty()
        ));
    }

    private static Shipment standardShipment() {
        return Shipment.fromAcceptedOrder(
                "SHP-001",
                acceptedStandardOrder(),
                Notes.empty()
        );
    }

    private static Shipment refrigeratedShipment() {
        return Shipment.fromAcceptedOrder(
                "SHP-002",
                acceptedRefrigeratedOrder(),
                Notes.empty()
        );
    }

    private static TransportOrder acceptedStandardOrder() {
        return TransportOrder.draft(
                "ORD-001",
                activeCustomerAccount(),
                standardCargoLoad(),
                pickupFacility(),
                deliveryFacility(),
                TimeWindow.of("08:00", "12:00"),
                TimeWindow.of("14:00", "18:00"),
                TransportServiceType.STANDARD,
                Money.of("1200.00", "EUR"),
                Notes.empty()
        ).submit().accept();
    }

    private static TransportOrder acceptedRefrigeratedOrder() {
        return TransportOrder.draft(
                "ORD-002",
                activeCustomerAccount(),
                refrigeratedCargoLoad(),
                pickupFacility(),
                deliveryFacility(),
                TimeWindow.of("08:00", "12:00"),
                TimeWindow.of("14:00", "18:00"),
                TransportServiceType.REFRIGERATED,
                Money.of("1800.00", "EUR"),
                Notes.empty()
        ).submit().accept();
    }

    private static CustomerAccount activeCustomerAccount() {
        return CustomerAccount.of(
                Customer.active(
                        "CUST-001",
                        "ACME Logistics S.r.l.",
                        CustomerType.COMPANY,
                        customerLocation(),
                        Notes.empty()
                ),
                primaryContact()
        );
    }

    private static CustomerContact primaryContact() {
        return CustomerContact.primary(
                "Mario Rossi",
                CustomerContactRole.LOGISTICS,
                "mario.rossi@example.com",
                "+39 333 1234567",
                Notes.empty()
        );
    }

    private static Location customerLocation() {
        return Location.of(
                "Sede Cliente Milano",
                Address.of("Via Cliente 10", "Milano", "20100", "IT"),
                "Europe/Rome"
        );
    }

    private static Facility pickupFacility() {
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

    private static Facility deliveryFacility() {
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

