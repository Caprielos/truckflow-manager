package it.gabriele.truckflow.domain.operational.common;

import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;

public record OperationalScope(String code, String name, String description, String area) {

  public OperationalScope {
    code = requireText(code, "code").toUpperCase();
    name = requireText(name, "name");
    description = normalize(description);
    area = normalize(area);
  }

  public static OperationalScope of(String code, String name) {
    return new OperationalScope(code, name, "", "");
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
