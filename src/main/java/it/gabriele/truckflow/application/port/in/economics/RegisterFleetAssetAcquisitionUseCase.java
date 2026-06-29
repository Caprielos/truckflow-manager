package it.gabriele.truckflow.application.port.in.economics;

import it.gabriele.truckflow.domain.economics.FleetAssetAcquisition;

public interface RegisterFleetAssetAcquisitionUseCase {

  FleetAssetAcquisition handle(Command command);

  record Command(FleetAssetAcquisition acquisition) {}
}
