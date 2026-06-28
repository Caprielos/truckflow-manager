package it.gabriele.truckflow.domain.driver;

import it.gabriele.truckflow.domain.cargo.AdrClass;
import it.gabriele.truckflow.domain.cargo.CargoCategory;
import it.gabriele.truckflow.domain.cargo.CargoItem;
import it.gabriele.truckflow.domain.cargo.CargoLoad;
import it.gabriele.truckflow.domain.cargo.DangerousGoodsProfile;
import it.gabriele.truckflow.domain.cargo.HazardLabel;
import it.gabriele.truckflow.domain.cargo.PackingGroup;
import it.gabriele.truckflow.domain.customer.Customer;
import it.gabriele.truckflow.domain.customer.CustomerAccount;
import it.gabriele.truckflow.domain.customer.CustomerContact;
import it.gabriele.truckflow.domain.customer.CustomerContactRole;
import it.gabriele.truckflow.domain.customer.CustomerType;
import it.gabriele.truckflow.domain.facility.Facility;
import it.gabriele.truckflow.domain.facility.FacilityType;
import it.gabriele.truckflow.domain.fleet.TireSpecification;
import it.gabriele.truckflow.domain.fleet.Vehicle;
import it.gabriele.truckflow.domain.fleet.VehicleBodyType;
import it.gabriele.truckflow.domain.fleet.VehicleCombination;
import it.gabriele.truckflow.domain.fleet.VehicleStatus;
import it.gabriele.truckflow.domain.fleet.VehicleType;
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

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa DriverRules.
 */
class DriverRulesTest {

    @Test
    void shouldCheckIfDriverCanBeAssigned() {
        assertTrue(DriverRules.canBeAssigned(standardDriver()));

        assertFalse(DriverRules.canBeAssigned(assignedDriver()));
    }

    @Test
    void shouldAllowDriverWithBToDriveVan() {
        Driver driver = Driver.available(
                "DRV-001",
                "Mario Rossi",
                Set.of(DriverLicenseCategory.B),
                Set.of(DriverProfessionalQualification.CQC_GOODS),
                Set.of(),
                Set.of(),
                Notes.empty()
        );

        assertTrue(DriverRules.hasRequiredLicenseForVehicleCombination(
                driver,
                singleVanCombination()
        ));
    }

    @Test
    void shouldAllowDriverWithCToDriveRigidTruck() {
        Driver driver = Driver.available(
                "DRV-001",
                "Mario Rossi",
                Set.of(DriverLicenseCategory.C),
                Set.of(DriverProfessionalQualification.CQC_GOODS),
                Set.of(),
                Set.of(),
                Notes.empty()
        );

        assertTrue(DriverRules.hasRequiredLicenseForVehicleCombination(
                driver,
                singleRigidTruckCombination()
        ));
    }

    @Test
    void shouldRequireCAndEForVehicleCombinationWithTrailer() {
        Driver driverWithOnlyC = Driver.available(
                "DRV-001",
                "Mario Rossi",
                Set.of(DriverLicenseCategory.C),
                Set.of(DriverProfessionalQualification.CQC_GOODS),
                Set.of(),
                Set.of(),
                Notes.empty()
        );

        Driver driverWithCAndE = standardDriver();

        assertFalse(DriverRules.hasRequiredLicenseForVehicleCombination(
                driverWithOnlyC,
                trailerCombination()
        ));

        assertTrue(DriverRules.hasRequiredLicenseForVehicleCombination(
                driverWithCAndE,
                trailerCombination()
        ));
    }

    @Test
    void shouldRequireGoodsCqcForProfessionalGoodsTransport() {
        Driver driverWithoutCqc = Driver.available(
                "DRV-001",
                "Mario Rossi",
                Set.of(DriverLicenseCategory.C, DriverLicenseCategory.E),
                Set.of(),
                Set.of(),
                Set.of(),
                Notes.empty()
        );

        assertFalse(DriverRules.hasRequiredProfessionalQualificationForGoodsTransport(driverWithoutCqc));
        assertTrue(DriverRules.hasRequiredProfessionalQualificationForGoodsTransport(standardDriver()));
    }

