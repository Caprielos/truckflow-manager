package it.gabriele.truckflow.domain.vehicles.combination;

import it.gabriele.truckflow.domain.vehicles.common.VehicleValidation;
import java.util.UUID;

public record VehicleCombinationId(UUID value) {

  public VehicleCombinationId {
    VehicleValidation.requireNonNull(value, "value");
  }

  public static VehicleCombinationId random() {
    return new VehicleCombinationId(UUID.randomUUID());
  }
}
