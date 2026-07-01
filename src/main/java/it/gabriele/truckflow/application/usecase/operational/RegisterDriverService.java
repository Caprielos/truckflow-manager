package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.application.command.operational.RegisterDriverCommand;
import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.operational.RegisterDriverUseCase;
import it.gabriele.truckflow.application.port.out.operational.DriverRepository;
import it.gabriele.truckflow.application.result.operational.DriverResult;
import it.gabriele.truckflow.domain.operational.driver.Driver;

/** Application service that registers operational driver roles. */
public final class RegisterDriverService implements RegisterDriverUseCase {

  private final DriverRepository driverRepository;

  public RegisterDriverService(DriverRepository driverRepository) {
    UseCaseValidationException.requireNonNull(driverRepository, "driverRepository");
    this.driverRepository = driverRepository;
  }

  @Override
  public DriverResult execute(RegisterDriverCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    if (driverRepository.existsByCode(command.code())) {
      throw new DuplicateResourceException("Driver", command.code().value());
    }

    if (driverRepository.existsByUserId(command.userId())) {
      throw new DuplicateResourceException("Driver", command.userId().value());
    }

    var driver =
        new Driver(
            null,
            command.code(),
            command.userId(),
            command.profile(),
            command.qualifications(),
            command.status(),
            command.metadata(),
            command.notes());

    return DriverResult.from(driverRepository.save(driver));
  }
}