    @Test
    void shouldCheckIfDriverCanDriveVehicleCombination() {
        assertTrue(DriverRules.canDriveVehicleCombination(
                standardDriver(),
                trailerCombination()
        ));

        assertFalse(DriverRules.canDriveVehicleCombination(
                assignedDriver(),
                trailerCombination()
        ));

        assertFalse(DriverRules.canDriveVehicleCombination(
                driverWithoutCqc(),
                trailerCombination()
        ));
    }

    @Test
    void shouldRequireAdrBasicForHazardousCargo() {
        CargoLoad cargoLoad = CargoLoad.of(CargoItem.dangerousGoods(
                "Vernice infiammabile",
                CargoCategory.HAZARDOUS_MATERIAL,
                Weight.ofKilograms(1000),
                Dimension.ofMeters(2, 1, 1),
                packagedFlammableLiquidProfile(),
                Notes.empty()
        ));

        assertTrue(DriverRules.hasRequiredAdrCertificatesForCargoLoad(
                standardDriver(),
                cargoLoad
        ));

        assertFalse(DriverRules.hasRequiredAdrCertificatesForCargoLoad(
                driverWithoutAdr(),
                cargoLoad
        ));
    }

    @Test
    void shouldRequireAdrTankForTankCargo() {
        CargoLoad cargoLoad = CargoLoad.of(CargoItem.dangerousGoods(
                "Benzina in cisterna",
                CargoCategory.HAZARDOUS_MATERIAL,
                Weight.ofKilograms(10000),
                Dimension.ofMeters(10, 2, 2),
                gasolineProfile(),
                Notes.empty()
        ));

        assertTrue(DriverRules.hasRequiredAdrCertificatesForCargoLoad(
                standardDriver(),
                cargoLoad
        ));

        assertFalse(DriverRules.hasRequiredAdrCertificatesForCargoLoad(
                driverWithAdrBasicOnly(),
                cargoLoad
        ));
    }

    @Test
    void shouldRequireExplosivesAdrCertificateForExplosives() {
        CargoLoad cargoLoad = CargoLoad.of(CargoItem.dangerousGoods(
                "Fuochi d'artificio",
                CargoCategory.HAZARDOUS_MATERIAL,
                Weight.ofKilograms(100),
                Dimension.ofMeters(1, 1, 1),
                explosiveProfile(),
                Notes.empty()
        ));

        assertTrue(DriverRules.hasRequiredAdrCertificatesForCargoLoad(
                driverWithExplosivesAdr(),
                cargoLoad
        ));

        assertFalse(DriverRules.hasRequiredAdrCertificatesForCargoLoad(
                standardDriver(),
                cargoLoad
        ));
    }

    @Test
    void shouldRequireRadioactiveAdrCertificateForRadioactiveMaterial() {
        CargoLoad cargoLoad = CargoLoad.of(CargoItem.dangerousGoods(
                "Materiale radioattivo",
                CargoCategory.HAZARDOUS_MATERIAL,
                Weight.ofKilograms(100),
                Dimension.ofMeters(1, 1, 1),
                radioactiveProfile(),
                Notes.empty()
        ));

        assertTrue(DriverRules.hasRequiredAdrCertificatesForCargoLoad(
                driverWithRadioactiveAdr(),
                cargoLoad
        ));

        assertFalse(DriverRules.hasRequiredAdrCertificatesForCargoLoad(
                standardDriver(),
                cargoLoad
        ));
    }

    @Test
    void shouldRequireTemperatureQualificationForRefrigeratedShipment() {
        assertTrue(DriverRules.hasRequiredOperationalQualificationsForShipment(
                standardDriver(),
                refrigeratedShipment()
        ));

        assertFalse(DriverRules.hasRequiredOperationalQualificationsForShipment(
                driverWithoutTemperatureQualification(),
                refrigeratedShipment()
        ));
    }

    @Test
    void shouldRequireInternationalQualificationForInternationalShipment() {
        assertTrue(DriverRules.hasRequiredOperationalQualificationsForShipment(
                standardDriver(),
                internationalShipment()
        ));

        assertFalse(DriverRules.hasRequiredOperationalQualificationsForShipment(
                driverWithoutInternationalQualification(),
                internationalShipment()
        ));
    }

    @Test
    void shouldCheckIfDriverCanBeAssignedToStandardShipment() {
        assertTrue(DriverRules.canBeAssignedToShipment(
                standardDriver(),
                trailerCombination(),
                standardShipment()
        ));
    }

