package it.gabriele.truckflow.application.usecase.compliance;

import it.gabriele.truckflow.application.command.compliance.RegisterComplianceRequirementCommand;
import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.compliance.RegisterComplianceRequirementUseCase;
import it.gabriele.truckflow.application.port.out.compliance.ComplianceRequirementRepository;
import it.gabriele.truckflow.application.result.compliance.ComplianceRequirementResult;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirement;

/** Application service that registers base compliance requirement catalog entries. */
public final class RegisterComplianceRequirementService
    implements RegisterComplianceRequirementUseCase {

  private final ComplianceRequirementRepository complianceRequirementRepository;

  public RegisterComplianceRequirementService(
      ComplianceRequirementRepository complianceRequirementRepository) {
    UseCaseValidationException.requireNonNull(
        complianceRequirementRepository, "complianceRequirementRepository");
    this.complianceRequirementRepository = complianceRequirementRepository;
  }

  @Override
  public ComplianceRequirementResult execute(RegisterComplianceRequirementCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    if (complianceRequirementRepository.existsByCode(command.code())) {
      throw new DuplicateResourceException("ComplianceRequirement", command.code().value());
    }

    var requirement =
        new ComplianceRequirement(
            null,
            command.code(),
            command.name(),
            command.description(),
            command.status(),
            command.category(),
            command.type(),
            command.obligationLevel(),
            command.severity(),
            command.target(),
            command.rule(),
            command.source(),
            command.jurisdiction(),
            command.notes());

    return ComplianceRequirementResult.from(complianceRequirementRepository.save(requirement));
  }
}
