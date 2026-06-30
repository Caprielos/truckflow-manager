package it.gabriele.truckflow.domain.compliance;

import it.gabriele.truckflow.domain.compliance.exceptions.InvalidComplianceRequirementException;

public record ComplianceRequirementCode(String value) {

  public ComplianceRequirementCode {
    value = ComplianceValidation.requireText(value, "value").toUpperCase();

    if (!value.matches("[A-Z0-9][A-Z0-9_-]*")) {
      throw new InvalidComplianceRequirementException(
          "Compliance requirement code can contain only uppercase letters, numbers, dashes and"
              + " underscores.");
    }
  }

  public static ComplianceRequirementCode of(String value) {
    return new ComplianceRequirementCode(value);
  }
}
