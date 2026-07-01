package it.gabriele.truckflow.application.port.in.shipments;

import it.gabriele.truckflow.application.command.shipments.CancelShipmentCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.shipments.ShipmentResult;

/** Use case that cancels an existing shipment. */
public interface CancelShipmentUseCase extends UseCase<CancelShipmentCommand, ShipmentResult> {}
