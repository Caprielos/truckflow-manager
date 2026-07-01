package it.gabriele.truckflow.domain.operational.common;

import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;

public record OperationalScopeCode(String value) {

  public OperationalScopeCode {
    value = normalize(value).toUpperCase();

    if (value.isBlank()) {
      throw new DomainValidationException("Operational scope code is required.");
    }

    if (!value.matches("[A-Z0-9][A-Z0-9_-]*")) {
      throw new DomainValidationException(
          "Operational scope code can contain only uppercase letters, numbers, dashes "
              + "and underscores.");
    }
  }

  public static OperationalScopeCode of(String value) {
    return new OperationalScopeCode(value);
  }

  private static String normalize(String value) {
    return value == null ? "" : value.trim();
  }
}
