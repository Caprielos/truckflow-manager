package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.application.command.operational.ActivateWarehouseOperatorCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.operational.ActivateWarehouseOperatorUseCase;
import it.gabriele.truckflow.application.port.out.operational.WarehouseOperatorRepository;
import it.gabriele.truckflow.application.result.operational.WarehouseOperatorResult;

/** Application service that changes the status of operational warehouseoperator roles. */
public final class ActivateWarehouseOperatorService implements ActivateWarehouseOperatorUseCase {

  private final WarehouseOperatorRepository warehouseOperatorRepository;

  public ActivateWarehouseOperatorService(WarehouseOperatorRepository warehouseOperatorRepository) {
    UseCaseValidationException.requireNonNull(
        warehouseOperatorRepository, "warehouseOperatorRepository");
    this.warehouseOperatorRepository = warehouseOperatorRepository;
  }

  @Override
  public WarehouseOperatorResult execute(ActivateWarehouseOperatorCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var existingWarehouseOperator =
        warehouseOperatorRepository
            .findById(command.id())
            .orElseThrow(() -> new ResourceNotFoundException("WarehouseOperator", command.id()));
    var updatedWarehouseOperator =
        WarehouseOperatorMutationSupport.copyOf(existingWarehouseOperator);
    updatedWarehouseOperator.activate(command.updatedBy());

    return WarehouseOperatorResult.from(warehouseOperatorRepository.save(updatedWarehouseOperator));
  }
}
