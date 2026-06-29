package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.VehicleRepository;
import it.gabriele.truckflow.domain.fleet.Vehicle;

/** Repository in memoria per Vehicle. */
public final class InMemoryVehicleRepository extends InMemoryRepository<Vehicle>
    implements VehicleRepository {

  public InMemoryVehicleRepository() {
    super(item -> item.getFleetNumber());
  }
}
