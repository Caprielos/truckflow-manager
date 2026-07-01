package it.gabriele.truckflow.application.command.shipments;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.shipments.core.ShipmentId;

/** Command used to cancel a shipment. */
public record CancelShipmentCommand(ShipmentId shipmentId) implements ApplicationCommand {

  public CancelShipmentCommand {
    UseCaseValidationException.requireNonNull(shipmentId, "shipmentId");
  }
}
