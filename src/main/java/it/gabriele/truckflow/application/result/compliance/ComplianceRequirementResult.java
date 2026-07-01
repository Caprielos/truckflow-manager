package it.gabriele.truckflow.application.result.compliance;

import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.result.ApplicationResult;
import it.gabriele.truckflow.domain.compliance.ComplianceCategory;
import it.gabriele.truckflow.domain.compliance.ComplianceJurisdictionScope;
import it.gabriele.truckflow.domain.compliance.ComplianceObligationLevel;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirement;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementCode;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementId;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementStatus;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementType;
import it.gabriele.truckflow.domain.compliance.ComplianceSeverity;
import it.gabriele.truckflow.domain.compliance.ComplianceTargetType;

/** Result returned by compliance requirement use cases. */
public record ComplianceRequirementResult(
    ComplianceRequirementId id,
    ComplianceRequirementCode code,
    String name,
    ComplianceRequirementStatus status,
    ComplianceCategory category,
    ComplianceRequirementType type,
    ComplianceObligationLevel obligationLevel,
    ComplianceSeverity severity,
    ComplianceTargetType targetType,
    ComplianceJurisdictionScope jurisdictionScope,
    String country,
    String region,
    boolean active,
    boolean mandatory,
    boolean critical)
    implements ApplicationResult {

  public static ComplianceRequirementResult from(ComplianceRequirement requirement) {
    UseCaseValidationException.requireNonNull(requirement, "requirement");

    return new ComplianceRequirementResult(
        requirement.id(),
        requirement.code(),
        requirement.name(),
        requirement.status(),
        requirement.category(),
        requirement.type(),
        requirement.obligationLevel(),
        requirement.severity(),
        requirement.target().targetType(),
        requirement.jurisdiction().scope(),
        requirement.jurisdiction().countryValue(),
        requirement.jurisdiction().regionValue(),
        requirement.isActive(),
        requirement.isMandatory(),
        requirement.isCritical());
  }
}
