package it.gabriele.truckflow.application.command.shipments;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.shipments.core.ShipmentId;

/** Command used to confirm a shipment. */
public record ConfirmShipmentCommand(ShipmentId shipmentId) implements ApplicationCommand {

  public ConfirmShipmentCommand {
    UseCaseValidationException.requireNonNull(shipmentId, "shipmentId");
  }
}
