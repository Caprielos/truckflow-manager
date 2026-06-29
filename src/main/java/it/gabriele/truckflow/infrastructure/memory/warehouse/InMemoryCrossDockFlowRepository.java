package it.gabriele.truckflow.infrastructure.memory.warehouse;

import it.gabriele.truckflow.application.port.out.warehouse.CrossDockFlowRepository;
import it.gabriele.truckflow.domain.warehouse.CrossDockFlow;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per CrossDockFlow. */
public final class InMemoryCrossDockFlowRepository extends InMemoryRepository<CrossDockFlow>
    implements CrossDockFlowRepository {

  public InMemoryCrossDockFlowRepository() {
    super(flow -> flow.flowCode());
  }
}
