package it.gabriele.truckflow.infrastructure.memory.fleet;

import it.gabriele.truckflow.application.port.out.VehicleCombinationRepository;
import it.gabriele.truckflow.domain.fleet.VehicleCombination;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per VehicleCombination. */
public final class InMemoryVehicleCombinationRepository
    extends InMemoryRepository<VehicleCombination> implements VehicleCombinationRepository {

  public InMemoryVehicleCombinationRepository() {
    super(item -> item.getCombinationNumber());
  }
}
