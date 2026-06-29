package it.gabriele.truckflow.domain.compliance;

import static org.junit.jupiter.api.Assertions.*;

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
import it.gabriele.truckflow.domain.driver.Driver;
import it.gabriele.truckflow.domain.driver.DriverAdrCertificateType;
import it.gabriele.truckflow.domain.driver.DriverLicenseCategory;
import it.gabriele.truckflow.domain.driver.DriverOperationalQualification;
import it.gabriele.truckflow.domain.driver.DriverProfessionalQualification;
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
import it.gabriele.truckflow.domain.route.RoutePlan;
import it.gabriele.truckflow.domain.route.RouteStop;
import it.gabriele.truckflow.domain.route.RouteStopType;
import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Distance;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TemperatureRange;
import it.gabriele.truckflow.domain.shared.TimeWindow;
import it.gabriele.truckflow.domain.shared.Weight;
import it.gabriele.truckflow.domain.shipment.Shipment;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

/** Testa ComplianceRules. */
class ComplianceRulesTest {

  @Test
  void shouldApproveCompliantStandardAssignment() {
    assertTrue(
        ComplianceRules.isAssignmentCompliant(
            standardDriver(), curtainSideCombination(), standardRoutePlan(), standardShipment()));
  }

  @Test
  void shouldRejectVehicleWithoutEnoughBodyCompatibility() {
    assertFalse(
        ComplianceRules.isVehicleCompliantForShipment(flatbedCombination(), fragileShipment()));
  }

  @Test
  void shouldApproveAdrTankShipmentWithFuelTankAndQualifiedDriver() {
    assertTrue(
        ComplianceRules.isAssignmentCompliant(
            standardDriver(), fuelTankCombination(), standardRoutePlan(), adrTankShipment()));
  }

  @Test
  void shouldRejectAdrTankShipmentWithWrongBodyType() {
    assertFalse(
        ComplianceRules.isVehicleCompliantForShipment(curtainSideCombination(), adrTankShipment()));
  }

  @Test
  void shouldRejectAdrTankShipmentWithDriverWithoutAdrTank() {
    assertFalse(
        ComplianceRules.isDriverCompliantForShipment(
            driverWithAdrBasicOnly(), fuelTankCombination(), adrTankShipment()));
  }

  @Test
  void shouldApproveRefrigeratedShipmentWithRefrigeratedCombinationAndQualifiedDriver() {
    assertTrue(
        ComplianceRules.isAssignmentCompliant(
            standardDriver(),
            refrigeratedCombination(),
            standardRoutePlan(),
            refrigeratedShipment()));
  }

  @Test
  void shouldRejectRefrigeratedShipmentWithNonRefrigeratedBody() {
    assertFalse(
        ComplianceRules.isVehicleCompliantForShipment(
            curtainSideCombination(), refrigeratedShipment()));
  }

  @Test
  void shouldRejectRefrigeratedShipmentWithDriverWithoutTemperatureQualification() {
    assertFalse(
        ComplianceRules.isDriverCompliantForShipment(
            driverWithoutTemperatureQualification(),
            refrigeratedCombination(),
            refrigeratedShipment()));
  }

  @Test
  void shouldRejectRouteWithoutMatchingPickupFacility() {
    RoutePlan wrongRoute =
        RoutePlan.of(
            "RTE-WRONG",
            List.of(
                RouteStop.of(
                    1,
                    RouteStopType.START,
                    wrongPickupFacility(),
                    TimeWindow.of("07:00", "08:00"),
                    Notes.empty()),
                RouteStop.of(
                    2,
                    RouteStopType.PICKUP,
                    wrongPickupFacility(),
                    TimeWindow.of("08:00", "09:00"),
                    Notes.empty()),
                RouteStop.of(
                    3,
                    RouteStopType.DELIVERY,
                    deliveryFacility(),
                    TimeWindow.of("14:00", "15:00"),
                    Notes.empty()),
                RouteStop.of(
                    4,
                    RouteStopType.END,
                    deliveryFacility(),
                    TimeWindow.of("16:00", "17:00"),
                    Notes.empty())),
            Distance.ofKilometers(580),
            Notes.empty());

    assertFalse(ComplianceRules.isRouteCompliantForShipment(wrongRoute, standardShipment()));
  }

