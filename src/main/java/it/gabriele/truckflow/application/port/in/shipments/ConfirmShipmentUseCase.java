package it.gabriele.truckflow.application.port.in.shipments;

import it.gabriele.truckflow.application.command.shipments.ConfirmShipmentCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.shipments.ShipmentResult;

/** Inbound port for confirming a shipment. */
public interface ConfirmShipmentUseCase extends UseCase<ConfirmShipmentCommand, ShipmentResult> {}
