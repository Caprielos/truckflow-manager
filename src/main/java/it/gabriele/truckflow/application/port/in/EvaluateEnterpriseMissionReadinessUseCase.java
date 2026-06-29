package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;
import it.gabriele.truckflow.domain.regulation.RegulatoryRequirementCode;
import java.util.Set;

public interface EvaluateEnterpriseMissionReadinessUseCase {
  EnterpriseValidationResult handle(Command command);

  record Command(
      String missionCode,
      String tenantCode,
      String vehicleUnitCode,
      String driverCode,
      Set<RegulatoryRequirementCode> requiredRegulatoryChecks) {}
}
