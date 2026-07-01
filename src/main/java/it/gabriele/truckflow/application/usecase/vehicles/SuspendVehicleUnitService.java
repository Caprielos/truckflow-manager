package it.gabriele.truckflow.application.usecase.vehicles;

import it.gabriele.truckflow.application.command.vehicles.SuspendVehicleUnitCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.vehicles.SuspendVehicleUnitUseCase;
import it.gabriele.truckflow.application.port.out.vehicles.VehicleUnitRepository;
import it.gabriele.truckflow.application.result.vehicles.VehicleUnitResult;

/** Application service that suspends existing vehicle units. */
public final class SuspendVehicleUnitService implements SuspendVehicleUnitUseCase {

  private final VehicleUnitRepository vehicleUnitRepository;

  public SuspendVehicleUnitService(VehicleUnitRepository vehicleUnitRepository) {
    UseCaseValidationException.requireNonNull(vehicleUnitRepository, "vehicleUnitRepository");
    this.vehicleUnitRepository = vehicleUnitRepository;
  }

  @Override
  public VehicleUnitResult execute(SuspendVehicleUnitCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var vehicleUnit =
        vehicleUnitRepository
            .findById(command.vehicleUnitId())
            .orElseThrow(
                () -> new ResourceNotFoundException("VehicleUnit", command.vehicleUnitId()));

    var updatedVehicleUnit = VehicleUnitMutationSupport.copyOf(vehicleUnit);
    updatedVehicleUnit.suspend();

    return VehicleUnitResult.from(vehicleUnitRepository.save(updatedVehicleUnit));
  }
}