    @Test
    void shouldNotAssignDriverWithoutAdrToAdrShipment() {
        assertFalse(DriverRules.canBeAssignedToShipment(
                driverWithoutAdr(),
                trailerCombination(),
                adrTankShipment()
        ));
    }

    @Test
    void shouldAssignQualifiedDriverToAdrTankShipment() {
        assertTrue(DriverRules.canBeAssignedToShipment(
                standardDriver(),
                trailerCombination(),
                adrTankShipment()
        ));
    }

    @Test
    void shouldNotAllowNullValues() {
        Driver driver = standardDriver();
        VehicleCombination combination = trailerCombination();
        Shipment shipment = standardShipment();
        CargoLoad cargoLoad = standardCargoLoad();

        assertThrows(IllegalArgumentException.class, () -> DriverRules.canBeAssigned(null));

        assertThrows(IllegalArgumentException.class,
                () -> DriverRules.hasRequiredLicenseForVehicleCombination(null, combination));

        assertThrows(IllegalArgumentException.class,
                () -> DriverRules.hasRequiredLicenseForVehicleCombination(driver, null));

        assertThrows(IllegalArgumentException.class,
                () -> DriverRules.hasRequiredAdrCertificatesForCargoLoad(null, cargoLoad));

        assertThrows(IllegalArgumentException.class,
                () -> DriverRules.hasRequiredAdrCertificatesForCargoLoad(driver, null));

        assertThrows(IllegalArgumentException.class,
                () -> DriverRules.hasRequiredOperationalQualificationsForShipment(null, shipment));

        assertThrows(IllegalArgumentException.class,
                () -> DriverRules.hasRequiredOperationalQualificationsForShipment(driver, null));

        assertThrows(IllegalArgumentException.class,
                () -> DriverRules.canDriveVehicleCombination(null, combination));

        assertThrows(IllegalArgumentException.class,
                () -> DriverRules.canBeAssignedToShipment(null, combination, shipment));

        assertThrows(IllegalArgumentException.class,
                () -> DriverRules.canBeAssignedToShipment(driver, null, shipment));

        assertThrows(IllegalArgumentException.class,
                () -> DriverRules.canBeAssignedToShipment(driver, combination, null));
    }

    private static Driver standardDriver() {
        return Driver.available(
                "DRV-001",
                "Mario Rossi",
                Set.of(DriverLicenseCategory.C, DriverLicenseCategory.E),
                Set.of(DriverProfessionalQualification.CQC_GOODS),
                Set.of(DriverAdrCertificateType.ADR_BASIC, DriverAdrCertificateType.ADR_TANK),
                Set.of(
                        DriverOperationalQualification.INTERNATIONAL_TRANSPORT,
                        DriverOperationalQualification.TEMPERATURE_CONTROLLED_TRANSPORT
                ),
                Notes.empty()
        );
    }

    private static Driver assignedDriver() {
        return Driver.assigned(
                "DRV-001",
                "Mario Rossi",
                Set.of(DriverLicenseCategory.C, DriverLicenseCategory.E),
                Set.of(DriverProfessionalQualification.CQC_GOODS),
                Set.of(DriverAdrCertificateType.ADR_BASIC, DriverAdrCertificateType.ADR_TANK),
                Set.of(
                        DriverOperationalQualification.INTERNATIONAL_TRANSPORT,
                        DriverOperationalQualification.TEMPERATURE_CONTROLLED_TRANSPORT
                ),
                Notes.empty()
        );
    }

    private static Driver driverWithoutCqc() {
        return Driver.available(
                "DRV-002",
                "Luigi Bianchi",
                Set.of(DriverLicenseCategory.C, DriverLicenseCategory.E),
                Set.of(),
                Set.of(DriverAdrCertificateType.ADR_BASIC, DriverAdrCertificateType.ADR_TANK),
                Set.of(
                        DriverOperationalQualification.INTERNATIONAL_TRANSPORT,
                        DriverOperationalQualification.TEMPERATURE_CONTROLLED_TRANSPORT
                ),
                Notes.empty()
        );
    }

