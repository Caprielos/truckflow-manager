package it.gabriele.truckflow.application.usecase.vehicles;

import it.gabriele.truckflow.application.command.vehicles.RegisterVehicleUnitCommand;
import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.vehicles.RegisterVehicleUnitUseCase;
import it.gabriele.truckflow.application.port.out.vehicles.VehicleUnitRepository;
import it.gabriele.truckflow.application.result.vehicles.VehicleUnitResult;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnit;

/** Application service that registers physical vehicle units. */
public final class RegisterVehicleUnitService implements RegisterVehicleUnitUseCase {

  private final VehicleUnitRepository vehicleUnitRepository;

  public RegisterVehicleUnitService(VehicleUnitRepository vehicleUnitRepository) {
    UseCaseValidationException.requireNonNull(vehicleUnitRepository, "vehicleUnitRepository");
    this.vehicleUnitRepository = vehicleUnitRepository;
  }

  @Override
  public VehicleUnitResult execute(RegisterVehicleUnitCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    if (vehicleUnitRepository.existsByFleetCode(command.fleetCode())) {
      throw new DuplicateResourceException("VehicleUnit", command.fleetCode().value());
    }

    if (vehicleUnitRepository.existsByVin(command.vin())) {
      throw new DuplicateResourceException("VehicleUnit", command.vin().value());
    }

    if (command.licensePlate() != null
        && vehicleUnitRepository.existsByLicensePlate(command.licensePlate())) {
      throw new DuplicateResourceException("VehicleUnit", command.licensePlate().value());
    }

    var vehicleUnit =
        new VehicleUnit(
            null,
            command.fleetCode(),
            command.licensePlate(),
            command.vin(),
            command.unitType(),
            command.bodyType(),
            command.powerSource(),
            command.technicalSpecification(),
            command.bodyProfile(),
            command.capabilities(),
            command.operationalRoles(),
            command.couplingProfile(),
            command.status(),
            command.notes());

    return VehicleUnitResult.from(vehicleUnitRepository.save(vehicleUnit));
  }
}
