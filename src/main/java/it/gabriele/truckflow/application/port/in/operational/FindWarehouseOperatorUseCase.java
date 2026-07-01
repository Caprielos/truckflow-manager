package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.FindWarehouseOperatorCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.WarehouseOperatorResult;

/** Inbound port for the find warehouseoperator use case. */
public interface FindWarehouseOperatorUseCase
    extends UseCase<FindWarehouseOperatorCommand, WarehouseOperatorResult> {}
