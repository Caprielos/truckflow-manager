package it.gabriele.truckflow.domain.users;

import it.gabriele.truckflow.domain.users.exceptions.InvalidUserException;
import java.util.UUID;

public record UserId(UUID value) {

  public UserId {
    if (value == null) {
      throw new InvalidUserException("User id is required.");
    }
  }

  public static UserId random() {
    return new UserId(UUID.randomUUID());
  }
}
