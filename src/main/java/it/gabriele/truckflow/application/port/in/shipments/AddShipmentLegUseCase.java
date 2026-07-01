package it.gabriele.truckflow.application.port.in.shipments;

import it.gabriele.truckflow.application.command.shipments.AddShipmentLegCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.shipments.ShipmentResult;

/** Inbound port for adding a logistics leg to a shipment. */
public interface AddShipmentLegUseCase extends UseCase<AddShipmentLegCommand, ShipmentResult> {}
