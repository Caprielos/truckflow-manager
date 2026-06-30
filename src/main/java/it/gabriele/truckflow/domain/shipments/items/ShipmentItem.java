package it.gabriele.truckflow.domain.shipments.items;

import it.gabriele.truckflow.domain.cargo.CargoId;
import it.gabriele.truckflow.domain.shipments.core.ShipmentValidation;
import java.math.BigDecimal;

public record ShipmentItem(
    ShipmentItemId id,
    CargoId cargoId,
    BigDecimal quantity,
    ShipmentUnitOfMeasure unitOfMeasure,
    String notes) {

  public ShipmentItem {
    id = id == null ? ShipmentItemId.random() : id;
    cargoId = ShipmentValidation.requireNonNull(cargoId, "cargoId");
    quantity = ShipmentValidation.requirePositive(quantity, "quantity");
    unitOfMeasure = ShipmentValidation.requireNonNull(unitOfMeasure, "unitOfMeasure");
    notes = ShipmentValidation.normalize(notes);
  }
}
