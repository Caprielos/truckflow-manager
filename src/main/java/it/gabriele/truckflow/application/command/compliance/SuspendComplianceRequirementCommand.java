package it.gabriele.truckflow.application.command.compliance;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementId;

/** Command used to suspend a compliance requirement catalog entry. */
public record SuspendComplianceRequirementCommand(ComplianceRequirementId requirementId)
    implements ApplicationCommand {

  public SuspendComplianceRequirementCommand {
    UseCaseValidationException.requireNonNull(requirementId, "requirementId");
  }
}
