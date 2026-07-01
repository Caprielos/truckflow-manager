package it.gabriele.truckflow.application.result.shipments;

import it.gabriele.truckflow.application.result.ApplicationResult;
import it.gabriele.truckflow.domain.shipments.core.Shipment;
import it.gabriele.truckflow.domain.shipments.core.ShipmentCode;
import it.gabriele.truckflow.domain.shipments.core.ShipmentId;
import it.gabriele.truckflow.domain.shipments.core.ShipmentPriority;
import it.gabriele.truckflow.domain.shipments.core.ShipmentServiceLevel;
import it.gabriele.truckflow.domain.shipments.core.ShipmentStatus;

/** Result returned by shipment use cases. */
public record ShipmentResult(
    ShipmentId id,
    ShipmentCode code,
    String name,
    ShipmentStatus status,
    ShipmentPriority priority,
    ShipmentServiceLevel serviceLevel,
    int itemCount,
    int legCount,
    boolean confirmed)
    implements ApplicationResult {

  public static ShipmentResult from(Shipment shipment) {
    return new ShipmentResult(
        shipment.id(),
        shipment.code(),
        shipment.name(),
        shipment.status(),
        shipment.priority(),
        shipment.serviceLevel(),
        shipment.itemCount(),
        shipment.legCount(),
        shipment.isConfirmed());
  }
}
