package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.ActivateWarehouseOperatorCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.WarehouseOperatorResult;

/** Inbound port for the activate warehouse operator use case. */
public interface ActivateWarehouseOperatorUseCase
    extends UseCase<ActivateWarehouseOperatorCommand, WarehouseOperatorResult> {}
