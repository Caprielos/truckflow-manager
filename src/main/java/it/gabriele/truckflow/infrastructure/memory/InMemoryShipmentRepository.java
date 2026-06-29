package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.ShipmentRepository;
import it.gabriele.truckflow.domain.shipment.Shipment;

/** Repository in memoria per Shipment. */
public final class InMemoryShipmentRepository extends InMemoryRepository<Shipment>
    implements ShipmentRepository {

  public InMemoryShipmentRepository() {
    super(item -> item.getShipmentNumber());
  }
}
