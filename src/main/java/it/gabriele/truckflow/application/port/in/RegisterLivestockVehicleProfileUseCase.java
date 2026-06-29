package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.livestock.LivestockVehicleProfile;

public interface RegisterLivestockVehicleProfileUseCase {
  LivestockVehicleProfile handle(Command command);

  record Command(LivestockVehicleProfile profile) {}
}
