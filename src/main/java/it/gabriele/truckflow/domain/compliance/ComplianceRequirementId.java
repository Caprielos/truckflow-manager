package it.gabriele.truckflow.domain.compliance;

import java.util.UUID;

public record ComplianceRequirementId(UUID value) {

  public ComplianceRequirementId {
    value = ComplianceValidation.requireNonNull(value, "value");
  }

  public static ComplianceRequirementId random() {
    return new ComplianceRequirementId(UUID.randomUUID());
  }
}
