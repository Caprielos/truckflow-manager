package it.gabriele.truckflow.application.command.shipments;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.cargo.CargoId;
import it.gabriele.truckflow.domain.shipments.core.ShipmentId;
import it.gabriele.truckflow.domain.shipments.items.ShipmentUnitOfMeasure;
import java.math.BigDecimal;

/** Command used to add a cargo item to a shipment. */
public record AddShipmentItemCommand(
    ShipmentId shipmentId,
    CargoId cargoId,
    BigDecimal quantity,
    ShipmentUnitOfMeasure unitOfMeasure,
    String notes)
    implements ApplicationCommand {

  public AddShipmentItemCommand {
    UseCaseValidationException.requireNonNull(shipmentId, "shipmentId");
    UseCaseValidationException.requireNonNull(cargoId, "cargoId");
    UseCaseValidationException.requireNonNull(quantity, "quantity");
    UseCaseValidationException.requireNonNull(unitOfMeasure, "unitOfMeasure");
  }
}
