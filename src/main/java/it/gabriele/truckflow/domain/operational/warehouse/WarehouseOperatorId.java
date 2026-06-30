package it.gabriele.truckflow.domain.operational.warehouse;

import java.util.UUID;

public record WarehouseOperatorId(UUID value) {

  public WarehouseOperatorId {
    if (value == null) {
      throw new IllegalArgumentException("Warehouse operator id is required.");
    }
  }

  public static WarehouseOperatorId random() {
    return new WarehouseOperatorId(UUID.randomUUID());
  }
}
