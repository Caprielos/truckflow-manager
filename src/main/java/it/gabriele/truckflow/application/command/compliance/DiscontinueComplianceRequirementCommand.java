package it.gabriele.truckflow.application.command.compliance;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementId;

/** Command used to discontinue a compliance requirement catalog entry. */
public record DiscontinueComplianceRequirementCommand(ComplianceRequirementId requirementId)
    implements ApplicationCommand {

  public DiscontinueComplianceRequirementCommand {
    UseCaseValidationException.requireNonNull(requirementId, "requirementId");
  }
}
