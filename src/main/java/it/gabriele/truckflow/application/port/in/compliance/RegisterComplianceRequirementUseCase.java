package it.gabriele.truckflow.application.port.in.compliance;

import it.gabriele.truckflow.application.command.compliance.RegisterComplianceRequirementCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.compliance.ComplianceRequirementResult;

/** Inbound port for registering compliance requirements. */
public interface RegisterComplianceRequirementUseCase
    extends UseCase<RegisterComplianceRequirementCommand, ComplianceRequirementResult> {}
