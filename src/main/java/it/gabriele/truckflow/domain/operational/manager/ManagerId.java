package it.gabriele.truckflow.domain.operational.manager;

import java.util.UUID;

public record ManagerId(UUID value) {

  public ManagerId {
    if (value == null) {
      throw new IllegalArgumentException("Manager id is required.");
    }
  }

  public static ManagerId random() {
    return new ManagerId(UUID.randomUUID());
  }
}
