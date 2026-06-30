package it.gabriele.truckflow.domain.operational.dispatcher;

import java.util.UUID;

public record DispatcherId(UUID value) {

  public DispatcherId {
    if (value == null) {
      throw new IllegalArgumentException("Dispatcher id is required.");
    }
  }

  public static DispatcherId random() {
    return new DispatcherId(UUID.randomUUID());
  }
}
