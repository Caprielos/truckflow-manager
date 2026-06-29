package it.gabriele.truckflow.infrastructure.memory.inventory;

import it.gabriele.truckflow.application.port.out.InventoryStockMovementRepository;
import it.gabriele.truckflow.domain.inventory.InventoryStockMovement;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per InventoryStockMovement. */
public final class InMemoryInventoryStockMovementRepository
    extends InMemoryRepository<InventoryStockMovement> implements InventoryStockMovementRepository {

  public InMemoryInventoryStockMovementRepository() {
    super(item -> item.getMovementCode());
  }
}
