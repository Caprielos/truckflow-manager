package it.gabriele.truckflow.domain.users;

public record UserPasswordHash(String value) {

  public UserPasswordHash {
    value = requireText(value, "passwordHash");
  }

  private static String requireText(String value, String fieldName) {
    String normalized = value == null ? "" : value.trim();

    if (normalized.isBlank()) {
      throw new IllegalArgumentException(fieldName + " is required.");
    }

    return normalized;
  }
}
