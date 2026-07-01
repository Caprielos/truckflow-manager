package it.gabriele.truckflow.application.usecase.vehicles;

import it.gabriele.truckflow.application.command.vehicles.DismissVehicleUnitCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.vehicles.DismissVehicleUnitUseCase;
import it.gabriele.truckflow.application.port.out.vehicles.VehicleUnitRepository;
import it.gabriele.truckflow.application.result.vehicles.VehicleUnitResult;

/** Application service that dismisses existing vehicle units from the fleet registry. */
public final class DismissVehicleUnitService implements DismissVehicleUnitUseCase {

  private final VehicleUnitRepository vehicleUnitRepository;

  public DismissVehicleUnitService(VehicleUnitRepository vehicleUnitRepository) {
    UseCaseValidationException.requireNonNull(vehicleUnitRepository, "vehicleUnitRepository");
    this.vehicleUnitRepository = vehicleUnitRepository;
  }

  @Override
  public VehicleUnitResult execute(DismissVehicleUnitCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var vehicleUnit =
        vehicleUnitRepository
            .findById(command.vehicleUnitId())
            .orElseThrow(
                () -> new ResourceNotFoundException("VehicleUnit", command.vehicleUnitId()));

    var updatedVehicleUnit = VehicleUnitMutationSupport.copyOf(vehicleUnit);
    updatedVehicleUnit.dismiss();

    return VehicleUnitResult.from(vehicleUnitRepository.save(updatedVehicleUnit));
  }
}
