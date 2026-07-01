package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.application.command.operational.MarkNotEligibleWarehouseOperatorCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.operational.MarkNotEligibleWarehouseOperatorUseCase;
import it.gabriele.truckflow.application.port.out.operational.WarehouseOperatorRepository;
import it.gabriele.truckflow.application.result.operational.WarehouseOperatorResult;

/** Application service that changes the status of operational warehouseoperator roles. */
public final class MarkNotEligibleWarehouseOperatorService
    implements MarkNotEligibleWarehouseOperatorUseCase {

  private final WarehouseOperatorRepository warehouseOperatorRepository;

  public MarkNotEligibleWarehouseOperatorService(
      WarehouseOperatorRepository warehouseOperatorRepository) {
    UseCaseValidationException.requireNonNull(
        warehouseOperatorRepository, "warehouseOperatorRepository");
    this.warehouseOperatorRepository = warehouseOperatorRepository;
  }

  @Override
  public WarehouseOperatorResult execute(MarkNotEligibleWarehouseOperatorCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var existingWarehouseOperator =
        warehouseOperatorRepository
            .findById(command.id())
            .orElseThrow(() -> new ResourceNotFoundException("WarehouseOperator", command.id()));
    var updatedWarehouseOperator =
        WarehouseOperatorMutationSupport.copyOf(existingWarehouseOperator);
    updatedWarehouseOperator.markNotEligible(command.updatedBy());

    return WarehouseOperatorResult.from(warehouseOperatorRepository.save(updatedWarehouseOperator));
  }
}
