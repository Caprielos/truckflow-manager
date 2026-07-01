package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.application.command.operational.FindDriverCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.operational.FindDriverUseCase;
import it.gabriele.truckflow.application.port.out.operational.DriverRepository;
import it.gabriele.truckflow.application.result.operational.DriverResult;

/** Application service that finds operational driver roles. */
public final class FindDriverService implements FindDriverUseCase {

  private final DriverRepository driverRepository;

  public FindDriverService(DriverRepository driverRepository) {
    UseCaseValidationException.requireNonNull(driverRepository, "driverRepository");
    this.driverRepository = driverRepository;
  }

  @Override
  public DriverResult execute(FindDriverCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    return driverRepository
        .findById(command.id())
        .map(DriverResult::from)
        .orElseThrow(() -> new ResourceNotFoundException("Driver", command.id()));
  }
}
