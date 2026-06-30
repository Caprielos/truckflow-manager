package it.gabriele.truckflow.domain.operational.warehouse;

import it.gabriele.truckflow.domain.operational.exceptions.InvalidWarehouseOperatorException;
import java.util.UUID;

public record WarehouseOperatorId(UUID value) {

  public WarehouseOperatorId {
    if (value == null) {
      throw new InvalidWarehouseOperatorException("Warehouse operator id is required.");
    }
  }

  public static WarehouseOperatorId random() {
    return new WarehouseOperatorId(UUID.randomUUID());
  }
}
