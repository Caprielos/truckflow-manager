package it.gabriele.truckflow.domain.vehicles;

import java.math.BigDecimal;
import java.util.Collection;

final class VehicleValidation {

  private VehicleValidation() {}

  static <T> T requireNonNull(T value, String fieldName) {
    if (value == null) {
      throw new IllegalArgumentException(fieldName + " is required.");
    }

    return value;
  }

  static String requireText(String value, String fieldName) {
    String normalized = normalize(value);

    if (normalized.isBlank()) {
      throw new IllegalArgumentException(fieldName + " is required.");
    }

    return normalized;
  }

  static String normalize(String value) {
    return value == null ? "" : value.trim();
  }

  static BigDecimal requireNonNegative(BigDecimal value, String fieldName) {
    requireNonNull(value, fieldName);
    if (value.signum() < 0) {
      throw new IllegalArgumentException(fieldName + " cannot be negative.");
    }

    return value;
  }

  static BigDecimal nonNegativeOrNull(BigDecimal value, String fieldName) {
    if (value != null && value.signum() < 0) {
      throw new IllegalArgumentException(fieldName + " cannot be negative.");
    }

    return value;
  }

  static Integer nonNegativeOrNull(Integer value, String fieldName) {
    if (value != null && value < 0) {
      throw new IllegalArgumentException(fieldName + " cannot be negative.");
    }

    return value;
  }

  static int requirePositive(int value, String fieldName) {
    if (value <= 0) {
      throw new IllegalArgumentException(fieldName + " must be positive.");
    }

    return value;
  }

  static Integer positiveOrNull(Integer value, String fieldName) {
    if (value != null && value <= 0) {
      throw new IllegalArgumentException(fieldName + " must be positive.");
    }

    return value;
  }

  static <T> void requireNoNullElements(Collection<T> values, String fieldName) {
    if (values.stream().anyMatch(value -> value == null)) {
      throw new IllegalArgumentException(fieldName + " cannot contain null values.");
    }
  }
}
