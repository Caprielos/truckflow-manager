package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.application.command.operational.FindWarehouseOperatorCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.operational.FindWarehouseOperatorUseCase;
import it.gabriele.truckflow.application.port.out.operational.WarehouseOperatorRepository;
import it.gabriele.truckflow.application.result.operational.WarehouseOperatorResult;

/** Application service that finds operational warehouse operator roles. */
public final class FindWarehouseOperatorService implements FindWarehouseOperatorUseCase {

  private final WarehouseOperatorRepository warehouseOperatorRepository;

  public FindWarehouseOperatorService(WarehouseOperatorRepository warehouseOperatorRepository) {
    UseCaseValidationException.requireNonNull(
        warehouseOperatorRepository, "warehouseOperatorRepository");
    this.warehouseOperatorRepository = warehouseOperatorRepository;
  }

  @Override
  public WarehouseOperatorResult execute(FindWarehouseOperatorCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    return warehouseOperatorRepository
        .findById(command.id())
        .map(WarehouseOperatorResult::from)
        .orElseThrow(() -> new ResourceNotFoundException("WarehouseOperator", command.id()));
  }
}
