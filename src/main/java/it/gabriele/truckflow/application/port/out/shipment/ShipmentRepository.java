package it.gabriele.truckflow.application.port.out.shipment;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.shipment.Shipment;

/** Repository port per Shipment. L'implementazione sarà in infrastructure. */
public interface ShipmentRepository extends RepositoryPort<Shipment> {}
