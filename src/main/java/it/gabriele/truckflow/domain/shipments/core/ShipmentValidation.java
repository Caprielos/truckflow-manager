package it.gabriele.truckflow.domain.shipments.core;

import java.math.BigDecimal;
import java.util.Collection;

public final class ShipmentValidation {

  private ShipmentValidation() {}

  public static <T> T requireNonNull(T value, String fieldName) {
    if (value == null) {
      throw new IllegalArgumentException(fieldName + " is required.");
    }

    return value;
  }

  public static String requireText(String value, String fieldName) {
    String normalized = normalize(value);

    if (normalized.isBlank()) {
      throw new IllegalArgumentException(fieldName + " is required.");
    }

    return normalized;
  }

  public static String normalize(String value) {
    return value == null ? "" : value.trim();
  }

  public static BigDecimal requirePositive(BigDecimal value, String fieldName) {
    value = requireNonNull(value, fieldName);

    if (value.signum() <= 0) {
      throw new IllegalArgumentException(fieldName + " must be positive.");
    }

    return value;
  }

  public static int requirePositive(int value, String fieldName) {
    if (value <= 0) {
      throw new IllegalArgumentException(fieldName + " must be positive.");
    }

    return value;
  }

  public static BigDecimal requireNonNegative(BigDecimal value, String fieldName) {
    value = requireNonNull(value, fieldName);

    if (value.signum() < 0) {
      throw new IllegalArgumentException(fieldName + " cannot be negative.");
    }

    return value;
  }

  public static BigDecimal nonNegativeOrNull(BigDecimal value, String fieldName) {
    if (value != null && value.signum() < 0) {
      throw new IllegalArgumentException(fieldName + " cannot be negative.");
    }

    return value;
  }

  public static <T> void requireNoNullElements(Collection<T> values, String fieldName) {
    if (values.stream().anyMatch(value -> value == null)) {
      throw new IllegalArgumentException(fieldName + " cannot contain null values.");
    }
  }
}
