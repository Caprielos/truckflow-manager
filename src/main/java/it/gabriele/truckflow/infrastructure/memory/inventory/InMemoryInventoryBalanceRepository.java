package it.gabriele.truckflow.infrastructure.memory.inventory;

import it.gabriele.truckflow.application.port.out.inventory.InventoryBalanceRepository;
import it.gabriele.truckflow.domain.inventory.InventoryBalance;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per InventoryBalance. */
public final class InMemoryInventoryBalanceRepository extends InMemoryRepository<InventoryBalance>
    implements InventoryBalanceRepository {

  public InMemoryInventoryBalanceRepository() {
    super(item -> item.getItem().getItemCode() + "@" + item.getLocation().getFullCode());
  }
}
