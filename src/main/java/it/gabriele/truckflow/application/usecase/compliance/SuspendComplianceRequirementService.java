package it.gabriele.truckflow.application.usecase.compliance;

import it.gabriele.truckflow.application.command.compliance.SuspendComplianceRequirementCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.compliance.SuspendComplianceRequirementUseCase;
import it.gabriele.truckflow.application.port.out.compliance.ComplianceRequirementRepository;
import it.gabriele.truckflow.application.result.compliance.ComplianceRequirementResult;

/** Application service that suspends compliance requirement catalog entries. */
public final class SuspendComplianceRequirementService
    implements SuspendComplianceRequirementUseCase {

  private final ComplianceRequirementRepository complianceRequirementRepository;

  public SuspendComplianceRequirementService(
      ComplianceRequirementRepository complianceRequirementRepository) {
    UseCaseValidationException.requireNonNull(
        complianceRequirementRepository, "complianceRequirementRepository");
    this.complianceRequirementRepository = complianceRequirementRepository;
  }

  @Override
  public ComplianceRequirementResult execute(SuspendComplianceRequirementCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var requirement =
        complianceRequirementRepository
            .findById(command.requirementId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "ComplianceRequirement", command.requirementId()));

    var updatedRequirement = ComplianceRequirementMutationSupport.copyOf(requirement);
    updatedRequirement.suspend();

    return ComplianceRequirementResult.from(
        complianceRequirementRepository.save(updatedRequirement));
  }
}
