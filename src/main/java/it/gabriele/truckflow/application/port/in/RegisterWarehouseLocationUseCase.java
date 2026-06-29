package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.warehouse.WarehouseLocation;

public interface RegisterWarehouseLocationUseCase {
  WarehouseLocation handle(Command command);

  record Command(WarehouseLocation location) {}
}
