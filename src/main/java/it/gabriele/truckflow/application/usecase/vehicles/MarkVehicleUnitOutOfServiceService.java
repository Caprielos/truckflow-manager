package it.gabriele.truckflow.application.usecase.vehicles;

import it.gabriele.truckflow.application.command.vehicles.MarkVehicleUnitOutOfServiceCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.vehicles.MarkVehicleUnitOutOfServiceUseCase;
import it.gabriele.truckflow.application.port.out.vehicles.VehicleUnitRepository;
import it.gabriele.truckflow.application.result.vehicles.VehicleUnitResult;

/** Application service that marks existing vehicle units out of service. */
public final class MarkVehicleUnitOutOfServiceService
    implements MarkVehicleUnitOutOfServiceUseCase {

  private final VehicleUnitRepository vehicleUnitRepository;

  public MarkVehicleUnitOutOfServiceService(VehicleUnitRepository vehicleUnitRepository) {
    UseCaseValidationException.requireNonNull(vehicleUnitRepository, "vehicleUnitRepository");
    this.vehicleUnitRepository = vehicleUnitRepository;
  }

  @Override
  public VehicleUnitResult execute(MarkVehicleUnitOutOfServiceCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var vehicleUnit =
        vehicleUnitRepository
            .findById(command.vehicleUnitId())
            .orElseThrow(
                () -> new ResourceNotFoundException("VehicleUnit", command.vehicleUnitId()));

    var updatedVehicleUnit = VehicleUnitMutationSupport.copyOf(vehicleUnit);
    updatedVehicleUnit.markOutOfService();

    return VehicleUnitResult.from(vehicleUnitRepository.save(updatedVehicleUnit));
  }
}
