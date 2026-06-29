package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.warehouse.CrossDockFlow;

public interface RegisterCrossDockFlowUseCase {
  CrossDockFlow handle(Command command);

  record Command(CrossDockFlow flow) {}
}
