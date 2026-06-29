package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.LoadingDockRepository;
import it.gabriele.truckflow.domain.warehouse.LoadingDock;

/** Repository in memoria per LoadingDock. */
public final class InMemoryLoadingDockRepository extends InMemoryRepository<LoadingDock>
    implements LoadingDockRepository {

  public InMemoryLoadingDockRepository() {
    super(dock -> dock.dockCode());
  }
}
