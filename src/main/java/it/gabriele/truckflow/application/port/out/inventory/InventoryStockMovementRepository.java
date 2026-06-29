package it.gabriele.truckflow.application.port.out.inventory;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.inventory.InventoryStockMovement;

/** Repository port per InventoryStockMovement. L'implementazione sarà in infrastructure. */
public interface InventoryStockMovementRepository extends RepositoryPort<InventoryStockMovement> {}
