package it.gabriele.truckflow.application.port.in.compliance;

import it.gabriele.truckflow.application.command.compliance.ArchiveComplianceRequirementCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.compliance.ComplianceRequirementResult;

/** Inbound port for archiving compliance requirements. */
public interface ArchiveComplianceRequirementUseCase
    extends UseCase<ArchiveComplianceRequirementCommand, ComplianceRequirementResult> {}
