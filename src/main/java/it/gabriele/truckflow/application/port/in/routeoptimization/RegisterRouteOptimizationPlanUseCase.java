package it.gabriele.truckflow.application.port.in.routeoptimization;

import it.gabriele.truckflow.domain.routeoptimization.RouteOptimizationPlan;

public interface RegisterRouteOptimizationPlanUseCase {
  RouteOptimizationPlan handle(Command command);

  record Command(RouteOptimizationPlan plan) {}
}
