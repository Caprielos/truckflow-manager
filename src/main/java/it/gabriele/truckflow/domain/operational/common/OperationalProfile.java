package it.gabriele.truckflow.domain.operational.common;

public record OperationalProfile(
    String firstName,
    String lastName,
    String email,
    String phone,
    String mobile,
    String department,
    String position,
    String notes) {

  public OperationalProfile {
    firstName = requireText(firstName, "firstName");
    lastName = requireText(lastName, "lastName");
    email = normalize(email).toLowerCase();
    phone = normalize(phone);
    mobile = normalize(mobile);
    department = normalize(department);
    position = normalize(position);
    notes = normalize(notes);

    if (!email.isBlank() && !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
      throw new IllegalArgumentException("Email is not valid.");
    }
  }

  public String fullName() {
    return firstName + " " + lastName;
  }

  private static String requireText(String value, String fieldName) {
    String normalized = normalize(value);

    if (normalized.isBlank()) {
      throw new IllegalArgumentException(fieldName + " is required.");
    }

    return normalized;
  }

  private static String normalize(String value) {
    return value == null ? "" : value.trim();
  }
}
