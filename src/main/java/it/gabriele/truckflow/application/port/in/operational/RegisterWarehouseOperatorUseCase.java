package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.RegisterWarehouseOperatorCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.WarehouseOperatorResult;

/** Inbound port for the register warehouseoperator use case. */
public interface RegisterWarehouseOperatorUseCase
    extends UseCase<RegisterWarehouseOperatorCommand, WarehouseOperatorResult> {}
