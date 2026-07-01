package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.SuspendWarehouseOperatorCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.WarehouseOperatorResult;

/** Inbound port for the suspend warehouseoperator use case. */
public interface SuspendWarehouseOperatorUseCase
    extends UseCase<SuspendWarehouseOperatorCommand, WarehouseOperatorResult> {}
