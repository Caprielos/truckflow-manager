package it.gabriele.truckflow.infrastructure.memory.shipment;

import it.gabriele.truckflow.application.port.out.shipment.ShipmentRepository;
import it.gabriele.truckflow.domain.shipment.Shipment;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per Shipment. */
public final class InMemoryShipmentRepository extends InMemoryRepository<Shipment>
    implements ShipmentRepository {

  public InMemoryShipmentRepository() {
    super(item -> item.getShipmentNumber());
  }
}
