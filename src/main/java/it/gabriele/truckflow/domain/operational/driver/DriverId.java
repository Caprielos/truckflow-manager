package it.gabriele.truckflow.domain.operational.driver;

import java.util.UUID;

public record DriverId(UUID value) {

  public DriverId {
    if (value == null) {
      throw new IllegalArgumentException("Driver id is required.");
    }
  }

  public static DriverId random() {
    return new DriverId(UUID.randomUUID());
  }
}
