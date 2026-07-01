package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.application.command.operational.ActivateDriverCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.operational.ActivateDriverUseCase;
import it.gabriele.truckflow.application.port.out.operational.DriverRepository;
import it.gabriele.truckflow.application.result.operational.DriverResult;

/** Application service that changes the status of operational driver roles. */
public final class ActivateDriverService implements ActivateDriverUseCase {

  private final DriverRepository driverRepository;

  public ActivateDriverService(DriverRepository driverRepository) {
    UseCaseValidationException.requireNonNull(driverRepository, "driverRepository");
    this.driverRepository = driverRepository;
  }

  @Override
  public DriverResult execute(ActivateDriverCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var existingDriver =
        driverRepository
            .findById(command.id())
            .orElseThrow(() -> new ResourceNotFoundException("Driver", command.id()));
    var updatedDriver = DriverMutationSupport.copyOf(existingDriver);
    updatedDriver.activate(command.updatedBy());

    return DriverResult.from(driverRepository.save(updatedDriver));
  }
}