    private static Driver driverWithoutAdr() {
        return Driver.available(
                "DRV-003",
                "Giuseppe Verdi",
                Set.of(DriverLicenseCategory.C, DriverLicenseCategory.E),
                Set.of(DriverProfessionalQualification.CQC_GOODS),
                Set.of(),
                Set.of(
                        DriverOperationalQualification.INTERNATIONAL_TRANSPORT,
                        DriverOperationalQualification.TEMPERATURE_CONTROLLED_TRANSPORT
                ),
                Notes.empty()
        );
    }

    private static Driver driverWithAdrBasicOnly() {
        return Driver.available(
                "DRV-004",
                "Paolo Neri",
                Set.of(DriverLicenseCategory.C, DriverLicenseCategory.E),
                Set.of(DriverProfessionalQualification.CQC_GOODS),
                Set.of(DriverAdrCertificateType.ADR_BASIC),
                Set.of(
                        DriverOperationalQualification.INTERNATIONAL_TRANSPORT,
                        DriverOperationalQualification.TEMPERATURE_CONTROLLED_TRANSPORT
                ),
                Notes.empty()
        );
    }

    private static Driver driverWithExplosivesAdr() {
        return Driver.available(
                "DRV-005",
                "Marco Gialli",
                Set.of(DriverLicenseCategory.C, DriverLicenseCategory.E),
                Set.of(DriverProfessionalQualification.CQC_GOODS),
                Set.of(
                        DriverAdrCertificateType.ADR_BASIC,
                        DriverAdrCertificateType.ADR_CLASS_1_EXPLOSIVES
                ),
                Set.of(
                        DriverOperationalQualification.INTERNATIONAL_TRANSPORT,
                        DriverOperationalQualification.TEMPERATURE_CONTROLLED_TRANSPORT
                ),
                Notes.empty()
        );
    }

    private static Driver driverWithRadioactiveAdr() {
        return Driver.available(
                "DRV-006",
                "Andrea Blu",
                Set.of(DriverLicenseCategory.C, DriverLicenseCategory.E),
                Set.of(DriverProfessionalQualification.CQC_GOODS),
                Set.of(
                        DriverAdrCertificateType.ADR_BASIC,
                        DriverAdrCertificateType.ADR_CLASS_7_RADIOACTIVE
                ),
                Set.of(
                        DriverOperationalQualification.INTERNATIONAL_TRANSPORT,
                        DriverOperationalQualification.TEMPERATURE_CONTROLLED_TRANSPORT
                ),
                Notes.empty()
        );
    }

    private static Driver driverWithoutTemperatureQualification() {
        return Driver.available(
                "DRV-007",
                "Franco Viola",
                Set.of(DriverLicenseCategory.C, DriverLicenseCategory.E),
                Set.of(DriverProfessionalQualification.CQC_GOODS),
                Set.of(DriverAdrCertificateType.ADR_BASIC, DriverAdrCertificateType.ADR_TANK),
                Set.of(DriverOperationalQualification.INTERNATIONAL_TRANSPORT),
                Notes.empty()
        );
    }

    private static Driver driverWithoutInternationalQualification() {
        return Driver.available(
                "DRV-008",
                "Davide Rosa",
                Set.of(DriverLicenseCategory.C, DriverLicenseCategory.E),
                Set.of(DriverProfessionalQualification.CQC_GOODS),
                Set.of(DriverAdrCertificateType.ADR_BASIC, DriverAdrCertificateType.ADR_TANK),
                Set.of(DriverOperationalQualification.TEMPERATURE_CONTROLLED_TRANSPORT),
                Notes.empty()
        );
    }

    private static VehicleCombination singleVanCombination() {
        return VehicleCombination.singleVehicle(
                "COMBO-VAN",
                Vehicle.cargoVehicle(
                        "VAN-001",
                        "VN 001 AA",
                        "VF1VANAA555123456",
                        VehicleType.VAN,
                        VehicleBodyType.VAN_BODY,
                        VehicleStatus.AVAILABLE,
                        standardTire(),
                        Weight.ofKilograms(1000),
                        Dimension.ofMeters(3, 1.7, 1.7),
                        null,
                        Notes.empty()
                ),
                Notes.empty()
        );
    }

    private static VehicleCombination singleRigidTruckCombination() {
        return VehicleCombination.singleVehicle(
                "COMBO-TRUCK",
                Vehicle.cargoVehicle(
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
                ),
                Notes.empty()
        );
    }

