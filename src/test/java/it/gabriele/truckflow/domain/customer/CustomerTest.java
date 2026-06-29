package it.gabriele.truckflow.domain.customer;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.location.Address;
import it.gabriele.truckflow.domain.location.Location;
import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

/** Testa Customer. */
class CustomerTest {

  @Test
  void shouldCreateActiveCustomer() {
    Location location = customerLocation();

    Customer customer =
        Customer.active(
            "cust-001", "ACME Logistics S.r.l.", CustomerType.COMPANY, location, Notes.empty());

    assertEquals("CUST-001", customer.getCode());
    assertEquals("ACME Logistics S.r.l.", customer.getLegalName());
    assertEquals(CustomerType.COMPANY, customer.getType());
    assertEquals(CustomerStatus.ACTIVE, customer.getStatus());
    assertEquals(location, customer.getPrimaryLocation());
    assertEquals(Notes.empty(), customer.getNotes());
    assertTrue(customer.isActive());
  }

  @Test
  void shouldCreateInactiveCustomer() {
    Customer customer =
        Customer.inactive(
            "CUST-001",
            "ACME Logistics S.r.l.",
            CustomerType.COMPANY,
            customerLocation(),
            Notes.empty());

    assertEquals(CustomerStatus.INACTIVE, customer.getStatus());
    assertFalse(customer.isActive());
  }

  @Test
  void shouldCreateSuspendedCustomer() {
    Customer customer =
        Customer.suspended(
            "CUST-001",
            "ACME Logistics S.r.l.",
            CustomerType.COMPANY,
            customerLocation(),
            Notes.empty());

    assertEquals(CustomerStatus.SUSPENDED, customer.getStatus());
    assertTrue(customer.isSuspended());
    assertFalse(customer.isActive());
  }

  @Test
  void shouldNormalizeCodeAndLegalName() {
    Customer customer =
        Customer.active(
            "  cust_001  ",
            "  ACME Logistics S.r.l.  ",
            CustomerType.COMPANY,
            customerLocation(),
            Notes.empty());

    assertEquals("CUST_001", customer.getCode());
    assertEquals("ACME Logistics S.r.l.", customer.getLegalName());
  }

  @Test
  void shouldNotAllowInvalidCode() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            Customer.active(
                null,
                "ACME Logistics S.r.l.",
                CustomerType.COMPANY,
                customerLocation(),
                Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            Customer.active(
                "   ",
                "ACME Logistics S.r.l.",
                CustomerType.COMPANY,
                customerLocation(),
                Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            Customer.active(
                "CUST 001",
                "ACME Logistics S.r.l.",
                CustomerType.COMPANY,
                customerLocation(),
                Notes.empty()));
  }

  @Test
  void shouldNotAllowTooLongCode() {
    String tooLongCode = "A".repeat(51);

    assertThrows(
        IllegalArgumentException.class,
        () ->
            Customer.active(
                tooLongCode,
                "ACME Logistics S.r.l.",
                CustomerType.COMPANY,
                customerLocation(),
                Notes.empty()));
  }

  @Test
  void shouldNotAllowInvalidLegalName() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            Customer.active(
                "CUST-001", null, CustomerType.COMPANY, customerLocation(), Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            Customer.active(
                "CUST-001", "   ", CustomerType.COMPANY, customerLocation(), Notes.empty()));
  }

  @Test
  void shouldNotAllowTooLongLegalName() {
    String tooLongLegalName = "A".repeat(201);

    assertThrows(
        IllegalArgumentException.class,
        () ->
            Customer.active(
                "CUST-001",
                tooLongLegalName,
                CustomerType.COMPANY,
                customerLocation(),
                Notes.empty()));
  }

  @Test
  void shouldNotAllowNullMandatoryFields() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            Customer.active(
                "CUST-001", "ACME Logistics S.r.l.", null, customerLocation(), Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            Customer.active(
                "CUST-001", "ACME Logistics S.r.l.", CustomerType.COMPANY, null, Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            Customer.active(
                "CUST-001",
                "ACME Logistics S.r.l.",
                CustomerType.COMPANY,
                customerLocation(),
                null));
  }

  @Test
  void shouldDetectBusinessCustomer() {
    Customer company =
        Customer.active(
            "CUST-001",
            "ACME Logistics S.r.l.",
            CustomerType.COMPANY,
            customerLocation(),
            Notes.empty());

    Customer individual =
        Customer.active(
            "CUST-002", "Mario Rossi", CustomerType.INDIVIDUAL, customerLocation(), Notes.empty());

    assertTrue(company.isBusinessCustomer());
    assertFalse(individual.isBusinessCustomer());
  }

  @Test
  void shouldDetectCountry() {
    Customer customer =
        Customer.active(
            "CUST-001",
            "ACME Logistics S.r.l.",
            CustomerType.COMPANY,
            customerLocation(),
            Notes.empty());

    assertTrue(customer.isInCountry("IT"));
    assertFalse(customer.isInCountry("FR"));
  }

  @Test
  void shouldDetectNotes() {
    Customer customer =
        Customer.active(
            "CUST-001",
            "ACME Logistics S.r.l.",
            CustomerType.COMPANY,
            customerLocation(),
            Notes.of("Cliente prioritario"));

    assertTrue(customer.hasNotes());
  }

  @Test
  void shouldFormatSingleLine() {
    Customer customer =
        Customer.active(
            "CUST-001",
            "ACME Logistics S.r.l.",
            CustomerType.COMPANY,
            customerLocation(),
            Notes.empty());

    assertEquals(
        "CUST-001 - ACME Logistics S.r.l. - COMPANY - ACTIVE", customer.formatSingleLine());
  }

  @Test
  void shouldConsiderEquivalentCustomersEqual() {
    Customer first =
        Customer.active(
            "  cust-001  ",
            "  ACME Logistics S.r.l.  ",
            CustomerType.COMPANY,
            customerLocation(),
            Notes.empty());

    Customer second =
        Customer.active(
            "CUST-001",
            "ACME Logistics S.r.l.",
            CustomerType.COMPANY,
            customerLocation(),
            Notes.empty());

    assertEquals(first, second);
    assertEquals(first.hashCode(), second.hashCode());
  }

  private static Location customerLocation() {
    return Location.of(
        "Sede Cliente Milano",
        Address.of("Via Cliente 10", "Milano", "20100", "IT"),
        "Europe/Rome");
  }
}