  @Test
  void shouldRejectRouteWithoutMatchingDeliveryFacility() {
    RoutePlan wrongRoute =
        RoutePlan.of(
            "RTE-WRONG",
            List.of(
                RouteStop.of(
                    1,
                    RouteStopType.START,
                    pickupFacility(),
                    TimeWindow.of("07:00", "08:00"),
                    Notes.empty()),
                RouteStop.of(
                    2,
                    RouteStopType.PICKUP,
                    pickupFacility(),
                    TimeWindow.of("08:00", "09:00"),
                    Notes.empty()),
                RouteStop.of(
                    3,
                    RouteStopType.DELIVERY,
                    wrongDeliveryFacility(),
                    TimeWindow.of("14:00", "15:00"),
                    Notes.empty()),
                RouteStop.of(
                    4,
                    RouteStopType.END,
                    wrongDeliveryFacility(),
                    TimeWindow.of("16:00", "17:00"),
                    Notes.empty())),
            Distance.ofKilometers(580),
            Notes.empty());

    assertFalse(ComplianceRules.isRouteCompliantForShipment(wrongRoute, standardShipment()));
  }

  @Test
  void shouldDetectSpecialComplianceChecks() {
    assertFalse(ComplianceRules.requiresSpecialComplianceChecks(standardShipment()));
    assertTrue(ComplianceRules.requiresSpecialComplianceChecks(refrigeratedShipment()));
    assertTrue(ComplianceRules.requiresSpecialComplianceChecks(adrTankShipment()));
    assertTrue(ComplianceRules.requiresSpecialComplianceChecks(internationalShipment()));
  }

