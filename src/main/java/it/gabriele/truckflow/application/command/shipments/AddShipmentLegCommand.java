package it.gabriele.truckflow.application.command.shipments;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.locations.LocationId;
import it.gabriele.truckflow.domain.shipments.core.ShipmentId;
import it.gabriele.truckflow.domain.shipments.legs.ShipmentLegType;
import java.math.BigDecimal;

/** Command used to add a logistics leg to a shipment. */
public record AddShipmentLegCommand(
    ShipmentId shipmentId,
    int sequenceNumber,
    ShipmentLegType type,
    LocationId originLocationId,
    LocationId destinationLocationId,
    BigDecimal estimatedDistanceKm,
    String notes)
    implements ApplicationCommand {

  public AddShipmentLegCommand {
    UseCaseValidationException.requireNonNull(shipmentId, "shipmentId");
    UseCaseValidationException.requireNonNull(type, "type");
    UseCaseValidationException.requireNonNull(originLocationId, "originLocationId");
    UseCaseValidationException.requireNonNull(destinationLocationId, "destinationLocationId");
  }
}
