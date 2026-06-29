package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.WarehouseLocationRepository;
import it.gabriele.truckflow.domain.inventory.WarehouseLocation;

/** Repository in memoria per WarehouseLocation. */
public final class InMemoryWarehouseLocationRepository extends InMemoryRepository<WarehouseLocation>
    implements WarehouseLocationRepository {

  public InMemoryWarehouseLocationRepository() {
    super(item -> item.getFullCode());
  }
}
