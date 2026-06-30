package it.gabriele.truckflow.domain.operational.common;

import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;

public record OperationalCode(String value) {

  public OperationalCode {
    value = normalize(value).toUpperCase();

    if (value.isBlank()) {
      throw new DomainValidationException("Operational code is required.");
    }

    if (!value.matches("[A-Z0-9][A-Z0-9_-]*")) {
      throw new DomainValidationException(
          "Operational code can contain only uppercase letters, numbers, dashes and underscores.");
    }
  }

  public static OperationalCode of(String value) {
    return new OperationalCode(value);
  }

  public boolean isAssigned() {
    return true;
  }

  private static String normalize(String value) {
    return value == null ? "" : value.trim();
  }
}
