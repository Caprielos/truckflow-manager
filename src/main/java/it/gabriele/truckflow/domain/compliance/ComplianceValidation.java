package it.gabriele.truckflow.domain.compliance;

import it.gabriele.truckflow.domain.compliance.exceptions.InvalidComplianceRequirementException;

final class ComplianceValidation {

  private ComplianceValidation() {}

  static <T> T requireNonNull(T value, String fieldName) {
    if (value == null) {
      throw new InvalidComplianceRequirementException(fieldName + " is required.");
    }

    return value;
  }

  static String requireText(String value, String fieldName) {
    String normalized = normalize(value);

    if (normalized.isBlank()) {
      throw new InvalidComplianceRequirementException(fieldName + " is required.");
    }

    return normalized;
  }

  static String normalize(String value) {
    return value == null ? "" : value.trim();
  }
}
