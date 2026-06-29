package it.gabriele.truckflow.domain.order;

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
import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TemperatureRange;
import it.gabriele.truckflow.domain.shared.TimeWindow;
import it.gabriele.truckflow.domain.shared.Weight;
import org.junit.jupiter.api.Test;

/** Testa TransportOrder. */
class TransportOrderTest {

  @Test
  void shouldCreateDraftTransportOrder() {
    TransportOrder order = standardDraftOrder();

    assertEquals("ORD-001", order.getOrderNumber());
    assertEquals(TransportOrderStatus.DRAFT, order.getStatus());
    assertEquals(TransportServiceType.STANDARD, order.getServiceType());
    assertEquals(Money.of("1200.00", "EUR"), order.getQuotedPrice());
    assertTrue(order.canBeSubmitted());
  }

  @Test
  void shouldCreateSubmittedTransportOrder() {
    TransportOrder order =
        TransportOrder.submitted(
            "ORD-001",
            activeCustomerAccount(),
            standardCargoLoad(),
            pickupFacility(),
            deliveryFacility(),
            TimeWindow.of("08:00", "12:00"),
            TimeWindow.of("14:00", "18:00"),
            TransportServiceType.STANDARD,
            Money.of("1200.00", "EUR"),
            Notes.empty());

    assertEquals(TransportOrderStatus.SUBMITTED, order.getStatus());
    assertTrue(order.canBeAccepted());
  }

  @Test
  void shouldNormalizeOrderNumber() {
    TransportOrder order =
        TransportOrder.draft(
            "  ord_001  ",
            activeCustomerAccount(),
            standardCargoLoad(),
            pickupFacility(),
            deliveryFacility(),
            TimeWindow.of("08:00", "12:00"),
            TimeWindow.of("14:00", "18:00"),
            TransportServiceType.STANDARD,
            Money.of("1200.00", "EUR"),
            Notes.empty());

    assertEquals("ORD_001", order.getOrderNumber());
  }

