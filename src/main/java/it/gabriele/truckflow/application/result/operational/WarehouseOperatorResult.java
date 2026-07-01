package it.gabriele.truckflow.application.result.operational;

import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.result.ApplicationResult;
import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.common.OperationalStatus;
import it.gabriele.truckflow.domain.operational.warehouse.WarehouseOperator;
import it.gabriele.truckflow.domain.operational.warehouse.WarehouseOperatorId;
import it.gabriele.truckflow.domain.users.UserId;

/** Result returned by warehouseoperator operational role use cases. */
public record WarehouseOperatorResult(
    WarehouseOperatorId id,
    OperationalCode code,
    UserId userId,
    OperationalStatus status,
    String fullName,
    boolean active,
    int qualificationCount,
    String notes)
    implements ApplicationResult {

  public static WarehouseOperatorResult from(WarehouseOperator warehouseOperator) {
    UseCaseValidationException.requireNonNull(warehouseOperator, "warehouseOperator");

    return new WarehouseOperatorResult(
        warehouseOperator.id(),
        warehouseOperator.code(),
        warehouseOperator.userId(),
        warehouseOperator.status(),
        warehouseOperator.profile().fullName(),
        warehouseOperator.isActive(),
        warehouseOperator.qualifications().size(),
        warehouseOperator.notes());
  }
}