  @Test
  void shouldNotAllowNullValues() {
    Driver driver = standardDriver();
    VehicleCombination combination = curtainSideCombination();
    RoutePlan routePlan = standardRoutePlan();
    Shipment shipment = standardShipment();

    assertThrows(
        IllegalArgumentException.class,
        () -> ComplianceRules.isVehicleCompliantForShipment(null, shipment));

    assertThrows(
        IllegalArgumentException.class,
        () -> ComplianceRules.isVehicleCompliantForShipment(combination, null));

    assertThrows(
        IllegalArgumentException.class,
        () -> ComplianceRules.isDriverCompliantForShipment(null, combination, shipment));

    assertThrows(
        IllegalArgumentException.class,
        () -> ComplianceRules.isDriverCompliantForShipment(driver, null, shipment));

    assertThrows(
        IllegalArgumentException.class,
        () -> ComplianceRules.isDriverCompliantForShipment(driver, combination, null));

    assertThrows(
        IllegalArgumentException.class,
        () -> ComplianceRules.isRouteCompliantForShipment(null, shipment));

    assertThrows(
        IllegalArgumentException.class,
        () -> ComplianceRules.isRouteCompliantForShipment(routePlan, null));

    assertThrows(
        IllegalArgumentException.class,
        () -> ComplianceRules.isAssignmentCompliant(null, combination, routePlan, shipment));

    assertThrows(
        IllegalArgumentException.class,
        () -> ComplianceRules.isAssignmentCompliant(driver, null, routePlan, shipment));

    assertThrows(
        IllegalArgumentException.class,
        () -> ComplianceRules.isAssignmentCompliant(driver, combination, null, shipment));

    assertThrows(
        IllegalArgumentException.class,
        () -> ComplianceRules.isAssignmentCompliant(driver, combination, routePlan, null));

    assertThrows(
        IllegalArgumentException.class,
        () -> ComplianceRules.requiresSpecialComplianceChecks(null));
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
            DriverOperationalQualification.TEMPERATURE_CONTROLLED_TRANSPORT),
        Notes.empty());
  }

  private static Driver driverWithAdrBasicOnly() {
    return Driver.available(
        "DRV-002",
        "Luigi Bianchi",
        Set.of(DriverLicenseCategory.C, DriverLicenseCategory.E),
        Set.of(DriverProfessionalQualification.CQC_GOODS),
        Set.of(DriverAdrCertificateType.ADR_BASIC),
        Set.of(
            DriverOperationalQualification.INTERNATIONAL_TRANSPORT,
            DriverOperationalQualification.TEMPERATURE_CONTROLLED_TRANSPORT),
        Notes.empty());
  }

  private static Driver driverWithoutTemperatureQualification() {
    return Driver.available(
        "DRV-003",
        "Giuseppe Verdi",
        Set.of(DriverLicenseCategory.C, DriverLicenseCategory.E),
        Set.of(DriverProfessionalQualification.CQC_GOODS),
        Set.of(DriverAdrCertificateType.ADR_BASIC, DriverAdrCertificateType.ADR_TANK),
        Set.of(DriverOperationalQualification.INTERNATIONAL_TRANSPORT),
        Notes.empty());
  }

  private static VehicleCombination curtainSideCombination() {
    return VehicleCombination.withTrailer(
        "COMBO-CURTAIN",
        tractorUnit(),
        trailer("TRAILER-CURTAIN", "VF1BBBBB555123456", VehicleBodyType.CURTAIN_SIDE),
        Notes.empty());
  }

  private static VehicleCombination flatbedCombination() {
    return VehicleCombination.withTrailer(
        "COMBO-FLATBED",
        tractorUnit(),
        trailer("TRAILER-FLATBED", "VF1DDDDD555123456", VehicleBodyType.FLATBED),
        Notes.empty());
  }

  private static VehicleCombination fuelTankCombination() {
    return VehicleCombination.withTrailer(
        "COMBO-FUEL",
        tractorUnit(),
        trailer("TRAILER-FUEL", "VF1EEEEE555123456", VehicleBodyType.TANK_FUEL),
        Notes.empty());
  }

  private static VehicleCombination refrigeratedCombination() {
    return VehicleCombination.withTrailer(
        "COMBO-FRIGO",
        tractorUnit(),
        Vehicle.cargoVehicle(
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
            Notes.empty()),
        Notes.empty());
  }

  private static Vehicle tractorUnit() {
    return Vehicle.nonCargoVehicle(
        "TRACTOR-001",
        "TR 001 AA",
        "JH4KA8260MC000000",
        VehicleType.TRACTOR_UNIT,
        VehicleStatus.AVAILABLE,
        standardTire(),
        Notes.empty());
  }

  private static Vehicle trailer(
      String fleetNumber, String chassisNumber, VehicleBodyType bodyType) {
    return Vehicle.cargoVehicle(
        fleetNumber,
        "TRL 001",
        chassisNumber,
        VehicleType.SEMI_TRAILER,
        bodyType,
        VehicleStatus.AVAILABLE,
        standardTire(),
        Weight.ofKilograms(25000),
        Dimension.ofMeters(13.6, 2.5, 3.2),
        null,
        Notes.empty());
  }

  private static TireSpecification standardTire() {
    return TireSpecification.of("Michelin", "X Multi", "315/70 R22.5", 154, "L");
  }

  private static RoutePlan standardRoutePlan() {
    return RoutePlan.of(
        "RTE-001",
        List.of(
            RouteStop.of(
                1,
                RouteStopType.START,
                pickupFacility(),
                TimeWindow.of("07:00", "08:00"),
                Notes.empty()),
            RouteStop.of(
                2,
                RouteStopType.PICKUP,
                pickupFacility(),
                TimeWindow.of("08:00", "09:00"),
                Notes.empty()),
            RouteStop.of(
                3,
                RouteStopType.DELIVERY,
                deliveryFacility(),
                TimeWindow.of("14:00", "15:00"),
                Notes.empty()),
            RouteStop.of(
                4,
                RouteStopType.END,
                deliveryFacility(),
                TimeWindow.of("16:00", "17:00"),
                Notes.empty())),
        Distance.ofKilometers(580),
        Notes.empty());
  }

  private static Shipment standardShipment() {
    return Shipment.fromAcceptedOrder("SHP-001", acceptedStandardOrder(), Notes.empty());
  }

  private static Shipment fragileShipment() {
    return Shipment.fromAcceptedOrder("SHP-002", acceptedFragileOrder(), Notes.empty());
  }

  private static Shipment refrigeratedShipment() {
    return Shipment.fromAcceptedOrder("SHP-003", acceptedRefrigeratedOrder(), Notes.empty());
  }

  private static Shipment adrTankShipment() {
    return Shipment.fromAcceptedOrder("SHP-004", acceptedAdrTankOrder(), Notes.empty());
  }

  private static Shipment internationalShipment() {
    return Shipment.fromAcceptedOrder("SHP-005", acceptedInternationalOrder(), Notes.empty());
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
            Notes.empty())
        .submit()
        .accept();
  }

  private static TransportOrder acceptedFragileOrder() {
    return TransportOrder.draft(
            "ORD-002",
            activeCustomerAccount(),
            fragileCargoLoad(),
            pickupFacility(),
            deliveryFacility(),
            TimeWindow.of("08:00", "12:00"),
            TimeWindow.of("14:00", "18:00"),
            TransportServiceType.STANDARD,
            Money.of("1300.00", "EUR"),
            Notes.empty())
        .submit()
        .accept();
  }

  private static TransportOrder acceptedRefrigeratedOrder() {
    return TransportOrder.draft(
            "ORD-003",
            activeCustomerAccount(),
            refrigeratedCargoLoad(),
            pickupFacility(),
            deliveryFacility(),
            TimeWindow.of("08:00", "12:00"),
            TimeWindow.of("14:00", "18:00"),
            TransportServiceType.REFRIGERATED,
            Money.of("1800.00", "EUR"),
            Notes.empty())
        .submit()
        .accept();
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
            Notes.empty())
        .submit()
        .accept();
  }

  private static TransportOrder acceptedInternationalOrder() {
    return TransportOrder.draft(
            "ORD-005",
            activeCustomerAccount(),
            standardCargoLoad(),
            pickupFacility(),
            frenchDeliveryFacility(),
            TimeWindow.of("08:00", "12:00"),
            TimeWindow.of("14:00", "18:00"),
            TransportServiceType.STANDARD,
            Money.of("1600.00", "EUR"),
            Notes.empty())
        .submit()
        .accept();
  }

  private static CustomerAccount activeCustomerAccount() {
    return CustomerAccount.of(
        Customer.active(
            "CUST-001",
            "ACME Logistics S.r.l.",
            CustomerType.COMPANY,
            customerLocation(),
            Notes.empty()),
        primaryContact());
  }

  private static CustomerContact primaryContact() {
    return CustomerContact.primary(
        "Mario Rossi",
        CustomerContactRole.LOGISTICS,
        "mario.rossi@example.com",
        "+39 333 1234567",
        Notes.empty());
  }

  private static Location customerLocation() {
    return Location.of(
        "Sede Cliente Milano",
        Address.of("Via Cliente 10", "Milano", "20100", "IT"),
        "Europe/Rome");
  }

  private static CargoLoad standardCargoLoad() {
    return CargoLoad.of(
        CargoItem.of(
            "Merce generale",
            CargoCategory.GENERAL,
            Weight.ofKilograms(5000),
            Dimension.ofMeters(8, 2, 2),
            Notes.empty()));
  }

  private static CargoLoad fragileCargoLoad() {
    return CargoLoad.of(
        CargoItem.of(
            "Vetri fragili",
            CargoCategory.FRAGILE,
            Weight.ofKilograms(1000),
            Dimension.ofMeters(2, 1, 1),
            Notes.empty()));
  }

  private static CargoLoad refrigeratedCargoLoad() {
    return CargoLoad.of(
        CargoItem.temperatureControlled(
            "Latte fresco",
            CargoCategory.REFRIGERATED_FOOD,
            Weight.ofKilograms(5000),
            Dimension.ofMeters(8, 2, 2),
            TemperatureRange.ofCelsius(2, 6),
            Notes.empty()));
  }

  private static CargoLoad adrTankCargoLoad() {
    return CargoLoad.of(
        CargoItem.dangerousGoods(
            "Benzina in cisterna",
            CargoCategory.HAZARDOUS_MATERIAL,
            Weight.ofKilograms(10000),
            Dimension.ofMeters(10, 2, 2),
            gasolineProfile(),
            Notes.empty()));
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
        true);
  }

  private static Facility pickupFacility() {
    return Facility.active(
        "MIL-WH-01",
        FacilityType.WAREHOUSE,
        Location.of(
            "Magazzino Milano", Address.of("Via Roma 10", "Milano", "20100", "IT"), "Europe/Rome"),
        TimeWindow.of("08:00", "18:00"),
        Notes.empty());
  }

  private static Facility wrongPickupFacility() {
    return Facility.active(
        "TOR-WH-01",
        FacilityType.WAREHOUSE,
        Location.of(
            "Magazzino Torino", Address.of("Via Po 5", "Torino", "10100", "IT"), "Europe/Rome"),
        TimeWindow.of("08:00", "18:00"),
        Notes.empty());
  }

  private static Facility deliveryFacility() {
    return Facility.active(
        "ROM-WH-01",
        FacilityType.WAREHOUSE,
        Location.of(
            "Magazzino Roma", Address.of("Via Appia 20", "Roma", "00100", "IT"), "Europe/Rome"),
        TimeWindow.of("08:00", "18:00"),
        Notes.empty());
  }

  private static Facility wrongDeliveryFacility() {
    return Facility.active(
        "NAP-WH-01",
        FacilityType.WAREHOUSE,
        Location.of(
            "Magazzino Napoli",
            Address.of("Via Toledo 30", "Napoli", "80100", "IT"),
            "Europe/Rome"),
        TimeWindow.of("08:00", "18:00"),
        Notes.empty());
  }

  private static Facility frenchDeliveryFacility() {
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
