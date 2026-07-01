package it.gabriele.truckflow.application.usecase.compliance;

import it.gabriele.truckflow.application.command.compliance.ArchiveComplianceRequirementCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.compliance.ArchiveComplianceRequirementUseCase;
import it.gabriele.truckflow.application.port.out.compliance.ComplianceRequirementRepository;
import it.gabriele.truckflow.application.result.compliance.ComplianceRequirementResult;

/** Application service that archives compliance requirement catalog entries. */
public final class ArchiveComplianceRequirementService
    implements ArchiveComplianceRequirementUseCase {

  private final ComplianceRequirementRepository complianceRequirementRepository;

  public ArchiveComplianceRequirementService(
      ComplianceRequirementRepository complianceRequirementRepository) {
    UseCaseValidationException.requireNonNull(
        complianceRequirementRepository, "complianceRequirementRepository");
    this.complianceRequirementRepository = complianceRequirementRepository;
  }

  @Override
  public ComplianceRequirementResult execute(ArchiveComplianceRequirementCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var requirement =
        complianceRequirementRepository
            .findById(command.requirementId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "ComplianceRequirement", command.requirementId()));

    var updatedRequirement = ComplianceRequirementMutationSupport.copyOf(requirement);
    updatedRequirement.archive();

    return ComplianceRequirementResult.from(
        complianceRequirementRepository.save(updatedRequirement));
  }
}
