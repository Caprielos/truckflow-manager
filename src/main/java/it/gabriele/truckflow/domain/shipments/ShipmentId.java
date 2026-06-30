package it.gabriele.truckflow.domain.shipments;

import java.util.UUID;

public record ShipmentId(UUID value) {

  public ShipmentId {
    value = ShipmentValidation.requireNonNull(value, "value");
  }

  public static ShipmentId random() {
    return new ShipmentId(UUID.randomUUID());
  }
}
