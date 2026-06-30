package it.gabriele.truckflow.domain.vehicles;

import java.util.UUID;

public record VehicleCombinationId(UUID value) {

  public VehicleCombinationId {
    VehicleValidation.requireNonNull(value, "value");
  }

  public static VehicleCombinationId random() {
    return new VehicleCombinationId(UUID.randomUUID());
  }
}
