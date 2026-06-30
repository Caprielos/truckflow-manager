package it.gabriele.truckflow.domain.locations;

import java.util.UUID;

public record LocationId(UUID value) {

  public LocationId {
    value = LocationValidation.requireNonNull(value, "value");
  }

  public static LocationId random() {
    return new LocationId(UUID.randomUUID());
  }
}
