package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.warehouse.LoadingDock;

public interface RegisterLoadingDockUseCase {
  LoadingDock handle(Command command);

  record Command(LoadingDock dock) {}
}
