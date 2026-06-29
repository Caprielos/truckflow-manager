package it.gabriele.truckflow.infrastructure.memory.document;

import it.gabriele.truckflow.application.port.out.ShipmentDocumentBundleRepository;
import it.gabriele.truckflow.domain.document.ShipmentDocumentBundle;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per ShipmentDocumentBundle. */
public final class InMemoryShipmentDocumentBundleRepository
    extends InMemoryRepository<ShipmentDocumentBundle> implements ShipmentDocumentBundleRepository {

  public InMemoryShipmentDocumentBundleRepository() {
    super(item -> item.getBundleCode());
  }
}
