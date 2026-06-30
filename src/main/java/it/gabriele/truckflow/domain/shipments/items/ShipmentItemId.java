package it.gabriele.truckflow.domain.shipments.items;

import it.gabriele.truckflow.domain.shipments.core.ShipmentValidation;
import java.util.UUID;

public record ShipmentItemId(UUID value) {

  public ShipmentItemId {
    value = ShipmentValidation.requireNonNull(value, "value");
  }

  public static ShipmentItemId random() {
    return new ShipmentItemId(UUID.randomUUID());
  }
}
