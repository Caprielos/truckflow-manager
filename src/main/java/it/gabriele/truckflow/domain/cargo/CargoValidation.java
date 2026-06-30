package it.gabriele.truckflow.domain.cargo;

import it.gabriele.truckflow.domain.cargo.exceptions.InvalidCargoException;
import java.math.BigDecimal;
import java.util.Collection;

final class CargoValidation {

  private CargoValidation() {}

  static <T> T requireNonNull(T value, String fieldName) {
    if (value == null) {
      throw new InvalidCargoException(fieldName + " is required.");
    }

    return value;
  }

  static String requireText(String value, String fieldName) {
    String normalized = normalize(value);

    if (normalized.isBlank()) {
      throw new InvalidCargoException(fieldName + " is required.");
    }

    return normalized;
  }

  static String normalize(String value) {
    return value == null ? "" : value.trim();
  }

  static BigDecimal nonNegativeOrNull(BigDecimal value, String fieldName) {
    if (value != null && value.signum() < 0) {
      throw new InvalidCargoException(fieldName + " cannot be negative.");
    }

    return value;
  }

  static Integer nonNegativeOrNull(Integer value, String fieldName) {
    if (value != null && value < 0) {
      throw new InvalidCargoException(fieldName + " cannot be negative.");
    }

    return value;
  }

  static <T> void requireNoNullElements(Collection<T> values, String fieldName) {
    if (values.stream().anyMatch(value -> value == null)) {
      throw new InvalidCargoException(fieldName + " cannot contain null values.");
    }
  }
}
