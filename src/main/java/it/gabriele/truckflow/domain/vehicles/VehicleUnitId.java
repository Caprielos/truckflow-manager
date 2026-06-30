package it.gabriele.truckflow.domain.vehicles;

import java.util.UUID;

public record VehicleUnitId(UUID value) {

  public VehicleUnitId {
    VehicleValidation.requireNonNull(value, "value");
  }

  public static VehicleUnitId random() {
    return new VehicleUnitId(UUID.randomUUID());
  }
}
