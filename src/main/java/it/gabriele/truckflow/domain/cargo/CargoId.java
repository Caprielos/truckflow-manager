package it.gabriele.truckflow.domain.cargo;

import java.util.UUID;

public record CargoId(UUID value) {

  public CargoId {
    value = CargoValidation.requireNonNull(value, "value");
  }

  public static CargoId random() {
    return new CargoId(UUID.randomUUID());
  }
}
