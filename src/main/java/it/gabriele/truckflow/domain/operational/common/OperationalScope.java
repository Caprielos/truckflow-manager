package it.gabriele.truckflow.domain.operational.common;

import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;

public record OperationalScope(
    OperationalScopeCode code, String name, String description, String area) {

  public OperationalScope {
    if (code == null) {
      throw new DomainValidationException("Operational scope code is required.");
    }

    name = requireText(name, "name");
    description = normalize(description);
    area = normalize(area);
  }

  public OperationalScope(String code, String name, String description, String area) {
    this(OperationalScopeCode.of(code), name, description, area);
  }

  public static OperationalScope of(String code, String name) {
    return new OperationalScope(OperationalScopeCode.of(code), name, "", "");
  }

  private static String requireText(String value, String fieldName) {
    String normalized = normalize(value);

    if (normalized.isBlank()) {
      throw new DomainValidationException(fieldName + " is required.");
    }

    return normalized;
  }

  private static String normalize(String value) {
    return value == null ? "" : value.trim();
  }
}
