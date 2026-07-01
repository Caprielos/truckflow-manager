package it.gabriele.truckflow.application.port.in.shipments;

import it.gabriele.truckflow.application.command.shipments.FindShipmentCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.shipments.ShipmentResult;

/** Inbound port for finding a shipment. */
public interface FindShipmentUseCase extends UseCase<FindShipmentCommand, ShipmentResult> {}
