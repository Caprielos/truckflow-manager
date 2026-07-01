package it.gabriele.truckflow.domain.users;

import it.gabriele.truckflow.domain.users.exceptions.InvalidUserException;
import java.util.Locale;

public record LanguageCode(String value) {

  public static final LanguageCode DEFAULT = new LanguageCode("EN");

  public LanguageCode {
    value = normalize(value);

    if (!value.matches("[A-Z]{2}")) {
      throw new InvalidUserException("Language code must use two uppercase letters.");
    }
  }

  public static LanguageCode of(String value) {
    return new LanguageCode(value);
  }

  static LanguageCode fromOrDefault(String value) {
    String normalized = value == null ? "" : value.trim();
    return normalized.isBlank() ? DEFAULT : new LanguageCode(normalized);
  }

  private static String normalize(String value) {
    if (value == null) {
      throw new InvalidUserException("Language code is required.");
    }

    return value.trim().toUpperCase(Locale.ROOT);
  }
}
