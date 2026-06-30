package it.gabriele.truckflow.domain.operational.manager;

import it.gabriele.truckflow.domain.operational.exceptions.InvalidManagerException;
import java.util.UUID;

public record ManagerId(UUID value) {

  public ManagerId {
    if (value == null) {
      throw new InvalidManagerException("Manager id is required.");
    }
  }

  public static ManagerId random() {
    return new ManagerId(UUID.randomUUID());
  }
}
