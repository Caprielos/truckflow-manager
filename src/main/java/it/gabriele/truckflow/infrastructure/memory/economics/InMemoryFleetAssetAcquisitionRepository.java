package it.gabriele.truckflow.infrastructure.memory.economics;

import it.gabriele.truckflow.application.port.out.FleetAssetAcquisitionRepository;
import it.gabriele.truckflow.domain.economics.FleetAssetAcquisition;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per FleetAssetAcquisition. */
public final class InMemoryFleetAssetAcquisitionRepository
    extends InMemoryRepository<FleetAssetAcquisition> implements FleetAssetAcquisitionRepository {

  public InMemoryFleetAssetAcquisitionRepository() {
    super(item -> item.getAcquisitionNumber());
  }
}
