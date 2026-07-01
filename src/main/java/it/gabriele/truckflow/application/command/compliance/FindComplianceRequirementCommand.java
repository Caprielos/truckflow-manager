package it.gabriele.truckflow.application.command.compliance;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementId;

/** Command used to find a compliance requirement by identity. */
public record FindComplianceRequirementCommand(ComplianceRequirementId requirementId)
    implements ApplicationCommand {

  public FindComplianceRequirementCommand {
    UseCaseValidationException.requireNonNull(requirementId, "requirementId");
  }
}
