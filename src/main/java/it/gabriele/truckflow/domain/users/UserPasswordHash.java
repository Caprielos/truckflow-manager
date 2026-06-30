package it.gabriele.truckflow.domain.users;

import it.gabriele.truckflow.domain.users.exceptions.InvalidUserException;

public record UserPasswordHash(String value) {

  public UserPasswordHash {
    value = requireText(value, "passwordHash");
  }

  private static String requireText(String value, String fieldName) {
    String normalized = value == null ? "" : value.trim();

    if (normalized.isBlank()) {
      throw new InvalidUserException(fieldName + " is required.");
    }

    return normalized;
  }
}
