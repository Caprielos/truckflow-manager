package it.gabriele.truckflow.domain.users;

import java.util.regex.Pattern;

public record UserContact(String email, String phoneNumber, String mobileNumber) {

  private static final Pattern EMAIL_PATTERN =
      Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE);

  public UserContact {
    email = normalize(email).toLowerCase();
    phoneNumber = normalize(phoneNumber);
    mobileNumber = normalize(mobileNumber);

    if (email.isBlank() && phoneNumber.isBlank() && mobileNumber.isBlank()) {
      throw new IllegalArgumentException("At least one contact value is required.");
    }

    if (!email.isBlank() && !EMAIL_PATTERN.matcher(email).matches()) {
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