    private static VehicleCombination trailerCombination() {
        return VehicleCombination.withTrailer(
                "COMBO-001",
                tractorUnit(),
                curtainSideTrailer(),
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

    private static Vehicle curtainSideTrailer() {
        return Vehicle.cargoVehicle(
                "TRAILER-001",
                "TRL 001",
                "VF1BBBBB555123456",
                VehicleType.SEMI_TRAILER,
                VehicleBodyType.CURTAIN_SIDE,
                VehicleStatus.AVAILABLE,
                standardTire(),
                Weight.ofKilograms(25000),
                Dimension.ofMeters(13.6, 2.5, 3.2),
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

    private static Shipment internationalShipment() {
        return Shipment.fromAcceptedOrder(
                "SHP-003",
                acceptedInternationalOrder(),
                Notes.empty()
        );
    }

    private static Shipment adrTankShipment() {
        return Shipment.fromAcceptedOrder(
                "SHP-004",
                acceptedAdrTankOrder(),
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

    private static TransportOrder acceptedInternationalOrder() {
        return TransportOrder.draft(
                "ORD-003",
                activeCustomerAccount(),
                standardCargoLoad(),
                pickupFacility(),
                frenchDeliveryFacility(),
                TimeWindow.of("08:00", "12:00"),
                TimeWindow.of("14:00", "18:00"),
                TransportServiceType.STANDARD,
                Money.of("1600.00", "EUR"),
                Notes.empty()
        ).submit().accept();
    }

    private static TransportOrder acceptedAdrTankOrder() {
        return TransportOrder.draft(
                "ORD-004",
                activeCustomerAccount(),
                adrTankCargoLoad(),
                pickupFacility(),
                deliveryFacility(),
                TimeWindow.of("08:00", "12:00"),
                TimeWindow.of("14:00", "18:00"),
                TransportServiceType.HAZARDOUS,
                Money.of("2200.00", "EUR"),
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

    private static CargoLoad standardCargoLoad() {
        return CargoLoad.of(CargoItem.of(
                "Merce generale",
                CargoCategory.GENERAL,
                Weight.ofKilograms(5000),
                Dimension.ofMeters(8, 2, 2),
                Notes.empty()
        ));
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

    private static CargoLoad adrTankCargoLoad() {
        return CargoLoad.of(CargoItem.dangerousGoods(
                "Benzina in cisterna",
                CargoCategory.HAZARDOUS_MATERIAL,
                Weight.ofKilograms(10000),
                Dimension.ofMeters(10, 2, 2),
                gasolineProfile(),
                Notes.empty()
        ));
    }

    private static DangerousGoodsProfile gasolineProfile() {
        return DangerousGoodsProfile.of(
                "UN 1203",
                "Gasoline",
                AdrClass.CLASS_3_FLAMMABLE_LIQUIDS,
                "F1",
                PackingGroup.II,
                Set.of(HazardLabel.LABEL_3_FLAMMABLE_LIQUID),
                "D/E",
                2,
                true
        );
    }

    private static DangerousGoodsProfile packagedFlammableLiquidProfile() {
        return DangerousGoodsProfile.of(
                "UN 1263",
                "Paint",
                AdrClass.CLASS_3_FLAMMABLE_LIQUIDS,
                "F1",
                PackingGroup.III,
                Set.of(HazardLabel.LABEL_3_FLAMMABLE_LIQUID),
                "D/E",
                3,
                false
        );
    }

    private static DangerousGoodsProfile explosiveProfile() {
        return DangerousGoodsProfile.of(
                "UN 0336",
                "Fireworks",
                AdrClass.CLASS_1_EXPLOSIVES,
                "1.4G",
                null,
                Set.of(HazardLabel.LABEL_1_EXPLOSIVES),
                "E",
                2,
                false
        );
    }

    private static DangerousGoodsProfile radioactiveProfile() {
        return DangerousGoodsProfile.of(
                "UN 2915",
                "Radioactive material, type A package",
                AdrClass.CLASS_7_RADIOACTIVE_MATERIAL,
                "",
                null,
                Set.of(HazardLabel.LABEL_7_RADIOACTIVE),
                "E",
                2,
                false
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

    private static Facility frenchDeliveryFacility() {
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
