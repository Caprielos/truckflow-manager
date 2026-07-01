package it.gabriele.truckflow.application.usecase.vehicles;

import it.gabriele.truckflow.application.command.vehicles.FindVehicleCombinationCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.vehicles.FindVehicleCombinationUseCase;
import it.gabriele.truckflow.application.port.out.vehicles.VehicleCombinationRepository;
import it.gabriele.truckflow.application.result.vehicles.VehicleCombinationResult;

/** Application service that finds vehicle combinations. */
public final class FindVehicleCombinationService implements FindVehicleCombinationUseCase {

  private final VehicleCombinationRepository vehicleCombinationRepository;

  public FindVehicleCombinationService(VehicleCombinationRepository vehicleCombinationRepository) {
    UseCaseValidationException.requireNonNull(
        vehicleCombinationRepository, "vehicleCombinationRepository");
    this.vehicleCombinationRepository = vehicleCombinationRepository;
  }

  @Override
  public VehicleCombinationResult execute(FindVehicleCombinationCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    return vehicleCombinationRepository
        .findById(command.vehicleCombinationId())
        .map(VehicleCombinationResult::from)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "VehicleCombination", command.vehicleCombinationId()));
  }
}
