package it.gabriele.truckflow.infrastructure.memory.routeoptimization;

import it.gabriele.truckflow.application.port.out.routeoptimization.RouteOptimizationPlanRepository;
import it.gabriele.truckflow.domain.routeoptimization.RouteOptimizationPlan;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per RouteOptimizationPlan. */
public final class InMemoryRouteOptimizationPlanRepository
    extends InMemoryRepository<RouteOptimizationPlan> implements RouteOptimizationPlanRepository {

  public InMemoryRouteOptimizationPlanRepository() {
    super(plan -> plan.planCode());
  }
}
