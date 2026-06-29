package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.CrossDockFlowRepository;
import it.gabriele.truckflow.domain.warehouse.CrossDockFlow;

/** Repository in memoria per CrossDockFlow. */
public final class InMemoryCrossDockFlowRepository extends InMemoryRepository<CrossDockFlow>
    implements CrossDockFlowRepository {

  public InMemoryCrossDockFlowRepository() {
    super(flow -> flow.flowCode());
  }
}
