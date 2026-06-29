package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.FleetAssetPurchaseRepository;
import it.gabriele.truckflow.domain.economics.FleetAssetPurchase;

/** Repository in memoria per FleetAssetPurchase. */
public final class InMemoryFleetAssetPurchaseRepository
    extends InMemoryRepository<FleetAssetPurchase> implements FleetAssetPurchaseRepository {

  public InMemoryFleetAssetPurchaseRepository() {
    super(item -> item.getAssetCode());
  }
}
