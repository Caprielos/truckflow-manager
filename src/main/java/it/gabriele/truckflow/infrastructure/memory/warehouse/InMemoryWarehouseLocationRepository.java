package it.gabriele.truckflow.infrastructure.memory.warehouse;

import it.gabriele.truckflow.application.port.out.WarehouseLocationRepository;
import it.gabriele.truckflow.domain.inventory.WarehouseLocation;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per WarehouseLocation. */
public final class InMemoryWarehouseLocationRepository extends InMemoryRepository<WarehouseLocation>
    implements WarehouseLocationRepository {

  public InMemoryWarehouseLocationRepository() {
    super(item -> item.getFullCode());
  }
}
