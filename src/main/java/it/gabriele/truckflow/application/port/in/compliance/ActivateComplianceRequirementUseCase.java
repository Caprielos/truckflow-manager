package it.gabriele.truckflow.application.port.in.compliance;

import it.gabriele.truckflow.application.command.compliance.ActivateComplianceRequirementCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.compliance.ComplianceRequirementResult;

/** Inbound port for activating compliance requirements. */
public interface ActivateComplianceRequirementUseCase
    extends UseCase<ActivateComplianceRequirementCommand, ComplianceRequirementResult> {}
