package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.EnterpriseWarehouseLocationRepository;
import it.gabriele.truckflow.domain.warehouse.WarehouseLocation;

/** Repository in memoria per WarehouseLocation. */
public final class InMemoryEnterpriseWarehouseLocationRepository
    extends InMemoryRepository<WarehouseLocation> implements EnterpriseWarehouseLocationRepository {

  public InMemoryEnterpriseWarehouseLocationRepository() {
    super(location -> location.locationCode());
  }
}
