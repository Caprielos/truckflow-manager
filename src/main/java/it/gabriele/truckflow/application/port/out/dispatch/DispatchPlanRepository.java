package it.gabriele.truckflow.application.port.out.dispatch;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.dispatch.DispatchPlan;

/** Repository port per DispatchPlan. L'implementazione sarà in infrastructure. */
public interface DispatchPlanRepository extends RepositoryPort<DispatchPlan> {}
