package it.gabriele.truckflow.application.port.in.shipments;

import it.gabriele.truckflow.application.command.shipments.AddShipmentItemCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.shipments.ShipmentResult;

/** Inbound port for adding a cargo item to a shipment. */
public interface AddShipmentItemUseCase extends UseCase<AddShipmentItemCommand, ShipmentResult> {}
