package it.gabriele.truckflow.application.port.out.shipments;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.shipments.core.Shipment;
import it.gabriele.truckflow.domain.shipments.core.ShipmentCode;
import it.gabriele.truckflow.domain.shipments.core.ShipmentId;
import java.util.Optional;

/** Outbound repository port used by shipment use cases. */
public interface ShipmentRepository extends RepositoryPort {

  Shipment save(Shipment shipment);

  Optional<Shipment> findById(ShipmentId id);

  Optional<Shipment> findByCode(ShipmentCode code);

  boolean existsById(ShipmentId id);

  boolean existsByCode(ShipmentCode code);
}
