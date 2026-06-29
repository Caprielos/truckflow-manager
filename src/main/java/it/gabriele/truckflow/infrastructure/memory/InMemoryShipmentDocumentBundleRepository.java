package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.ShipmentDocumentBundleRepository;
import it.gabriele.truckflow.domain.document.ShipmentDocumentBundle;

/** Repository in memoria per ShipmentDocumentBundle. */
public final class InMemoryShipmentDocumentBundleRepository extends InMemoryRepository<ShipmentDocumentBundle> implements ShipmentDocumentBundleRepository {

    public InMemoryShipmentDocumentBundleRepository() {
        super(item -> item.getBundleCode());
    }
}
