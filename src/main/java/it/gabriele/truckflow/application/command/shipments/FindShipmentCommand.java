package it.gabriele.truckflow.application.command.shipments;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.shipments.core.ShipmentId;

/** Command used to find a shipment by identifier. */
public record FindShipmentCommand(ShipmentId shipmentId) implements ApplicationCommand {

  public FindShipmentCommand {
    UseCaseValidationException.requireNonNull(shipmentId, "shipmentId");
  }
}
