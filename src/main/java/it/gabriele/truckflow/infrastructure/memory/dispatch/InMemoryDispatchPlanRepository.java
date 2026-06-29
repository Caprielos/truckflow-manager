package it.gabriele.truckflow.infrastructure.memory.dispatch;

import it.gabriele.truckflow.application.port.out.dispatch.DispatchPlanRepository;
import it.gabriele.truckflow.domain.dispatch.DispatchPlan;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per DispatchPlan. */
public final class InMemoryDispatchPlanRepository extends InMemoryRepository<DispatchPlan>
    implements DispatchPlanRepository {

  public InMemoryDispatchPlanRepository() {
    super(item -> item.getPlanCode());
  }
}
