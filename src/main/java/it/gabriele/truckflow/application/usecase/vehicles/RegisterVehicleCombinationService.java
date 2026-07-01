package it.gabriele.truckflow.application.usecase.vehicles;

import it.gabriele.truckflow.application.command.vehicles.RegisterVehicleCombinationCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.vehicles.RegisterVehicleCombinationUseCase;
import it.gabriele.truckflow.application.port.out.vehicles.VehicleCombinationRepository;
import it.gabriele.truckflow.application.port.out.vehicles.VehicleUnitRepository;
import it.gabriele.truckflow.application.result.vehicles.VehicleCombinationResult;
import it.gabriele.truckflow.domain.vehicles.combination.VehicleCombination;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnit;

/** Application service that registers operational vehicle combinations from existing units. */
public final class RegisterVehicleCombinationService implements RegisterVehicleCombinationUseCase {

  private final VehicleUnitRepository vehicleUnitRepository;
  private final VehicleCombinationRepository vehicleCombinationRepository;

  public RegisterVehicleCombinationService(
      VehicleUnitRepository vehicleUnitRepository,
      VehicleCombinationRepository vehicleCombinationRepository) {
    UseCaseValidationException.requireNonNull(vehicleUnitRepository, "vehicleUnitRepository");
    UseCaseValidationException.requireNonNull(
        vehicleCombinationRepository, "vehicleCombinationRepository");
    this.vehicleUnitRepository = vehicleUnitRepository;
    this.vehicleCombinationRepository = vehicleCombinationRepository;
  }

  @Override
  public VehicleCombinationResult execute(RegisterVehicleCombinationCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var primaryUnit =
        vehicleUnitRepository
            .findById(command.primaryUnitId())
            .orElseThrow(
                () -> new ResourceNotFoundException("VehicleUnit", command.primaryUnitId()));
    VehicleUnit secondaryUnit = null;
    if (command.secondaryUnitId() != null) {
      secondaryUnit =
          vehicleUnitRepository
              .findById(command.secondaryUnitId())
              .orElseThrow(
                  () -> new ResourceNotFoundException("VehicleUnit", command.secondaryUnitId()));
    }

    var vehicleCombination =
        VehicleCombination.fromUnits(
            null,
            command.combinationType(),
            primaryUnit,
            secondaryUnit,
            command.status(),
            command.notes());

    return VehicleCombinationResult.from(vehicleCombinationRepository.save(vehicleCombination));
  }
}
