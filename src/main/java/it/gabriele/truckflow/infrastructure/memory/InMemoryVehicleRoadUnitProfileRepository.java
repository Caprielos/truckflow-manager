package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.VehicleRoadUnitProfileRepository;
import it.gabriele.truckflow.domain.roadtransport.VehicleRoadUnitProfile;

/** Repository in memoria per VehicleRoadUnitProfile. */
public final class InMemoryVehicleRoadUnitProfileRepository
    extends InMemoryRepository<VehicleRoadUnitProfile> implements VehicleRoadUnitProfileRepository {

  public InMemoryVehicleRoadUnitProfileRepository() {
    super(profile -> profile.unitCode());
  }
}
