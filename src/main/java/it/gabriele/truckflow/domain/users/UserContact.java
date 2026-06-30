package it.gabriele.truckflow.domain.users;

public record UserContact(String email, String phoneNumber, String mobileNumber) {

  public UserContact {
    email = normalize(email).toLowerCase();
    phoneNumber = normalize(phoneNumber);
    mobileNumber = normalize(mobileNumber);

    if (email.isBlank() && phoneNumber.isBlank() && mobileNumber.isBlank()) {
      throw new IllegalArgumentException("At least one contact value is required.");
    }

    if (!email.isBlank() && !email.contains("@")) {
      throw new IllegalArgumentException("Email is not valid.");
    }
  }

  public boolean hasEmail() {
    return !email.isBlank();
  }

  public boolean hasPhoneContact() {
    return !phoneNumber.isBlank() || !mobileNumber.isBlank();
  }

  private static String normalize(String value) {
    return value == null ? "" : value.trim();
  }
}
