package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.InventoryStockMovementRepository;
import it.gabriele.truckflow.domain.inventory.InventoryStockMovement;

/** Repository in memoria per InventoryStockMovement. */
public final class InMemoryInventoryStockMovementRepository
    extends InMemoryRepository<InventoryStockMovement> implements InventoryStockMovementRepository {

  public InMemoryInventoryStockMovementRepository() {
    super(item -> item.getMovementCode());
  }
}
