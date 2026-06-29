package it.gabriele.truckflow.application.port.in.routeoptimization;

import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;

public interface EvaluateRouteOptimizationPlanUseCase {
  EnterpriseValidationResult handle(Command command);

  record Command(String planCode) {}
}
