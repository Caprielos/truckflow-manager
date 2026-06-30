package it.gabriele.truckflow.domain.shipments.legs;

import it.gabriele.truckflow.domain.shipments.core.ShipmentValidation;
import java.util.UUID;

public record ShipmentLegId(UUID value) {

  public ShipmentLegId {
    value = ShipmentValidation.requireNonNull(value, "value");
  }

  public static ShipmentLegId random() {
    return new ShipmentLegId(UUID.randomUUID());
  }
}
