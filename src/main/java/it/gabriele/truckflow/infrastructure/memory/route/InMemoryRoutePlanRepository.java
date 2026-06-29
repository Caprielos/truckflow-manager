package it.gabriele.truckflow.infrastructure.memory.route;

import it.gabriele.truckflow.application.port.out.route.RoutePlanRepository;
import it.gabriele.truckflow.domain.route.RoutePlan;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per RoutePlan. */
public final class InMemoryRoutePlanRepository extends InMemoryRepository<RoutePlan>
    implements RoutePlanRepository {

  public InMemoryRoutePlanRepository() {
    super(item -> item.getRouteNumber());
  }
}
