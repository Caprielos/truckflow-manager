package it.gabriele.truckflow.infrastructure.memory.fleet;

import it.gabriele.truckflow.application.port.out.VehicleRepository;
import it.gabriele.truckflow.domain.fleet.Vehicle;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per Vehicle. */
public final class InMemoryVehicleRepository extends InMemoryRepository<Vehicle>
    implements VehicleRepository {

  public InMemoryVehicleRepository() {
    super(item -> item.getFleetNumber());
  }
}
