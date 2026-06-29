package it.gabriele.truckflow.infrastructure.memory.economics;

import it.gabriele.truckflow.application.port.out.economics.FleetAssetPurchaseRepository;
import it.gabriele.truckflow.domain.economics.FleetAssetPurchase;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per FleetAssetPurchase. */
public final class InMemoryFleetAssetPurchaseRepository
    extends InMemoryRepository<FleetAssetPurchase> implements FleetAssetPurchaseRepository {

  public InMemoryFleetAssetPurchaseRepository() {
    super(item -> item.getAssetCode());
  }
}
