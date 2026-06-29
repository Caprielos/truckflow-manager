package it.gabriele.truckflow.domain.customer;

import it.gabriele.truckflow.domain.location.Location;
import it.gabriele.truckflow.domain.shared.Notes;
import java.util.Objects;

/**
 * Rappresenta un cliente del sistema logistico. Un cliente può richiedere spedizioni, ritiri e
 * consegne.
 */
public final class Customer {

  private static final int MAX_CODE_LENGTH = 50;
  private static final int MAX_LEGAL_NAME_LENGTH = 200;

  private final String code;
  private final String legalName;
  private final CustomerType type;
  private final CustomerStatus status;
  private final Location primaryLocation;
  private final Notes notes;

  private Customer(
      String code,
      String legalName,
      CustomerType type,
      CustomerStatus status,
      Location primaryLocation,
      Notes notes) {
    this.code = validateCode(code);
    this.legalName = validateLegalName(legalName);

    if (type == null) {
      throw new IllegalArgumentException("Il tipo cliente è obbligatorio.");
    }

    if (status == null) {
      throw new IllegalArgumentException("Lo stato cliente è obbligatorio.");
    }

    if (primaryLocation == null) {
      throw new IllegalArgumentException("La sede principale del cliente è obbligatoria.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note del cliente sono obbligatorie.");
    }

    this.type = type;
    this.status = status;
    this.primaryLocation = primaryLocation;
    this.notes = notes;
  }

  public static Customer active(
      String code, String legalName, CustomerType type, Location primaryLocation, Notes notes) {
    return new Customer(code, legalName, type, CustomerStatus.ACTIVE, primaryLocation, notes);
  }

  public static Customer inactive(
      String code, String legalName, CustomerType type, Location primaryLocation, Notes notes) {
    return new Customer(code, legalName, type, CustomerStatus.INACTIVE, primaryLocation, notes);
  }

  public static Customer suspended(
      String code, String legalName, CustomerType type, Location primaryLocation, Notes notes) {
    return new Customer(code, legalName, type, CustomerStatus.SUSPENDED, primaryLocation, notes);
  }

  private static String validateCode(String code) {
    if (code == null) {
      throw new IllegalArgumentException("Il codice cliente è obbligatorio.");
    }

    String normalizedCode = code.trim().toUpperCase();

    if (normalizedCode.isEmpty()) {
      throw new IllegalArgumentException("Il codice cliente non può essere vuoto.");
    }

    if (normalizedCode.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice cliente non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }

    if (!normalizedCode.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice cliente può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedCode;
  }

  private static String validateLegalName(String legalName) {
    if (legalName == null) {
      throw new IllegalArgumentException("La ragione sociale del cliente è obbligatoria.");
    }

    String normalizedLegalName = legalName.trim();

    if (normalizedLegalName.isEmpty()) {
      throw new IllegalArgumentException("La ragione sociale del cliente non può essere vuota.");
    }

    if (normalizedLegalName.length() > MAX_LEGAL_NAME_LENGTH) {
      throw new IllegalArgumentException(
          "La ragione sociale del cliente non può superare "
              + MAX_LEGAL_NAME_LENGTH
              + " caratteri.");
    }

    return normalizedLegalName;
  }

  public String getCode() {
    return code;
  }

  public String getLegalName() {
    return legalName;
  }

  public CustomerType getType() {
    return type;
  }

  public CustomerStatus getStatus() {
    return status;
  }

  public Location getPrimaryLocation() {
    return primaryLocation;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isActive() {
    return status == CustomerStatus.ACTIVE;
  }

  public boolean isSuspended() {
    return status == CustomerStatus.SUSPENDED;
  }

  public boolean isBusinessCustomer() {
    return type.isBusinessCustomer();
  }

  public boolean isInCountry(String countryCode) {
    return primaryLocation.isInCountry(countryCode);
  }

  public boolean hasNotes() {
    return notes.hasText();
  }

  public String formatSingleLine() {
    return code + " - " + legalName + " - " + type + " - " + status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Customer customer)) return false;
    return code.equals(customer.code)
        && legalName.equals(customer.legalName)
        && type == customer.type
        && status == customer.status
        && primaryLocation.equals(customer.primaryLocation)
        && notes.equals(customer.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, legalName, type, status, primaryLocation, notes);
  }

  @Override
  public String toString() {
    return formatSingleLine();
  }
}
