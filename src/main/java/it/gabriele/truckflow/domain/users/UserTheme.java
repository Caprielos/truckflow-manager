package it.gabriele.truckflow.domain.users;

import it.gabriele.truckflow.domain.users.exceptions.InvalidUserException;
import java.util.Locale;

public enum UserTheme {
  LIGHT,
  DARK,
  SYSTEM;

  public static final UserTheme DEFAULT = LIGHT;

  public static UserTheme from(String value) {
    String normalized = normalize(value);

    try {
      return UserTheme.valueOf(normalized);
    } catch (IllegalArgumentException exception) {
      throw new InvalidUserException("User theme must be LIGHT, DARK or SYSTEM.");
    }
  }

  static UserTheme fromOrDefault(String value) {
    String normalized = value == null ? "" : value.trim();
    return normalized.isBlank() ? DEFAULT : from(normalized);
  }

  private static String normalize(String value) {
    if (value == null || value.isBlank()) {
      throw new InvalidUserException("User theme is required.");
    }

    return value.trim().toUpperCase(Locale.ROOT);
  }
}
