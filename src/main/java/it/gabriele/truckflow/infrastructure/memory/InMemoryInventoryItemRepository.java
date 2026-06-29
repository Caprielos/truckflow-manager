package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.InventoryItemRepository;
import it.gabriele.truckflow.domain.inventory.InventoryItem;

/** Repository in memoria per InventoryItem. */
public final class InMemoryInventoryItemRepository extends InMemoryRepository<InventoryItem> implements InventoryItemRepository {

    public InMemoryInventoryItemRepository() {
        super(item -> item.getItemCode());
    }
}
