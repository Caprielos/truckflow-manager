package it.gabriele.truckflow.application.usecase.compliance;

import it.gabriele.truckflow.application.command.compliance.FindComplianceRequirementCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.compliance.FindComplianceRequirementUseCase;
import it.gabriele.truckflow.application.port.out.compliance.ComplianceRequirementRepository;
import it.gabriele.truckflow.application.result.compliance.ComplianceRequirementResult;

/** Application service that finds compliance requirement catalog entries. */
public final class FindComplianceRequirementService implements FindComplianceRequirementUseCase {

  private final ComplianceRequirementRepository complianceRequirementRepository;

  public FindComplianceRequirementService(
      ComplianceRequirementRepository complianceRequirementRepository) {
    UseCaseValidationException.requireNonNull(
        complianceRequirementRepository, "complianceRequirementRepository");
    this.complianceRequirementRepository = complianceRequirementRepository;
  }

  @Override
  public ComplianceRequirementResult execute(FindComplianceRequirementCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    return complianceRequirementRepository
        .findById(command.requirementId())
        .map(ComplianceRequirementResult::from)
        .orElseThrow(
            () -> new ResourceNotFoundException("ComplianceRequirement", command.requirementId()));
  }
}
