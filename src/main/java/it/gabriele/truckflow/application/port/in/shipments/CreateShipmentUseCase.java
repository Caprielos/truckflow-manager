package it.gabriele.truckflow.application.port.in.shipments;

import it.gabriele.truckflow.application.command.shipments.CreateShipmentCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.shipments.ShipmentResult;

/** Inbound port for creating a shipment. */
public interface CreateShipmentUseCase extends UseCase<CreateShipmentCommand, ShipmentResult> {}