  @Test
  void shouldNotAllowInvalidOrderNumber() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            TransportOrder.draft(
                null,
                activeCustomerAccount(),
                standardCargoLoad(),
                pickupFacility(),
                deliveryFacility(),
                TimeWindow.of("08:00", "12:00"),
                TimeWindow.of("14:00", "18:00"),
                TransportServiceType.STANDARD,
                Money.of("1200.00", "EUR"),
                Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            TransportOrder.draft(
                "ORD 001",
                activeCustomerAccount(),
                standardCargoLoad(),
                pickupFacility(),
                deliveryFacility(),
                TimeWindow.of("08:00", "12:00"),
                TimeWindow.of("14:00", "18:00"),
                TransportServiceType.STANDARD,
                Money.of("1200.00", "EUR"),
                Notes.empty()));
  }

  @Test
  void shouldNotAllowSamePickupAndDeliveryFacility() {
    Facility facility = pickupFacility();

    assertThrows(
        IllegalArgumentException.class,
        () ->
            TransportOrder.draft(
                "ORD-001",
                activeCustomerAccount(),
                standardCargoLoad(),
                facility,
                facility,
                TimeWindow.of("08:00", "12:00"),
                TimeWindow.of("14:00", "18:00"),
                TransportServiceType.STANDARD,
                Money.of("1200.00", "EUR"),
                Notes.empty()));
  }

  @Test
  void shouldDetectInternationalOrder() {
    TransportOrder order =
        TransportOrder.draft(
            "ORD-001",
            activeCustomerAccount(),
            standardCargoLoad(),
            pickupFacility(),
            frenchDeliveryFacility(),
            TimeWindow.of("08:00", "12:00"),
            TimeWindow.of("14:00", "18:00"),
            TransportServiceType.STANDARD,
            Money.of("1200.00", "EUR"),
            Notes.empty());

    assertTrue(order.isInternational());
  }

  @Test
  void shouldSubmitDraftOrder() {
    TransportOrder submitted = standardDraftOrder().submit();

    assertEquals(TransportOrderStatus.SUBMITTED, submitted.getStatus());
  }

  @Test
  void shouldNotSubmitOrderForInactiveCustomer() {
    TransportOrder order =
        TransportOrder.draft(
            "ORD-001",
            inactiveCustomerAccount(),
            standardCargoLoad(),
            pickupFacility(),
            deliveryFacility(),
            TimeWindow.of("08:00", "12:00"),
            TimeWindow.of("14:00", "18:00"),
            TransportServiceType.STANDARD,
            Money.of("1200.00", "EUR"),
            Notes.empty());

    assertFalse(order.canBeSubmitted());
    assertThrows(IllegalStateException.class, order::submit);
  }

  @Test
  void shouldAcceptSubmittedOrder() {
    TransportOrder accepted = standardDraftOrder().submit().accept();

    assertEquals(TransportOrderStatus.ACCEPTED, accepted.getStatus());
  }

  @Test
  void shouldNotAcceptDraftOrder() {
    TransportOrder order = standardDraftOrder();

    assertFalse(order.canBeAccepted());
    assertThrows(IllegalStateException.class, order::accept);
  }

  @Test
  void shouldRejectSubmittedOrder() {
    TransportOrder rejected = standardDraftOrder().submit().reject();

    assertEquals(TransportOrderStatus.REJECTED, rejected.getStatus());
  }

  @Test
  void shouldCancelNonTerminalOrder() {
    TransportOrder cancelled = standardDraftOrder().cancel();

    assertEquals(TransportOrderStatus.CANCELLED, cancelled.getStatus());
  }

  @Test
  void shouldNotCancelTerminalOrder() {
    TransportOrder cancelled = standardDraftOrder().cancel();

    assertThrows(IllegalStateException.class, cancelled::cancel);
  }

  @Test
  void shouldDetectTemperatureControlRequirement() {
    TransportOrder order =
        TransportOrder.draft(
            "ORD-001",
            activeCustomerAccount(),
            refrigeratedCargoLoad(),
            pickupFacility(),
            deliveryFacility(),
            TimeWindow.of("08:00", "12:00"),
            TimeWindow.of("14:00", "18:00"),
            TransportServiceType.REFRIGERATED,
            Money.of("1800.00", "EUR"),
            Notes.empty());

    assertTrue(order.requiresTemperatureControlledTransport());
    assertTrue(order.isServiceCompatibleWithCargo());
  }

  @Test
  void shouldDetectIncompatibleTemperatureService() {
    TransportOrder order =
        TransportOrder.draft(
            "ORD-001",
            activeCustomerAccount(),
            refrigeratedCargoLoad(),
            pickupFacility(),
            deliveryFacility(),
            TimeWindow.of("08:00", "12:00"),
            TimeWindow.of("14:00", "18:00"),
            TransportServiceType.STANDARD,
            Money.of("1800.00", "EUR"),
            Notes.empty());

    assertFalse(order.isServiceCompatibleWithCargo());
    assertFalse(order.canBeSubmitted());
  }

  @Test
  void shouldDetectHazardousMaterial() {
    TransportOrder order =
        TransportOrder.draft(
            "ORD-001",
            activeCustomerAccount(),
            hazardousCargoLoad(),
            pickupFacility(),
            deliveryFacility(),
            TimeWindow.of("08:00", "12:00"),
            TimeWindow.of("14:00", "18:00"),
            TransportServiceType.HAZARDOUS,
            Money.of("2200.00", "EUR"),
            Notes.empty());

    assertTrue(order.containsHazardousMaterial());
    assertTrue(order.isServiceCompatibleWithCargo());
  }

  @Test
  void shouldFormatSingleLine() {
    TransportOrder order = standardDraftOrder();

    assertEquals("ORD-001 - CUST-001 - STANDARD - DRAFT", order.formatSingleLine());
  }

  @Test
  void shouldConsiderEquivalentOrdersEqual() {
    TransportOrder first = standardDraftOrder();
    TransportOrder second = standardDraftOrder();

    assertEquals(first, second);
    assertEquals(first.hashCode(), second.hashCode());
  }

  private static TransportOrder standardDraftOrder() {
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
        Notes.empty());
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

  private static CustomerAccount inactiveCustomerAccount() {
    return CustomerAccount.of(
        Customer.inactive(
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
