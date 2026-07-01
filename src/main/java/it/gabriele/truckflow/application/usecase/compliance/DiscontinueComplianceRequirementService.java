package it.gabriele.truckflow.application.usecase.compliance;

import it.gabriele.truckflow.application.command.compliance.DiscontinueComplianceRequirementCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.compliance.DiscontinueComplianceRequirementUseCase;
import it.gabriele.truckflow.application.port.out.compliance.ComplianceRequirementRepository;
import it.gabriele.truckflow.application.result.compliance.ComplianceRequirementResult;

/** Application service that discontinues compliance requirement catalog entries. */
public final class DiscontinueComplianceRequirementService
    implements DiscontinueComplianceRequirementUseCase {

  private final ComplianceRequirementRepository complianceRequirementRepository;

  public DiscontinueComplianceRequirementService(
      ComplianceRequirementRepository complianceRequirementRepository) {
    UseCaseValidationException.requireNonNull(
        complianceRequirementRepository, "complianceRequirementRepository");
    this.complianceRequirementRepository = complianceRequirementRepository;
  }

  @Override
  public ComplianceRequirementResult execute(DiscontinueComplianceRequirementCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var requirement =
        complianceRequirementRepository
            .findById(command.requirementId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "ComplianceRequirement", command.requirementId()));

    var updatedRequirement = ComplianceRequirementMutationSupport.copyOf(requirement);
    updatedRequirement.discontinue();

    return ComplianceRequirementResult.from(
        complianceRequirementRepository.save(updatedRequirement));
  }
}
