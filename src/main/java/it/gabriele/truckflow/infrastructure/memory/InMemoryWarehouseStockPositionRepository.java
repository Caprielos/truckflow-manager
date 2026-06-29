package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.WarehouseStockPositionRepository;
import it.gabriele.truckflow.domain.warehouse.StockPosition;

/** Repository in memoria per StockPosition. */
public final class InMemoryWarehouseStockPositionRepository
    extends InMemoryRepository<StockPosition> implements WarehouseStockPositionRepository {

  public InMemoryWarehouseStockPositionRepository() {
    super(stock -> stock.itemCode() + "_" + stock.batchCode() + "_" + stock.locationCode());
  }
}
