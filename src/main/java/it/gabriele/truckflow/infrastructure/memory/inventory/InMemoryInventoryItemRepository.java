package it.gabriele.truckflow.infrastructure.memory.inventory;

import it.gabriele.truckflow.application.port.out.InventoryItemRepository;
import it.gabriele.truckflow.domain.inventory.InventoryItem;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per InventoryItem. */
public final class InMemoryInventoryItemRepository extends InMemoryRepository<InventoryItem>
    implements InventoryItemRepository {

  public InMemoryInventoryItemRepository() {
    super(item -> item.getItemCode());
  }
}
