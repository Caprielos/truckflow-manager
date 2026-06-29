package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.RouteOptimizationPlanRepository;
import it.gabriele.truckflow.domain.routeoptimization.RouteOptimizationPlan;

/** Repository in memoria per RouteOptimizationPlan. */
public final class InMemoryRouteOptimizationPlanRepository
    extends InMemoryRepository<RouteOptimizationPlan> implements RouteOptimizationPlanRepository {

  public InMemoryRouteOptimizationPlanRepository() {
    super(plan -> plan.planCode());
  }
}
