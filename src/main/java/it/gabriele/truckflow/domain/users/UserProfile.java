package it.gabriele.truckflow.domain.users;

import it.gabriele.truckflow.domain.users.exceptions.InvalidUserException;

public record UserProfile(
    String firstName, String lastName, UserContact contact, UserAddress address) {

  public UserProfile {
    firstName = requireText(firstName, "firstName");
    lastName = requireText(lastName, "lastName");
    contact = requireNonNull(contact, "contact");
    address = requireNonNull(address, "address");
  }

  public String fullName() {
    return firstName + " " + lastName;
  }

  public UserProfile withContact(UserContact contact) {
    return new UserProfile(firstName, lastName, contact, address);
  }

  public UserProfile withAddress(UserAddress address) {
    return new UserProfile(firstName, lastName, contact, address);
  }

  private static String requireText(String value, String fieldName) {
    String normalized = value == null ? "" : value.trim();

    if (normalized.isBlank()) {
      throw new InvalidUserException(fieldName + " is required.");
    }

    return normalized;
  }

  private static <T> T requireNonNull(T value, String fieldName) {
    if (value == null) {
      throw new InvalidUserException(fieldName + " is required.");
    }

    return value;
  }
}
