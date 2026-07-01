package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.MarkNotEligibleWarehouseOperatorCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.WarehouseOperatorResult;

/** Inbound port for the markNotEligible warehouseoperator use case. */
public interface MarkNotEligibleWarehouseOperatorUseCase
    extends UseCase<MarkNotEligibleWarehouseOperatorCommand, WarehouseOperatorResult> {}
