package it.gabriele.truckflow.application.command.compliance;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.compliance.ComplianceCategory;
import it.gabriele.truckflow.domain.compliance.ComplianceJurisdiction;
import it.gabriele.truckflow.domain.compliance.ComplianceObligationLevel;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementCode;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementStatus;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementType;
import it.gabriele.truckflow.domain.compliance.ComplianceRule;
import it.gabriele.truckflow.domain.compliance.ComplianceSeverity;
import it.gabriele.truckflow.domain.compliance.ComplianceSource;
import it.gabriele.truckflow.domain.compliance.ComplianceTarget;

/** Command used to register a base compliance requirement catalog entry. */
public record RegisterComplianceRequirementCommand(
    ComplianceRequirementCode code,
    String name,
    String description,
    ComplianceRequirementStatus status,
    ComplianceCategory category,
    ComplianceRequirementType type,
    ComplianceObligationLevel obligationLevel,
    ComplianceSeverity severity,
    ComplianceTarget target,
    ComplianceRule rule,
    ComplianceSource source,
    ComplianceJurisdiction jurisdiction,
    String notes)
    implements ApplicationCommand {

  public RegisterComplianceRequirementCommand {
    UseCaseValidationException.requireNonNull(code, "code");
    UseCaseValidationException.requireNotBlank(name, "name");
    name = name.trim();
    description = normalize(description);
    UseCaseValidationException.requireNonNull(status, "status");
    UseCaseValidationException.requireNonNull(category, "category");
    UseCaseValidationException.requireNonNull(type, "type");
    UseCaseValidationException.requireNonNull(obligationLevel, "obligationLevel");
    UseCaseValidationException.requireNonNull(severity, "severity");
    UseCaseValidationException.requireNonNull(target, "target");
    UseCaseValidationException.requireNonNull(rule, "rule");
    UseCaseValidationException.requireNonNull(source, "source");
    UseCaseValidationException.requireNonNull(jurisdiction, "jurisdiction");
    notes = normalize(notes);
  }

  private static String normalize(String value) {
    return value == null ? "" : value.trim();
  }
}
