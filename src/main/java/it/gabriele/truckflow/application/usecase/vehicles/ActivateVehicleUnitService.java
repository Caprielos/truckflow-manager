package it.gabriele.truckflow.application.usecase.vehicles;

import it.gabriele.truckflow.application.command.vehicles.ActivateVehicleUnitCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.vehicles.ActivateVehicleUnitUseCase;
import it.gabriele.truckflow.application.port.out.vehicles.VehicleUnitRepository;
import it.gabriele.truckflow.application.result.vehicles.VehicleUnitResult;

/** Application service that activates existing vehicle units. */
public final class ActivateVehicleUnitService implements ActivateVehicleUnitUseCase {

  private final VehicleUnitRepository vehicleUnitRepository;

  public ActivateVehicleUnitService(VehicleUnitRepository vehicleUnitRepository) {
    UseCaseValidationException.requireNonNull(vehicleUnitRepository, "vehicleUnitRepository");
    this.vehicleUnitRepository = vehicleUnitRepository;
  }

  @Override
  public VehicleUnitResult execute(ActivateVehicleUnitCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var vehicleUnit =
        vehicleUnitRepository
            .findById(command.vehicleUnitId())
            .orElseThrow(
                () -> new ResourceNotFoundException("VehicleUnit", command.vehicleUnitId()));

    var updatedVehicleUnit = VehicleUnitMutationSupport.copyOf(vehicleUnit);
    updatedVehicleUnit.activate();

    return VehicleUnitResult.from(vehicleUnitRepository.save(updatedVehicleUnit));
  }
}
