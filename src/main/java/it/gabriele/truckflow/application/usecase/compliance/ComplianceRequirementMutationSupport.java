package it.gabriele.truckflow.application.usecase.compliance;

import it.gabriele.truckflow.domain.compliance.ComplianceRequirement;

/** Utility used by compliance requirement use cases to mutate copies before saving. */
final class ComplianceRequirementMutationSupport {

  private ComplianceRequirementMutationSupport() {}

  static ComplianceRequirement copyOf(ComplianceRequirement requirement) {
    return new ComplianceRequirement(
        requirement.id(),
        requirement.code(),
        requirement.name(),
        requirement.description(),
        requirement.status(),
        requirement.category(),
        requirement.type(),
        requirement.obligationLevel(),
        requirement.severity(),
        requirement.target(),
        requirement.rule(),
        requirement.source(),
        requirement.jurisdiction(),
        requirement.notes());
  }
}
