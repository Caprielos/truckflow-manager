package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.LivestockVehicleProfileRepository;
import it.gabriele.truckflow.domain.livestock.LivestockVehicleProfile;

/** Repository in memoria per LivestockVehicleProfile. */
public final class InMemoryLivestockVehicleProfileRepository
    extends InMemoryRepository<LivestockVehicleProfile>
    implements LivestockVehicleProfileRepository {

  public InMemoryLivestockVehicleProfileRepository() {
    super(profile -> profile.vehicleCode());
  }
}
