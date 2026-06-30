package it.gabriele.truckflow.domain.locations;

import it.gabriele.truckflow.domain.locations.exceptions.InvalidLocationException;
import java.math.BigDecimal;

final class LocationValidation {

  private LocationValidation() {}

  static <T> T requireNonNull(T value, String fieldName) {
    if (value == null) {
      throw new InvalidLocationException(fieldName + " is required.");
    }

    return value;
  }

  static String requireText(String value, String fieldName) {
    String normalized = normalize(value);

    if (normalized.isBlank()) {
      throw new InvalidLocationException(fieldName + " is required.");
    }

    return normalized;
  }

  static String normalize(String value) {
    return value == null ? "" : value.trim();
  }

  static BigDecimal requireInRange(
      BigDecimal value, BigDecimal min, BigDecimal max, String fieldName) {
    requireNonNull(value, fieldName);

    if (value.compareTo(min) < 0 || value.compareTo(max) > 0) {
      throw new InvalidLocationException(fieldName + " is outside the allowed range.");
    }

    return value;
  }
}
