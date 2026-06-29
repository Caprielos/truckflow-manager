package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.InventoryBalanceRepository;
import it.gabriele.truckflow.domain.inventory.InventoryBalance;

/** Repository in memoria per InventoryBalance. */
public final class InMemoryInventoryBalanceRepository extends InMemoryRepository<InventoryBalance> implements InventoryBalanceRepository {

    public InMemoryInventoryBalanceRepository() {
        super(item -> item.getItem().getItemCode() + "@" + item.getLocation().getFullCode());
    }
}
