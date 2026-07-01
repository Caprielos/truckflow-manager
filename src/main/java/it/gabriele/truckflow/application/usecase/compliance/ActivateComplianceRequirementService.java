package it.gabriele.truckflow.application.usecase.compliance;

import it.gabriele.truckflow.application.command.compliance.ActivateComplianceRequirementCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.compliance.ActivateComplianceRequirementUseCase;
import it.gabriele.truckflow.application.port.out.compliance.ComplianceRequirementRepository;
import it.gabriele.truckflow.application.result.compliance.ComplianceRequirementResult;

/** Application service that activates compliance requirement catalog entries. */
public final class ActivateComplianceRequirementService
    implements ActivateComplianceRequirementUseCase {

  private final ComplianceRequirementRepository complianceRequirementRepository;

  public ActivateComplianceRequirementService(
      ComplianceRequirementRepository complianceRequirementRepository) {
    UseCaseValidationException.requireNonNull(
        complianceRequirementRepository, "complianceRequirementRepository");
    this.complianceRequirementRepository = complianceRequirementRepository;
  }

  @Override
  public ComplianceRequirementResult execute(ActivateComplianceRequirementCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var requirement =
        complianceRequirementRepository
            .findById(command.requirementId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "ComplianceRequirement", command.requirementId()));

    var updatedRequirement = ComplianceRequirementMutationSupport.copyOf(requirement);
    updatedRequirement.activate();

    return ComplianceRequirementResult.from(
        complianceRequirementRepository.save(updatedRequirement));
  }
}
