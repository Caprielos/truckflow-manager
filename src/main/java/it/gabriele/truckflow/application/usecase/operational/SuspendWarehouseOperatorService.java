package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.application.command.operational.SuspendWarehouseOperatorCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.operational.SuspendWarehouseOperatorUseCase;
import it.gabriele.truckflow.application.port.out.operational.WarehouseOperatorRepository;
import it.gabriele.truckflow.application.result.operational.WarehouseOperatorResult;

/** Application service that changes the status of operational warehouse operator roles. */
public final class SuspendWarehouseOperatorService implements SuspendWarehouseOperatorUseCase {

  private final WarehouseOperatorRepository warehouseOperatorRepository;

  public SuspendWarehouseOperatorService(WarehouseOperatorRepository warehouseOperatorRepository) {
    UseCaseValidationException.requireNonNull(
        warehouseOperatorRepository, "warehouseOperatorRepository");
    this.warehouseOperatorRepository = warehouseOperatorRepository;
  }

  @Override
  public WarehouseOperatorResult execute(SuspendWarehouseOperatorCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var existingWarehouseOperator =
        warehouseOperatorRepository
            .findById(command.id())
            .orElseThrow(() -> new ResourceNotFoundException("WarehouseOperator", command.id()));
    var updatedWarehouseOperator =
        WarehouseOperatorMutationSupport.copyOf(existingWarehouseOperator);
    updatedWarehouseOperator.suspend(command.updatedBy());

    return WarehouseOperatorResult.from(warehouseOperatorRepository.save(updatedWarehouseOperator));
  }
}
