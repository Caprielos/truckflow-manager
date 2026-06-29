package it.gabriele.truckflow.domain.shipment;

import static org.junit.jupiter.api.Assertions.*;

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
import org.junit.jupiter.api.Test;

/** Testa ShipmentRules. */
class ShipmentRulesTest {

  @Test
  void shouldCheckPlanningRule() {
    Shipment createdShipment = createdStandardShipment();
    Shipment plannedShipment = createdStandardShipment().plan();

    assertTrue(ShipmentRules.canBePlanned(createdShipment));
    assertFalse(ShipmentRules.canBePlanned(plannedShipment));
  }

  @Test
  void shouldCheckDispatchRule() {
    Shipment plannedShipment = createdStandardShipment().plan();
    Shipment createdShipment = createdStandardShipment();

    assertTrue(ShipmentRules.canBeDispatched(plannedShipment));
    assertFalse(ShipmentRules.canBeDispatched(createdShipment));
  }

  @Test
  void shouldCheckInTransitRule() {
    Shipment dispatchedShipment = createdStandardShipment().plan().dispatch();

    Shipment plannedShipment = createdStandardShipment().plan();

    assertTrue(ShipmentRules.canBeMarkedInTransit(dispatchedShipment));
    assertFalse(ShipmentRules.canBeMarkedInTransit(plannedShipment));
  }

  @Test
  void shouldCheckDeliveryRule() {
    Shipment inTransitShipment = createdStandardShipment().plan().dispatch().markInTransit();

    Shipment dispatchedShipment = createdStandardShipment().plan().dispatch();

    assertTrue(ShipmentRules.canBeDelivered(inTransitShipment));
    assertFalse(ShipmentRules.canBeDelivered(dispatchedShipment));
  }

  @Test
  void shouldCheckCancellationRule() {
    Shipment createdShipment = createdStandardShipment();
    Shipment deliveredShipment =
        createdStandardShipment().plan().dispatch().markInTransit().deliver();

    assertTrue(ShipmentRules.canBeCancelled(createdShipment));
    assertFalse(ShipmentRules.canBeCancelled(deliveredShipment));
  }

  @Test
  void shouldDetectCompletedShipment() {
    Shipment deliveredShipment =
        createdStandardShipment().plan().dispatch().markInTransit().deliver();

    Shipment createdShipment = createdStandardShipment();

    assertTrue(ShipmentRules.isCompleted(deliveredShipment));
    assertFalse(ShipmentRules.isCompleted(createdShipment));
  }

  @Test
  void shouldDetectTerminalShipment() {
    Shipment deliveredShipment =
        createdStandardShipment().plan().dispatch().markInTransit().deliver();

    Shipment cancelledShipment = createdStandardShipment().cancel();
    Shipment createdShipment = createdStandardShipment();

    assertTrue(ShipmentRules.isTerminal(deliveredShipment));
    assertTrue(ShipmentRules.isTerminal(cancelledShipment));
    assertFalse(ShipmentRules.isTerminal(createdShipment));
  }

  @Test
  void shouldDetectSpecialHandlingForInternationalShipment() {
    Shipment shipment =
        Shipment.fromAcceptedOrder("SHP-001", acceptedInternationalOrder(), Notes.empty());

    assertTrue(ShipmentRules.requiresSpecialHandling(shipment));
  }

  @Test
  void shouldDetectSpecialHandlingForRefrigeratedShipment() {
    Shipment shipment =
        Shipment.fromAcceptedOrder("SHP-001", acceptedRefrigeratedOrder(), Notes.empty());

    assertTrue(ShipmentRules.requiresSpecialHandling(shipment));
  }

  @Test
  void shouldDetectSpecialHandlingForHazardousShipment() {
    Shipment shipment =
        Shipment.fromAcceptedOrder("SHP-001", acceptedHazardousOrder(), Notes.empty());

    assertTrue(ShipmentRules.requiresSpecialHandling(shipment));
  }

  @Test
  void shouldDetectNormalShipmentWithoutSpecialHandling() {
    Shipment shipment = createdStandardShipment();

    assertFalse(ShipmentRules.requiresSpecialHandling(shipment));
  }

  @Test
  void shouldNotAllowNullShipment() {
    assertThrows(IllegalArgumentException.class, () -> ShipmentRules.canBePlanned(null));
    assertThrows(IllegalArgumentException.class, () -> ShipmentRules.canBeDispatched(null));
    assertThrows(IllegalArgumentException.class, () -> ShipmentRules.canBeMarkedInTransit(null));
    assertThrows(IllegalArgumentException.class, () -> ShipmentRules.canBeDelivered(null));
    assertThrows(IllegalArgumentException.class, () -> ShipmentRules.canBeCancelled(null));
    assertThrows(IllegalArgumentException.class, () -> ShipmentRules.requiresSpecialHandling(null));
    assertThrows(IllegalArgumentException.class, () -> ShipmentRules.isCompleted(null));
    assertThrows(IllegalArgumentException.class, () -> ShipmentRules.isTerminal(null));
  }

  private static Shipment createdStandardShipment() {
    return Shipment.fromAcceptedOrder("SHP-001", acceptedStandardOrder(), Notes.empty());
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

  private static TransportOrder acceptedInternationalOrder() {
    return TransportOrder.draft(
            "ORD-001",
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

  private static TransportOrder acceptedRefrigeratedOrder() {
    return TransportOrder.draft(
            "ORD-001",
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

  private static TransportOrder acceptedHazardousOrder() {
    return TransportOrder.draft(
            "ORD-001",
            activeCustomerAccount(),
            hazardousCargoLoad(),
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
            Weight.ofKilograms(500),
            Dimension.ofMeters(2, 1, 1),
            Notes.empty()));
  }

  private static CargoLoad refrigeratedCargoLoad() {
    return CargoLoad.of(
        CargoItem.temperatureControlled(
            "Latte fresco",
            CargoCategory.REFRIGERATED_FOOD,
            Weight.ofKilograms(300),
            Dimension.ofMeters(2, 1, 1),
            TemperatureRange.ofCelsius(2, 8),
            Notes.empty()));
  }

  private static CargoLoad hazardousCargoLoad() {
    return CargoLoad.of(
        CargoItem.of(
            "Prodotto chimico",
            CargoCategory.HAZARDOUS_MATERIAL,
            Weight.ofKilograms(200),
            Dimension.ofMeters(1, 1, 1),
            Notes.empty()));
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

  private static Facility deliveryFacility() {
    return Facility.active(
        "ROM-WH-01",
        FacilityType.WAREHOUSE,
        Location.of(
            "Magazzino Roma", Address.of("Via Appia 20", "Roma", "00100", "IT"), "Europe/Rome"),
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
