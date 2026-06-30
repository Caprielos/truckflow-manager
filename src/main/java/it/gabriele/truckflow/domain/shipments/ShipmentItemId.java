package it.gabriele.truckflow.domain.shipments;

import java.util.UUID;

public record ShipmentItemId(UUID value) {

  public ShipmentItemId {
    value = ShipmentValidation.requireNonNull(value, "value");
  }

  public static ShipmentItemId random() {
    return new ShipmentItemId(UUID.randomUUID());
  }
}
