package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.LivestockTripPlanRepository;
import it.gabriele.truckflow.domain.livestock.LivestockTripPlan;

/** Repository in memoria per LivestockTripPlan. */
public final class InMemoryLivestockTripPlanRepository extends InMemoryRepository<LivestockTripPlan>
    implements LivestockTripPlanRepository {

  public InMemoryLivestockTripPlanRepository() {
    super(plan -> plan.tripCode());
  }
}
