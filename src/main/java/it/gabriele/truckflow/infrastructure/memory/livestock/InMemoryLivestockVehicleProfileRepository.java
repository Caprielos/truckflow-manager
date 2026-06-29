package it.gabriele.truckflow.infrastructure.memory.livestock;

import it.gabriele.truckflow.application.port.out.livestock.LivestockVehicleProfileRepository;
import it.gabriele.truckflow.domain.livestock.LivestockVehicleProfile;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per LivestockVehicleProfile. */
public final class InMemoryLivestockVehicleProfileRepository
    extends InMemoryRepository<LivestockVehicleProfile>
    implements LivestockVehicleProfileRepository {

  public InMemoryLivestockVehicleProfileRepository() {
    super(profile -> profile.vehicleCode());
  }
}
