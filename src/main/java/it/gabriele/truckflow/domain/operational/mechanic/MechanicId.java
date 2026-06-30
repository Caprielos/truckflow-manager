package it.gabriele.truckflow.domain.operational.mechanic;

import it.gabriele.truckflow.domain.operational.exceptions.InvalidMechanicException;
import java.util.UUID;

public record MechanicId(UUID value) {

  public MechanicId {
    if (value == null) {
      throw new InvalidMechanicException("Mechanic id is required.");
    }
  }

  public static MechanicId random() {
    return new MechanicId(UUID.randomUUID());
  }
}
