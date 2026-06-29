package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.DispatchPlanRepository;
import it.gabriele.truckflow.domain.dispatch.DispatchPlan;

/** Repository in memoria per DispatchPlan. */
public final class InMemoryDispatchPlanRepository extends InMemoryRepository<DispatchPlan> implements DispatchPlanRepository {

    public InMemoryDispatchPlanRepository() {
        super(item -> item.getPlanCode());
    }
}
