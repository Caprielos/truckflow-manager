package it.gabriele.truckflow.domain.operational.dispatcher;

import it.gabriele.truckflow.domain.operational.exceptions.InvalidDispatcherException;
import java.util.UUID;

public record DispatcherId(UUID value) {

  public DispatcherId {
    if (value == null) {
      throw new InvalidDispatcherException("Dispatcher id is required.");
    }
  }

  public static DispatcherId random() {
    return new DispatcherId(UUID.randomUUID());
  }
}
