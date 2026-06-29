package it.gabriele.truckflow.domain.customer;

import it.gabriele.truckflow.domain.shared.Notes;
import java.util.Objects;

/**
 * Rappresenta una persona di contatto associata a un cliente. Esempio: referente logistico,
 * amministrativo o fatturazione.
 */
public final class CustomerContact {

  private static final int MAX_NAME_LENGTH = 150;
  private static final int MAX_EMAIL_LENGTH = 254;
  private static final int MAX_PHONE_LENGTH = 30;

  private final String fullName;
  private final CustomerContactRole role;
  private final String email;
  private final String phoneNumber;
  private final boolean primaryContact;
  private final Notes notes;

  private CustomerContact(
      String fullName,
      CustomerContactRole role,
      String email,
      String phoneNumber,
      boolean primaryContact,
      Notes notes) {
    this.fullName = validateFullName(fullName);

    if (role == null) {
      throw new IllegalArgumentException("Il ruolo del contatto è obbligatorio.");
    }

    this.email = validateEmail(email);
    this.phoneNumber = validatePhoneNumber(phoneNumber);

    if (notes == null) {
      throw new IllegalArgumentException("Le note del contatto sono obbligatorie.");
    }

    this.role = role;
    this.primaryContact = primaryContact;
    this.notes = notes;
  }

  public static CustomerContact primary(
      String fullName, CustomerContactRole role, String email, String phoneNumber, Notes notes) {
    return new CustomerContact(fullName, role, email, phoneNumber, true, notes);
  }

  public static CustomerContact secondary(
      String fullName, CustomerContactRole role, String email, String phoneNumber, Notes notes) {
    return new CustomerContact(fullName, role, email, phoneNumber, false, notes);
  }

  private static String validateFullName(String fullName) {
    if (fullName == null) {
      throw new IllegalArgumentException("Il nome completo del contatto è obbligatorio.");
    }

    String normalizedFullName = fullName.trim();

    if (normalizedFullName.isEmpty()) {
      throw new IllegalArgumentException("Il nome completo del contatto non può essere vuoto.");
    }

    if (normalizedFullName.length() > MAX_NAME_LENGTH) {
      throw new IllegalArgumentException(
          "Il nome completo del contatto non può superare " + MAX_NAME_LENGTH + " caratteri.");
    }

    return normalizedFullName;
  }

  private static String validateEmail(String email) {
    if (email == null) {
      throw new IllegalArgumentException("L'email del contatto è obbligatoria.");
    }

    String normalizedEmail = email.trim().toLowerCase();

    if (normalizedEmail.isEmpty()) {
      throw new IllegalArgumentException("L'email del contatto non può essere vuota.");
    }

    if (normalizedEmail.length() > MAX_EMAIL_LENGTH) {
      throw new IllegalArgumentException(
          "L'email del contatto non può superare " + MAX_EMAIL_LENGTH + " caratteri.");
    }

    if (!normalizedEmail.contains("@")) {
      throw new IllegalArgumentException("L'email del contatto deve contenere il carattere @.");
    }

    return normalizedEmail;
  }

  private static String validatePhoneNumber(String phoneNumber) {
    if (phoneNumber == null) {
      throw new IllegalArgumentException("Il numero di telefono del contatto è obbligatorio.");
    }

    String normalizedPhoneNumber = phoneNumber.trim();

    if (normalizedPhoneNumber.isEmpty()) {
      throw new IllegalArgumentException(
          "Il numero di telefono del contatto non può essere vuoto.");
    }

    if (normalizedPhoneNumber.length() > MAX_PHONE_LENGTH) {
      throw new IllegalArgumentException(
          "Il numero di telefono del contatto non può superare "
              + MAX_PHONE_LENGTH
              + " caratteri.");
    }

    if (!normalizedPhoneNumber.matches("[0-9+() .-]+")) {
      throw new IllegalArgumentException("Il numero di telefono contiene caratteri non validi.");
    }

    return normalizedPhoneNumber;
  }

  public String getFullName() {
    return fullName;
  }

  public CustomerContactRole getRole() {
    return role;
  }

  public String getEmail() {
    return email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public boolean isPrimaryContact() {
    return primaryContact;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean hasRole(CustomerContactRole role) {
    if (role == null) {
      throw new IllegalArgumentException("Il ruolo da confrontare è obbligatorio.");
    }

    return this.role == role;
  }

  public boolean hasNotes() {
    return notes.hasText();
  }

  public String formatSingleLine() {
    return fullName + " - " + role + " - " + email + " - " + phoneNumber;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CustomerContact that)) return false;
    return primaryContact == that.primaryContact
        && fullName.equals(that.fullName)
        && role == that.role
        && email.equals(that.email)
        && phoneNumber.equals(that.phoneNumber)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fullName, role, email, phoneNumber, primaryContact, notes);
  }

  @Override
  public String toString() {
    return formatSingleLine();
  }
}
