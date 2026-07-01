package it.gabriele.truckflow.application.port.in.compliance;

import it.gabriele.truckflow.application.command.compliance.SuspendComplianceRequirementCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.compliance.ComplianceRequirementResult;

/** Inbound port for suspending compliance requirements. */
public interface SuspendComplianceRequirementUseCase
    extends UseCase<SuspendComplianceRequirementCommand, ComplianceRequirementResult> {}
