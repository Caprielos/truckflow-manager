package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;
import it.gabriele.truckflow.domain.regulation.RegulatoryRequirementCode;

public interface EvaluateRegulatoryRequirementUseCase {
  EnterpriseValidationResult handle(Command command);

  record Command(String tenantCode, RegulatoryRequirementCode requirementCode) {}
}
