package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.roadtransport.VehicleRoadUnitProfile;

public interface RegisterVehicleRoadUnitProfileUseCase {
  VehicleRoadUnitProfile handle(Command command);

  record Command(VehicleRoadUnitProfile profile) {}
}
