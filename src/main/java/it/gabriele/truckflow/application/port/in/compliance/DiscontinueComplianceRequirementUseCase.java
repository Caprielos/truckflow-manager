package it.gabriele.truckflow.application.port.in.compliance;

import it.gabriele.truckflow.application.command.compliance.DiscontinueComplianceRequirementCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.compliance.ComplianceRequirementResult;

/** Inbound port for discontinuing compliance requirements. */
public interface DiscontinueComplianceRequirementUseCase
    extends UseCase<DiscontinueComplianceRequirementCommand, ComplianceRequirementResult> {}
