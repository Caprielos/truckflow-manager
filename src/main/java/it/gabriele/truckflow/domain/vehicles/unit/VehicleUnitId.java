package it.gabriele.truckflow.domain.vehicles.unit;

import it.gabriele.truckflow.domain.vehicles.common.VehicleValidation;
import java.util.UUID;

public record VehicleUnitId(UUID value) {

  public VehicleUnitId {
    VehicleValidation.requireNonNull(value, "value");
  }

  public static VehicleUnitId random() {
    return new VehicleUnitId(UUID.randomUUID());
  }
}
