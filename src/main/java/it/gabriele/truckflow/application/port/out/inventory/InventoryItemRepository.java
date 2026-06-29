package it.gabriele.truckflow.application.port.out.inventory;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.inventory.InventoryItem;

/** Repository port per InventoryItem. L'implementazione sarà in infrastructure. */
public interface InventoryItemRepository extends RepositoryPort<InventoryItem> {}
