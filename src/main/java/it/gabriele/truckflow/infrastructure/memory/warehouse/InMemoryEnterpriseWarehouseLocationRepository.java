package it.gabriele.truckflow.infrastructure.memory.warehouse;

import it.gabriele.truckflow.application.port.out.warehouse.EnterpriseWarehouseLocationRepository;
import it.gabriele.truckflow.domain.warehouse.WarehouseLocation;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per WarehouseLocation. */
public final class InMemoryEnterpriseWarehouseLocationRepository
    extends InMemoryRepository<WarehouseLocation> implements EnterpriseWarehouseLocationRepository {

  public InMemoryEnterpriseWarehouseLocationRepository() {
    super(location -> location.locationCode());
  }
}
