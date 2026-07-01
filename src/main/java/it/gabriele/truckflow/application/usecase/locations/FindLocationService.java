package it.gabriele.truckflow.application.usecase.locations;

import it.gabriele.truckflow.application.command.locations.FindLocationCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.locations.FindLocationUseCase;
import it.gabriele.truckflow.application.port.out.locations.LocationRepository;
import it.gabriele.truckflow.application.result.locations.LocationResult;

/** Application service that finds logistics locations. */
public final class FindLocationService implements FindLocationUseCase {

  private final LocationRepository locationRepository;

  public FindLocationService(LocationRepository locationRepository) {
    UseCaseValidationException.requireNonNull(locationRepository, "locationRepository");
    this.locationRepository = locationRepository;
  }

  @Override
  public LocationResult execute(FindLocationCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    return locationRepository
        .findById(command.locationId())
        .map(LocationResult::from)
        .orElseThrow(() -> new ResourceNotFoundException("Location", command.locationId()));
  }
}
