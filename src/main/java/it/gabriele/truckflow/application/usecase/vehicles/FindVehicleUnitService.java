package it.gabriele.truckflow.application.usecase.vehicles;

import it.gabriele.truckflow.application.command.vehicles.FindVehicleUnitCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.vehicles.FindVehicleUnitUseCase;
import it.gabriele.truckflow.application.port.out.vehicles.VehicleUnitRepository;
import it.gabriele.truckflow.application.result.vehicles.VehicleUnitResult;

/** Application service that finds vehicle units. */
public final class FindVehicleUnitService implements FindVehicleUnitUseCase {

  private final VehicleUnitRepository vehicleUnitRepository;

  public FindVehicleUnitService(VehicleUnitRepository vehicleUnitRepository) {
    UseCaseValidationException.requireNonNull(vehicleUnitRepository, "vehicleUnitRepository");
    this.vehicleUnitRepository = vehicleUnitRepository;
  }

  @Override
  public VehicleUnitResult execute(FindVehicleUnitCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    return vehicleUnitRepository
        .findById(command.vehicleUnitId())
        .map(VehicleUnitResult::from)
        .orElseThrow(() -> new ResourceNotFoundException("VehicleUnit", command.vehicleUnitId()));
  }
}
