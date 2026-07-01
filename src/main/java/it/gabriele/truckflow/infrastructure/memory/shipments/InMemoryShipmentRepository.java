package it.gabriele.truckflow.infrastructure.memory.shipments;

import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.out.shipments.ShipmentRepository;
import it.gabriele.truckflow.domain.shipments.core.Shipment;
import it.gabriele.truckflow.domain.shipments.core.ShipmentCode;
import it.gabriele.truckflow.domain.shipments.core.ShipmentId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** In-memory implementation of the shipment repository port. */
public final class InMemoryShipmentRepository implements ShipmentRepository {

  private final Map<ShipmentId, Shipment> shipmentsById = new HashMap<>();
  private final Map<ShipmentCode, ShipmentId> idsByCode = new HashMap<>();

  @Override
  public Shipment save(Shipment shipment) {
    UseCaseValidationException.requireNonNull(shipment, "shipment");

    ShipmentId existingId = idsByCode.get(shipment.code());
    if (existingId != null && !existingId.equals(shipment.id())) {
      throw new DuplicateResourceException("Shipment", shipment.code().value());
    }

    Shipment previousShipment = shipmentsById.put(shipment.id(), shipment);
    if (previousShipment != null && !previousShipment.code().equals(shipment.code())) {
      idsByCode.remove(previousShipment.code());
    }

    idsByCode.put(shipment.code(), shipment.id());
    return shipment;
  }

  @Override
  public Optional<Shipment> findById(ShipmentId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return Optional.ofNullable(shipmentsById.get(id));
  }

  @Override
  public Optional<Shipment> findByCode(ShipmentCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    ShipmentId id = idsByCode.get(code);
    return id == null ? Optional.empty() : findById(id);
  }

  @Override
  public boolean existsById(ShipmentId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return shipmentsById.containsKey(id);
  }

  @Override
  public boolean existsByCode(ShipmentCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    return idsByCode.containsKey(code);
  }
}
