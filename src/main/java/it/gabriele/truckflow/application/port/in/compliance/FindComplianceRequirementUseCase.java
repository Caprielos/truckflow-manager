package it.gabriele.truckflow.application.port.in.compliance;

import it.gabriele.truckflow.application.command.compliance.FindComplianceRequirementCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.compliance.ComplianceRequirementResult;

/** Inbound port for finding compliance requirements. */
public interface FindComplianceRequirementUseCase
    extends UseCase<FindComplianceRequirementCommand, ComplianceRequirementResult> {}
