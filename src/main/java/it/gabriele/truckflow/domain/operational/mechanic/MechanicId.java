package it.gabriele.truckflow.domain.operational.mechanic;

import java.util.UUID;

public record MechanicId(UUID value) {

  public MechanicId {
    if (value == null) {
      throw new IllegalArgumentException("Mechanic id is required.");
    }
  }

  public static MechanicId random() {
    return new MechanicId(UUID.randomUUID());
  }
}
