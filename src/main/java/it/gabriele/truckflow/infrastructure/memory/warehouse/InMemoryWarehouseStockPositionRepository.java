package it.gabriele.truckflow.infrastructure.memory.warehouse;

import it.gabriele.truckflow.application.port.out.WarehouseStockPositionRepository;
import it.gabriele.truckflow.domain.warehouse.StockPosition;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per StockPosition. */
public final class InMemoryWarehouseStockPositionRepository
    extends InMemoryRepository<StockPosition> implements WarehouseStockPositionRepository {

  public InMemoryWarehouseStockPositionRepository() {
    super(stock -> stock.itemCode() + "_" + stock.batchCode() + "_" + stock.locationCode());
  }
}
