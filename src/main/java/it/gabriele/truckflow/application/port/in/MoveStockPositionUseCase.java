package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.warehouse.StockPosition;

public interface MoveStockPositionUseCase {
  StockPosition handle(Command command);

  record Command(StockPosition stock) {}
}
