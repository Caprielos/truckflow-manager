package it.gabriele.truckflow.infrastructure.memory.livestock;

import it.gabriele.truckflow.application.port.out.livestock.LivestockTripPlanRepository;
import it.gabriele.truckflow.domain.livestock.LivestockTripPlan;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per LivestockTripPlan. */
public final class InMemoryLivestockTripPlanRepository extends InMemoryRepository<LivestockTripPlan>
    implements LivestockTripPlanRepository {

  public InMemoryLivestockTripPlanRepository() {
    super(plan -> plan.tripCode());
  }
}
