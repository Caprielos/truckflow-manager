package it.gabriele.truckflow.application.usecase.locations;

import it.gabriele.truckflow.application.command.locations.RegisterLocationCommand;
import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.locations.RegisterLocationUseCase;
import it.gabriele.truckflow.application.port.out.locations.LocationRepository;
import it.gabriele.truckflow.application.result.locations.LocationResult;
import it.gabriele.truckflow.domain.locations.Location;

/** Application service that registers logistics locations. */
public final class RegisterLocationService implements RegisterLocationUseCase {

  private final LocationRepository locationRepository;

  public RegisterLocationService(LocationRepository locationRepository) {
    UseCaseValidationException.requireNonNull(locationRepository, "locationRepository");
    this.locationRepository = locationRepository;
  }

  @Override
  public LocationResult execute(RegisterLocationCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    if (locationRepository.existsByCode(command.code())) {
      throw new DuplicateResourceException("Location", command.code().value());
    }

    var location =
        new Location(
            null,
            command.code(),
            command.name(),
            command.type(),
            command.status(),
            command.address(),
            command.coordinates(),
            command.notes());

    return LocationResult.from(locationRepository.save(location));
  }
}
