package it.gabriele.truckflow.domain.operational.driver;

import it.gabriele.truckflow.domain.operational.exceptions.InvalidDriverException;
import java.util.UUID;

public record DriverId(UUID value) {

  public DriverId {
    if (value == null) {
      throw new InvalidDriverException("Driver id is required.");
    }
  }

  public static DriverId random() {
    return new DriverId(UUID.randomUUID());
  }
}
