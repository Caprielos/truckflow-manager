package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.domain.operational.warehouse.WarehouseOperator;
import java.util.Set;

final class WarehouseOperatorMutationSupport {

  private WarehouseOperatorMutationSupport() {}

  static WarehouseOperator copyOf(WarehouseOperator warehouseOperator) {
    return new WarehouseOperator(
        warehouseOperator.id(),
        warehouseOperator.code(),
        warehouseOperator.userId(),
        warehouseOperator.profile(),
        Set.copyOf(warehouseOperator.qualifications()),
        warehouseOperator.status(),
        warehouseOperator.metadata(),
        warehouseOperator.notes());
  }
}
