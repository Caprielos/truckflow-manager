package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.application.command.operational.RegisterWarehouseOperatorCommand;
import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.operational.RegisterWarehouseOperatorUseCase;
import it.gabriele.truckflow.application.port.out.operational.WarehouseOperatorRepository;
import it.gabriele.truckflow.application.result.operational.WarehouseOperatorResult;
import it.gabriele.truckflow.domain.operational.warehouse.WarehouseOperator;

/** Application service that registers operational warehouseoperator roles. */
public final class RegisterWarehouseOperatorService implements RegisterWarehouseOperatorUseCase {

  private final WarehouseOperatorRepository warehouseOperatorRepository;

  public RegisterWarehouseOperatorService(WarehouseOperatorRepository warehouseOperatorRepository) {
    UseCaseValidationException.requireNonNull(
        warehouseOperatorRepository, "warehouseOperatorRepository");
    this.warehouseOperatorRepository = warehouseOperatorRepository;
  }

  @Override
  public WarehouseOperatorResult execute(RegisterWarehouseOperatorCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    if (warehouseOperatorRepository.existsByCode(command.code())) {
      throw new DuplicateResourceException("WarehouseOperator", command.code().value());
    }

    if (warehouseOperatorRepository.existsByUserId(command.userId())) {
      throw new DuplicateResourceException("WarehouseOperator", command.userId().value());
    }

    var warehouseOperator =
        new WarehouseOperator(
            null,
            command.code(),
            command.userId(),
            command.profile(),
            command.qualifications(),
            command.status(),
            command.metadata(),
            command.notes());

    return WarehouseOperatorResult.from(warehouseOperatorRepository.save(warehouseOperator));
  }
}
