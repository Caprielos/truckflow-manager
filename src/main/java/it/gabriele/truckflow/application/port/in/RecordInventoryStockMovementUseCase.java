package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.inventory.InventoryBalance;
import it.gabriele.truckflow.domain.inventory.InventoryStockMovement;

public interface RecordInventoryStockMovementUseCase {

    InventoryBalance handle(Command command);

    record Command(String itemCode, String locationId, InventoryStockMovement movement) {
    }
}
